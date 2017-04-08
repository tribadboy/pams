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
			for(DepositChange change : changeList) {
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
				for(DepositChange change : getChangeListBetweenDays(changeList, startDate, tmpDate)) {
					int typeId = change.getChangeTypeId();
					String changeDate = change.getChangeTime();
					if(typeId == DepositChange.ChangeType.Inflow.toIntValue()) {
						BigDecimal inflowAmount = change.getChangeAmount();
						int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, tmpDate);
						interest  = interest.add(inflowAmount.multiply(
								BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear)));
						interest = interest.add(inflowAmount);
					} else if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
						BigDecimal outflowAmount = change.getChangeAmount();
						int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(startDate, changeDate);
						interest  = interest.add(outflowAmount.multiply(
								BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear)));
						capital = capital.subtract(outflowAmount);
					} 
				}
				interest  = interest.add(capital.multiply(
						BigDecimal.valueOf(fixedProfitPercent * daysCount / daysOfYear)));
				capital = capital.add(interest);
				interest = BigDecimal.ZERO;
				startDate = tmpDate;
				tmpDate = TimeRangeUtil.getSomedayPlusDays(startDate, daysCount);
			}
			//此时检查日在一个存储周期之中，可转出的最大值为 该周期的本金累计减去周期内转出，得出的剩余额度
			for(DepositChange change : getChangeListBetweenDays(changeList, startDate, tmpDate)) {
				int typeId = change.getChangeTypeId();
				if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
					capital = capital.subtract(change.getChangeAmount());
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

	@Override
	public void insertDepositChangeForInflowAndOutflow(DepositChange depositChange) {
		pamsDepositChangeDAO.insertDepositChange(depositChange);
	}

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

	@Override
	public BigDecimal computeDepositRecordValue(Integer depositId, String checkDate) {
		DepositRecord record = pamsDepositRecordDAO.getDepositRecordByDepositId(depositId);
		List<DepositChange> changeList = pamsDepositChangeDAO.getDepositChangeListByDepositId(depositId);
		if(null == record || null == changeList || changeList.size() == 0) {
			return null;
		}
		if(record.getDepositTimeId() == DepositTimeEnum.NoTime.getIndex()) {
			//该存款账户为活期账户
			BigDecimal value = BigDecimal.ZERO;
			int daysOfYear = 360;
			float currentProfitPercent = record.getCurrentProfitPercent();
			for(DepositChange change : changeList) {
				int typeId = change.getChangeTypeId();
				String changeDate = change.getChangeTime();
				BigDecimal amount = change.getChangeAmount();
				int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, checkDate);
				if(typeId == DepositChange.ChangeType.Inflow.toIntValue() ||
						typeId == DepositChange.ChangeType.MakeAccount.toIntValue()) {				
					value = value.add(amount);
					value = value.add(amount.multiply(
							BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear)));
				} else if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
					value = value.subtract(amount);
					value = value.subtract(amount.multiply(
							BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear)));
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
				for(DepositChange change : getChangeListBetweenDays(changeList, startDate, tmpDate)) {
					int typeId = change.getChangeTypeId();
					String changeDate = change.getChangeTime();
					if(typeId == DepositChange.ChangeType.Inflow.toIntValue()) {
						BigDecimal inflowAmount = change.getChangeAmount();
						int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, tmpDate);
						interest  = interest.add(inflowAmount.multiply(
								BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear)));
						interest = interest.add(inflowAmount);
					} else if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
						BigDecimal outflowAmount = change.getChangeAmount();
						int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(startDate, changeDate);
						interest  = interest.add(outflowAmount.multiply(
								BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear)));
						capital = capital.subtract(outflowAmount);
					} 
				}
				interest  = interest.add(capital.multiply(
						BigDecimal.valueOf(fixedProfitPercent * daysCount / daysOfYear)));
				capital = capital.add(interest);
				interest = BigDecimal.ZERO;
				startDate = tmpDate;
				tmpDate = TimeRangeUtil.getSomedayPlusDays(startDate, daysCount);
			}
			//此时检查日在一个存储周期之中，按活期计算总价值
			int capitalKeepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(startDate, checkDate);
			capital = capital.add(capital.multiply(
					BigDecimal.valueOf(currentProfitPercent * capitalKeepDays / daysOfYear)));
			for(DepositChange change : getChangeListBetweenDays(changeList, startDate, tmpDate)) {
				int typeId = change.getChangeTypeId();
				String changeDate = change.getChangeTime();
				BigDecimal amount = change.getChangeAmount();
				int keepDays = TimeRangeUtil.getPeriodDaysBetweenTwoDate(changeDate, checkDate);
				if(typeId == DepositChange.ChangeType.MakeAccount.toIntValue()) {				
					capital = capital.add(amount);
					capital = capital.add(amount.multiply(
							BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear)));
				} else if(typeId == DepositChange.ChangeType.Outflow.toIntValue()) {
					capital = capital.subtract(amount);
					capital = capital.subtract(amount.multiply(
							BigDecimal.valueOf(currentProfitPercent * keepDays / daysOfYear)));
				} 
			}
			return BigDecimalUtil.generateFormatNumber(capital);
		}
	}

	@Override
	public void deleteDepositRecordAndChange(Integer depositId) {
		pamsDepositRecordDAO.deleteDepositRecordByDepositId(depositId);
		pamsDepositChangeDAO.deleteDepositChangeByDepositId(depositId);
	}

	
}
