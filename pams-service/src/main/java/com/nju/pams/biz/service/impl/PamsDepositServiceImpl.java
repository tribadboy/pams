package com.nju.pams.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsDepositService;
import com.nju.pams.mapper.dao.PamsDepositChangeDAO;
import com.nju.pams.mapper.dao.PamsDepositRecordDAO;
import com.nju.pams.model.asset.DepositChange;
import com.nju.pams.model.asset.DepositRecord;
import com.nju.pams.model.asset.DepositTimeEnum;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.TimeRangeUtil;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsDepositServiceImpl implements PamsDepositService {
	
	@Autowired
	PamsDepositRecordDAO pamsDepositRecordDAO;
	
	@Autowired
	PamsDepositChangeDAO pamsDepositChangeDAO;
	
	/**
	 * 根据存款id获取存款记录
	 */
	@Override
	public DepositRecord getDepositRecordByDepositId(Integer depositId) {
		return pamsDepositRecordDAO.getDepositRecordByDepositId(depositId);
	}

	/**
	 * 首次创建存款账户
	 */
	@Override
	public void makeDepositRecord(DepositRecord depositRecord, BigDecimal changeAmount, String changeTime) {
		if(null == depositRecord || null == changeAmount || null == changeTime) {
			return;
		}
		//首先插入存款记录
		pamsDepositRecordDAO.insertDepositRecord(depositRecord);
		//然后插入存款变更记录，设置为首次创建存款
		DepositChange change = new DepositChange();
		change.setDepositId(depositRecord.getDepositId());
		change.setChangeTypeId(DepositChange.ChangeType.MakeAccount.toIntValue());
		change.setChangeAmount(changeAmount);
		change.setChangeTime(changeTime);
		pamsDepositChangeDAO.insertDepositChange(change);
	}

	/**
	 * 检查参数日期是否可以进行转入或转出
	 */
	@Override
	public boolean checkDateValid(Integer depositId, String checkDate) {
		if(null == depositId || null == checkDate) {
			return false;
		}
		DepositRecord record = pamsDepositRecordDAO.getDepositRecordByDepositId(depositId);
		if(null == record || record.getStatus() == DepositRecord.Status.InValid.toIntValue()) {
			return false;
		}
		String maxDate = pamsDepositChangeDAO.getMaxChangeDateByDepositId(depositId);
		if(null == maxDate || maxDate.compareTo(checkDate) >= 0) {
			return false;
		}
		return true;
	}

	/**
	 * 检查对于该账户当前可转出的最大数额，（即当前本金）,此时不再检查checkDate是否有效
	 */
	@Override
	public BigDecimal getMaxValidAmountForOutflow(Integer depositId, String checkDate) {
		DepositRecord record = pamsDepositRecordDAO.getDepositRecordByDepositId(depositId);
		List<DepositChange> changeList = pamsDepositChangeDAO.getDepositChangeListByDepositId(depositId);
		if(null == record || null == changeList || changeList.size() == 0) {
			return null;
		}
		if(record.getDepositTimeId() == DepositTimeEnum.NoTime.getIndex()) {
			//该存款账户为活期账户
			BigDecimal capital = changeList.get(0).getChangeAmount();
			String startDate = changeList.get(0).getChangeTime();
			for(DepositChange change : getChangeListBetweenDays(changeList, startDate, checkDate)) {
				int typeId = change.getChangeTypeId();
				if(typeId == DepositChange.ChangeType.Inflow.toIntValue()) {
					capital = capital.add(change.getChangeAmount());
				} else if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
					capital = capital.subtract(change.getChangeAmount());
				} 
			}
			return BigDecimalUtil.generateFormatNumber(capital);
		} else {
			//该账户为定期账户
			int daysOfYear = 360;
			float currentProfitPercent = record.getCurrentProfitPercent();
			float fixedProfitPercent = record.getFixedProfitPercent();
			BigDecimal capital = changeList.get(0).getChangeAmount();
			BigDecimal interest = BigDecimal.ZERO;
			int daysCount = DepositTimeEnum.getTimeFromIndex(record.getDepositTimeId()).getDaysCount();
			String startDate = changeList.get(0).getChangeTime();
			String tmpDate = TimeRangeUtil.getSomedayPlusDays(startDate, daysCount);
			while(checkDate.compareTo(tmpDate) >= 0) {
				//检查日在一个存储周期之后，更新本金和利息，重置周期
				BigDecimal currentCapital = BigDecimal.ZERO;
				for(DepositChange change : getChangeListBetweenDays(changeList, startDate, tmpDate)) {
					int typeId = change.getChangeTypeId();
					String changeDate = change.getChangeTime();		
					if(typeId == DepositChange.ChangeType.Inflow.toIntValue()) {
						BigDecimal inflowAmount = change.getChangeAmount();
						int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, tmpDate);
						interest  = interest.add(inflowAmount.multiply(
								BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear /100)));
						currentCapital = currentCapital.add(inflowAmount);
					} else if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
						BigDecimal outflowAmount = change.getChangeAmount();
						if(outflowAmount.compareTo(currentCapital) > 0) {
							BigDecimal subtractCaptial = outflowAmount.subtract(currentCapital);
							int period1 = TimeRangeUtil.getPeriodDaysBetweenTwoDate(startDate, changeDate);
							int period2 = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, tmpDate);
							interest  = interest.add(subtractCaptial.multiply(
									BigDecimal.valueOf(currentProfitPercent * period1 / daysOfYear /100)));
							capital = capital.subtract(subtractCaptial);
							interest  = interest.subtract(currentCapital.multiply(
									BigDecimal.valueOf(currentProfitPercent * period2 / daysOfYear /100)));
							currentCapital = BigDecimal.ZERO;						
						} else {
							int period2 = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, tmpDate);
							interest  = interest.subtract(outflowAmount.multiply(
									BigDecimal.valueOf(currentProfitPercent * period2 / daysOfYear /100)));
							currentCapital = currentCapital.subtract(outflowAmount);	
						}
					} 
				}
				interest  = interest.add(capital.multiply(
						BigDecimal.valueOf(fixedProfitPercent * daysCount / daysOfYear /100)));
				capital = capital.add(interest);
				capital = capital.add(currentCapital);
				interest = BigDecimal.ZERO;
				startDate = tmpDate;
				tmpDate = TimeRangeUtil.getSomedayPlusDays(startDate, daysCount);
			}
			//此时检查日在一个存储周期之中，可当作活期处理
			for(DepositChange change : getChangeListBetweenDays(changeList, startDate, checkDate)) {
				int typeId = change.getChangeTypeId();
				if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
					capital = capital.subtract(change.getChangeAmount());
				} else if(typeId == DepositChange.ChangeType.Inflow.toIntValue()) {
					capital = capital.add(change.getChangeAmount());
				}
			}
			return BigDecimalUtil.generateFormatNumber(capital);
		}
	}
	
	public List<DepositChange> getChangeListBetweenDays(List<DepositChange>sourceList, 
			String startDate, String endDate) {
		List<DepositChange> ansList = new ArrayList<DepositChange>();
		if(CollectionUtils.isNotEmpty(sourceList)) {
			for(DepositChange change : sourceList) {
				String changeTime = change.getChangeTime();
				if(changeTime.compareTo(startDate) >= 0 && changeTime.compareTo(endDate) < 0) {
					ansList.add(change);
				}
			}
		}
		return ansList;
	}

	/**
	 * 插入转入或者转出记录，此时不做任何检查
	 */
	@Override
	public void insertDepositChangeForInflowAndOutflow(DepositChange depositChange) {
		pamsDepositChangeDAO.insertDepositChange(depositChange);
	}

	/**
	 * 关闭存款记录，此时不做任何检查
	 */
	@Override
	public void closeDepositRecord(Integer depositId, String checkDate) {
		BigDecimal allAmount = computeDepositRecordValue(depositId, checkDate);
		DepositChange endChange = new DepositChange();
		endChange.setDepositId(depositId);
		endChange.setChangeTypeId(DepositChange.ChangeType.CloseAccount.toIntValue());
		endChange.setChangeAmount(allAmount);
		endChange.setChangeTime(checkDate);
		pamsDepositChangeDAO.insertDepositChange(endChange);
		pamsDepositRecordDAO.closeDepositRecordByDepositId(depositId);
		
	}

	/**
	 * 计算当前存款的总金额（含利息），不检查当前是否可以转出
	 */
	@Override
	public BigDecimal computeDepositRecordValue(Integer depositId, String checkDate) {
		DepositRecord record = pamsDepositRecordDAO.getDepositRecordByDepositId(depositId);
		List<DepositChange> changeList = pamsDepositChangeDAO.getDepositChangeListByDepositId(depositId);
		if(null == record || null == changeList || changeList.size() == 0) {
			return null;
		}
		if(record.getStatus() == DepositRecord.Status.InValid.toIntValue()) {
			return BigDecimal.valueOf(0.0);
		}
		if(record.getDepositTimeId() == DepositTimeEnum.NoTime.getIndex()) {
			//该存款账户为活期账户
			BigDecimal value = BigDecimal.ZERO;
			int daysOfYear = 360;
			float currentProfitPercent = record.getCurrentProfitPercent();
			String startDate = changeList.get(0).getChangeTime();
			for(DepositChange change : getChangeListBetweenDays(changeList, startDate, checkDate)) {
				int typeId = change.getChangeTypeId();
				String changeDate = change.getChangeTime();
				BigDecimal amount = change.getChangeAmount();
				int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, checkDate);
				if(typeId == DepositChange.ChangeType.Inflow.toIntValue() ||
						typeId == DepositChange.ChangeType.MakeAccount.toIntValue()) {				
					value = value.add(amount);
					value = value.add(amount.multiply(
							BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear /100)));
				} else if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
					value = value.subtract(amount);
					value = value.subtract(amount.multiply(
							BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear /100)));
				} 
			}
			return BigDecimalUtil.generateFormatNumber(value);
		} else {
			//该账户为定期账户
			int daysOfYear = 360;
			float currentProfitPercent = record.getCurrentProfitPercent();
			float fixedProfitPercent = record.getFixedProfitPercent();
			BigDecimal capital = changeList.get(0).getChangeAmount();
			BigDecimal interest = BigDecimal.ZERO;
			int daysCount = DepositTimeEnum.getTimeFromIndex(record.getDepositTimeId()).getDaysCount();
			String startDate = changeList.get(0).getChangeTime();
			String tmpDate = TimeRangeUtil.getSomedayPlusDays(startDate, daysCount);
			while(checkDate.compareTo(tmpDate) >= 0) {
				//检查日在一个存储周期之后，更新本金和利息，重置周期
				BigDecimal currentCapital = BigDecimal.ZERO;
				for(DepositChange change : getChangeListBetweenDays(changeList, startDate, tmpDate)) {
					int typeId = change.getChangeTypeId();
					String changeDate = change.getChangeTime();		
					if(typeId == DepositChange.ChangeType.Inflow.toIntValue()) {
						BigDecimal inflowAmount = change.getChangeAmount();
						int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, tmpDate);
						interest  = interest.add(inflowAmount.multiply(
								BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear /100)));
						currentCapital = currentCapital.add(inflowAmount);
					} else if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
						BigDecimal outflowAmount = change.getChangeAmount();
						if(outflowAmount.compareTo(currentCapital) > 0) {
							BigDecimal subtractCaptial = outflowAmount.subtract(currentCapital);
							int period1 = TimeRangeUtil.getPeriodDaysBetweenTwoDate(startDate, changeDate);
							int period2 = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, tmpDate);
							interest  = interest.add(subtractCaptial.multiply(
									BigDecimal.valueOf(currentProfitPercent * period1 / daysOfYear /100)));
							capital = capital.subtract(subtractCaptial);
							interest  = interest.subtract(currentCapital.multiply(
									BigDecimal.valueOf(currentProfitPercent * period2 / daysOfYear /100)));
							currentCapital = BigDecimal.ZERO;						
						} else {
							int period2 = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, tmpDate);
							interest  = interest.subtract(outflowAmount.multiply(
									BigDecimal.valueOf(currentProfitPercent * period2 / daysOfYear /100)));
							currentCapital = currentCapital.subtract(outflowAmount);	
						}
					} 
				}
				interest  = interest.add(capital.multiply(
						BigDecimal.valueOf(fixedProfitPercent * daysCount / daysOfYear /100)));
				capital = capital.add(interest);
				capital = capital.add(currentCapital);
				interest = BigDecimal.ZERO;
				startDate = tmpDate;
				tmpDate = TimeRangeUtil.getSomedayPlusDays(startDate, daysCount);
			}
			//此时检查日在一个存储周期之中，按活期计算总价值
			int capitalKeepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(startDate, checkDate);
			capital = capital.add(capital.multiply(
					BigDecimal.valueOf(currentProfitPercent * capitalKeepDays / daysOfYear /100)));
			for(DepositChange change : getChangeListBetweenDays(changeList, startDate, checkDate)) {
				int typeId = change.getChangeTypeId();
				String changeDate = change.getChangeTime();
				BigDecimal amount = change.getChangeAmount();
				int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, checkDate);
				if(typeId == DepositChange.ChangeType.Inflow.toIntValue()) {				
					capital = capital.add(amount);
					capital = capital.add(amount.multiply(
							BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear /100)));
				} else if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
					capital = capital.subtract(amount);
					capital = capital.subtract(amount.multiply(
							BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear /100)));
				} 
			}
			return BigDecimalUtil.generateFormatNumber(capital);
		}
	}

	/**
	 * 删除存款记录和相应的变更记录
	 */
	@Override
	public void deleteDepositRecordAndChange(Integer depositId) {
		pamsDepositRecordDAO.deleteDepositRecordByDepositId(depositId);
		pamsDepositChangeDAO.deleteDepositChangeByDepositId(depositId);
	}

	/**
	 * 获取当前所有有效的存款记录
	 */
	@Override
	public List<DepositRecord> getValidDepositRecordsByUserId(Integer userId) {
		List<DepositRecord> resultList = pamsDepositRecordDAO.getValidDepositRecordsByUserId(userId);
		if(null == resultList) {
			return new ArrayList<DepositRecord>();
		} else {
			return resultList;
		}
	}

	/**
	 * 根据某个存款获取其所有的变更记录
	 */
	@Override
	public List<DepositChange> getDepositChangeListByDepositId(Integer depositId) {
		List<DepositChange> resultList = pamsDepositChangeDAO.getDepositChangeListByDepositId(depositId);
		if(null == resultList) {
			return new ArrayList<DepositChange>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取某个用户的所有存款记录，不论是否有效
	 */
	@Override
	public List<DepositRecord> getAllDepositRecordsByUserId(Integer userId) {
		List<DepositRecord> resultList = pamsDepositRecordDAO.getAllDepositRecordsByUserId(userId);
		if(null == resultList) {
			return new ArrayList<DepositRecord>();
		} else {
			return resultList;
		}
	}

	
}
