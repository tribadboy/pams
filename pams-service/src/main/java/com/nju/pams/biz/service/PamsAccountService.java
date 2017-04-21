package com.nju.pams.biz.service;

import java.math.BigDecimal;
import java.util.List;

import com.nju.pams.model.consumption.ConsumptionAccount;
import com.nju.pams.model.consumption.ConsumptionCondition;

public interface PamsAccountService {
	ConsumptionAccount getConsumptionAccountByAccountId(Integer accountId);
	
	List<ConsumptionAccount> getConsumptionAccountListByUserId(Integer userId);
	
	List<ConsumptionAccount> selectAccountByCondition(ConsumptionCondition consumptionCondition);
	
	void insertConsumptionAccount(ConsumptionAccount consumptionAccount);
	
	void updateConsumptionAccount(ConsumptionAccount consumptionAccount);
	
	void deleteConsumptionAccountByAccountId(Integer accountId);
	
	BigDecimal computeAllConsumptionValue(Integer userId);
}
