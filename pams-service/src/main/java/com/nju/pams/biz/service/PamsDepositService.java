package com.nju.pams.biz.service;

import java.math.BigDecimal;
import java.util.List;

import com.nju.pams.biz.model.vo.DepositOverallVO;
import com.nju.pams.model.asset.DepositChange;
import com.nju.pams.model.asset.DepositRecord;

public interface PamsDepositService {
	DepositRecord getDepositRecordByDepositId(Integer depositId);
	
	List<DepositRecord> getValidDepositRecordsByUserId(Integer userId);
	
	List<DepositRecord> getAllDepositRecordsByUserId(Integer userId);
	
	List<DepositChange> getDepositChangeListByDepositId(Integer depositId);
	
	void makeDepositRecord(DepositRecord depositRecord, BigDecimal changeAmount, String changeTime);

	boolean checkDateValid(Integer depositId, String checkDate);
	
	BigDecimal getMaxValidAmountForOutflow(Integer depositId, String checkDate);
	
	void insertDepositChangeForInflowAndOutflow(DepositChange depositChange);
	
	void closeDepositRecord(Integer depositId, String checkDate);
	
	BigDecimal computeDepositRecordValue(Integer depositId, String checkDate);
	
	void deleteDepositRecordAndChange(Integer depositId);
	
	DepositOverallVO getDepositOverall(Integer userId);
	
}
