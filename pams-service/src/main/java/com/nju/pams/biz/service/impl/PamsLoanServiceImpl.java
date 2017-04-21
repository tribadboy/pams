package com.nju.pams.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
		return pamsLoanRecordDAO.getValidLoanRecordsByUserId(userId);
	}

	/**
	 * 活期某个所有的贷款
	 */
	@Override
	public List<LoanRecord> getAllLoanRecordsByUserId(Integer userId) {
		return pamsLoanRecordDAO.getAllLoanRecordsByUserId(userId);
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
	
	
}
