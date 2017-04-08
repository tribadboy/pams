package com.nju.pams.biz.service;

import java.math.BigDecimal;

import com.nju.pams.model.asset.DepositChange;
import com.nju.pams.model.asset.DepositRecord;

public interface PamsDepositService {
	void makeDepositRecord(DepositRecord depositRecord, BigDecimal changeAmount, String changeTime);

	boolean checkDateValid(Integer depositId, String checkDate);
	
	BigDecimal getMaxValidAmountForOutflow(Integer depositId, String checkDate);
	
	void insertDepositChangeForInflowAndOutflow(DepositChange depositChange);
	
	void closeDepositRecord(Integer depositId, String checkDate);
	
	BigDecimal computeDepositRecordValue(Integer depositId, String checkDate);
	
	void deleteDepositRecordAndChange(Integer depositId);
	
}
