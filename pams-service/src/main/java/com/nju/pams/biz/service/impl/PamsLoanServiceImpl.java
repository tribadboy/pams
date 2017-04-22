package com.nju.pams.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.model.vo.LoanOverallVO;
import com.nju.pams.biz.service.PamsLoanService;
import com.nju.pams.mapper.dao.PamsLoanChangeDAO;
import com.nju.pams.mapper.dao.PamsLoanRecordDAO;
import com.nju.pams.model.asset.LoanChange;
import com.nju.pams.model.asset.LoanRecord;
import com.nju.pams.util.BigDecimalUtil;


@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsLoanServiceImpl implements PamsLoanService {
	
	@Autowired
	PamsLoanRecordDAO pamsLoanRecordDAO;
	
	@Autowired
	PamsLoanChangeDAO pamsLoanChangeDAO;
	
	/**
	 * 根据loanId 获取贷款信息
	 */
	@Override
	public LoanRecord getLoanRecordByLoanId(Integer loanId) {
		return pamsLoanRecordDAO.getLoanRecordByLoanId(loanId);
	}
	
	/**
	 * 根据changeId 获取还款信息
	 */
	@Override
	public LoanChange getLoanChangeByChangeId(Integer changeId) {
		return pamsLoanChangeDAO.getLoanChangeByChangeId(changeId);
	}

	/**
	 * 获取某个贷款的所有变更记录
	 */
	@Override
	public List<LoanChange> getLoanChangeListByLoanId(Integer loanId) {
		List<LoanChange> resultList = pamsLoanChangeDAO.getLoanChangeListByLoanId(loanId);
		if(null == resultList) {
			return new ArrayList<LoanChange>();
		} else {
			return resultList;
		}
	}

	/**
	 * 创建贷款
	 */
	@Override
	public void makeLoanRecord(LoanRecord loanRecord, BigDecimal changeAmount, String changeTime) {
		pamsLoanRecordDAO.insertLoanRecord(loanRecord);
		LoanChange makeLoan = new LoanChange();
		makeLoan.setLoanId(loanRecord.getLoanId());
		makeLoan.setChangeTypeId(LoanChange.ChangeType.MakeLoan.getIndex());
		makeLoan.setChangeAmount(changeAmount);
		makeLoan.setChangeTime(changeTime);
		pamsLoanChangeDAO.insertLoanChange(makeLoan);
	}

	/**
	 * 插入还款记录
	 */
	@Override
	public void insertLoanChange(LoanChange loanChange) {
		if(null == loanChange || loanChange.getChangeTypeId() == LoanChange.ChangeType.MakeLoan.getIndex()) {
			return;
		}
		pamsLoanChangeDAO.insertLoanChange(loanChange);
	}

	/**
	 * 结束某个贷款
	 */
	@Override
	public void closeLoanRecord(Integer loanId) {
		pamsLoanRecordDAO.closeLoanRecordByLoanId(loanId);
	}

	/**
	 * 删除某个贷款及其相关的还款
	 */
	@Override
	public void deleteLoanRecordAndChange(Integer loanId) {
		pamsLoanRecordDAO.deleteLoanRecordByLoanId(loanId);
		pamsLoanChangeDAO.deleteLoanChangeByLoanId(loanId);
	}

	/**
	 * 删除某项贷款变更记录
	 */
	@Override
	public void deleteLoanChange(Integer changeId) {
		LoanChange change = pamsLoanChangeDAO.getLoanChangeByChangeId(changeId);
		if(null == change) {
			return;
		}
		if(change.getChangeTypeId() == LoanChange.ChangeType.MakeLoan.getIndex()) {
			deleteLoanRecordAndChange(change.getLoanId());
		} else {
			pamsLoanChangeDAO.deleteLoanChangeByChangeId(changeId);
		}
	}

	/**
	 * 更新某个贷款
	 */
	@Override
	public void updateLoanRecord(LoanRecord loanRecord) {
		pamsLoanRecordDAO.updateLoanRecord(loanRecord);
	}

	/**
	 * 获取某个用户的所有有效的贷款
	 */
	@Override
	public List<LoanRecord> getValidLoanRecordsByUserId(Integer userId) {
		List<LoanRecord> resultList = pamsLoanRecordDAO.getValidLoanRecordsByUserId(userId);
		if(null == resultList) {
			return new ArrayList<LoanRecord>();
		} else {
			return resultList;
		}
	}
	
	/**
	 * 获取某个用户的所有无效的贷款
	 * @param userId
	 * @return
	 */
	@Override
	public List<LoanRecord> getInvalidLoanRecordsByUserId(Integer userId) {
		List<LoanRecord> resultList = pamsLoanRecordDAO.getInvalidLoanRecordsByUserId(userId);
		if(null == resultList) {
			return new ArrayList<LoanRecord>();
		} else {
			return resultList;
		}
	}

	/**
	 * 活期某个所有的贷款
	 */
	@Override
	public List<LoanRecord> getAllLoanRecordsByUserId(Integer userId) {
		List<LoanRecord> resultList = pamsLoanRecordDAO.getAllLoanRecordsByUserId(userId);
		if(null == resultList) {
			return new ArrayList<LoanRecord>();
		} else {
			return resultList;
		}
	}

	/**
	 * 计算累计贷还款数额，不考虑时间
	 */
	@Override
	public BigDecimal computeSumAmountInRealLoanChange(Integer loanId) {
		BigDecimal result = BigDecimal.ZERO;
		List<LoanChange> changeList = getLoanChangeListByLoanId(loanId);
		if(CollectionUtils.isNotEmpty(changeList)) {
			for(LoanChange change : changeList) {
				if(change.getChangeTypeId() == LoanChange.ChangeType.Repay.getIndex()) {
					result = result.add(change.getChangeAmount());
				}
			}
		}
		return BigDecimalUtil.generateFormatNumber(result);	
	}

	@Override
	public LoanOverallVO getLoanOverall(Integer userId) {
		int countOfValidInflow = 0;								//进行中的贷入贷款
		int countOfValidOutflow = 0;							//进行中的贷出贷款
		int countOfInvalidInflow = 0;							//已结束的贷入贷款
		int countOfInvalidOutflow = 0;							//已结束的贷出贷款
		BigDecimal validValue = BigDecimal.ZERO;				//进行中的贷款差值
		BigDecimal invalidValue = BigDecimal.ZERO;				//已结束的贷款差值
		BigDecimal exceptValue = BigDecimal.ZERO;				//预期剩余的贷款差值
		
		//遍历所有贷款
		List<LoanRecord> allList = pamsLoanRecordDAO.getAllLoanRecordsByUserId(userId);
		if(CollectionUtils.isNotEmpty(allList)) {
			for(LoanRecord record : allList) {
				int direction = record.getDirection();
				int status = record.getStatus();
				//计算贷款的差值
				BigDecimal value = BigDecimal.ZERO;
				BigDecimal repay = BigDecimal.ZERO;
				List<LoanChange> changeList = getLoanChangeListByLoanId(record.getLoanId());
				if(CollectionUtils.isNotEmpty(changeList)) {
					for(LoanChange change : changeList) {
						if(change.getChangeTypeId() == LoanChange.ChangeType.Repay.getIndex()) {
							value = value.subtract(change.getChangeAmount());
							repay = repay.add(change.getChangeAmount());
						} else if(change.getChangeTypeId() == LoanChange.ChangeType.MakeLoan.getIndex()) {
							value = value.add(change.getChangeAmount());
						}
					}
				}
				if(direction == LoanRecord.Direction.Inflow.toIndex()) {
					if(status == LoanRecord.Status.InValid.getIndex()) {
						countOfInvalidInflow++;			
						invalidValue = invalidValue.add(value);
					} else if(status == LoanRecord.Status.Valid.getIndex()) {
						countOfValidInflow++;
						validValue = validValue.add(value);
						exceptValue = exceptValue.add(repay).subtract(record.getExceptRepayAmount());
					}
				} else if(direction == LoanRecord.Direction.Outflow.toIndex()){
					if(status == LoanRecord.Status.InValid.getIndex()) {
						countOfInvalidOutflow++;
						invalidValue = invalidValue.subtract(value);
					} else if(status == LoanRecord.Status.InValid.getIndex()) {
						countOfValidOutflow++;
						validValue = validValue.subtract(value);
						exceptValue = exceptValue.add(record.getExceptRepayAmount()).subtract(repay);
					}
				}
			}
		}
		return new LoanOverallVO(countOfValidInflow, countOfValidOutflow, countOfInvalidInflow,
				countOfInvalidOutflow, BigDecimalUtil.generateFormatNumber(validValue), 
				BigDecimalUtil.generateFormatNumber(invalidValue), BigDecimalUtil.generateFormatNumber(exceptValue));
		
	}
	
	
}
