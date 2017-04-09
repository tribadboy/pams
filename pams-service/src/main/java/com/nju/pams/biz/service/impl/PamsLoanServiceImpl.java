package com.nju.pams.biz.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsLoanService;
import com.nju.pams.mapper.dao.PamsLoanChangeDAO;
import com.nju.pams.mapper.dao.PamsLoanRecordDAO;
import com.nju.pams.model.asset.LoanChange;
import com.nju.pams.model.asset.LoanRecord;


@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsLoanServiceImpl implements PamsLoanService {
	
	@Autowired
	PamsLoanRecordDAO pamsLoanRecordDAO;
	
	@Autowired
	PamsLoanChangeDAO pamsLoanChangeDAO;

	@Override
	public List<LoanChange> getLoanChangeListByLoanId(Integer loanId) {
		return pamsLoanChangeDAO.getLoanChangeListByLoanId(loanId);
	}

	@Override
	public void makeLoanRecord(LoanRecord loanRecord, BigDecimal changeAmount, String changeTime) {
		if(null == loanRecord || null == changeAmount || null == changeTime) {
			return;
		}
		pamsLoanRecordDAO.insertLoanRecord(loanRecord);
		LoanChange makeLoan = new LoanChange();
		makeLoan.setLoanId(loanRecord.getLoanId());
		makeLoan.setChangeTypeId(LoanChange.ChangeType.MakeLoan.getIndex());
		makeLoan.setChangeAmount(changeAmount);
		makeLoan.setChangeTime(changeTime);
		pamsLoanChangeDAO.insertLoanChange(makeLoan);
	}

	@Override
	public void insertLoanChange(LoanChange loanChange) {
		if(null == loanChange || loanChange.getChangeTypeId() == LoanChange.ChangeType.MakeLoan.getIndex()) {
			return;
		}
		pamsLoanChangeDAO.insertLoanChange(loanChange);
	}

	@Override
	public void closeLoanRecord(Integer loanId) {
		pamsLoanRecordDAO.closeLoanRecordByLoanId(loanId);
	}

	@Override
	public void deleteLoanRecordAndChange(Integer loanId) {
		pamsLoanRecordDAO.deleteLoanRecordByLoanId(loanId);
		pamsLoanChangeDAO.deleteLoanChangeByLoanId(loanId);
	}

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

	@Override
	public void updateLoanRecord(LoanRecord loanRecord) {
		pamsLoanRecordDAO.updateLoanRecord(loanRecord);
	}

	@Override
	public List<LoanRecord> getValidLoanRecordsByUserId(Integer userId) {
		return pamsLoanRecordDAO.getValidLoanRecordsByUserId(userId);
	}

	@Override
	public List<LoanRecord> getAllLoanRecordsByUserId(Integer userId) {
		return pamsLoanRecordDAO.getAllLoanRecordsByUserId(userId);
	}
	
	
}
