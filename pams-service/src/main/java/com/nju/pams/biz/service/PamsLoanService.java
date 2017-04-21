package com.nju.pams.biz.service;

import java.math.BigDecimal;
import java.util.List;

import com.nju.pams.model.asset.LoanChange;
import com.nju.pams.model.asset.LoanRecord;

public interface PamsLoanService {
	LoanRecord getLoanRecordByLoanId(Integer loanId);
	
	LoanChange getLoanChangeByChangeId(Integer changeId);
	
	List<LoanChange> getLoanChangeListByLoanId(Integer loanId);
	
	void makeLoanRecord(LoanRecord loanRecord, BigDecimal changeAmount, String changeTime);
	
	void insertLoanChange(LoanChange loanChange);
	
	void closeLoanRecord(Integer loanId);
	
	List<LoanRecord> getValidLoanRecordsByUserId(Integer userId);
	
	List<LoanRecord> getAllLoanRecordsByUserId(Integer userId);
	
	void deleteLoanRecordAndChange(Integer loanId);
	
	void deleteLoanChange(Integer changeId);
	
	void updateLoanRecord(LoanRecord loanRecord);
	
	BigDecimal computeSumAmountInRealLoanChange(Integer loanId);
	
}
