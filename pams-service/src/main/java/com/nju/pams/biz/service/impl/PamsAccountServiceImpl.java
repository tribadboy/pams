package com.nju.pams.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsAccountService;
import com.nju.pams.mapper.dao.PamsAccountDAO;
import com.nju.pams.model.asset.ConsumptionAccount;
import com.nju.pams.model.asset.ConsumptionCondition;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsAccountServiceImpl implements PamsAccountService {
	
	@Autowired
	PamsAccountDAO pamsAccountDAO;

	@Override
	public ConsumptionAccount getConsumptionAccountByAccountId(Integer accountId) {
		return pamsAccountDAO.getConsumptionAccountByAccountId(accountId);
	}

	@Override
	public List<ConsumptionAccount> getConsumptionAccountListByUserId(Integer userId) {
		return pamsAccountDAO.getConsumptionAccountListByUserId(userId);
	}

	@Override
	public List<ConsumptionAccount> selectAccountByCondition(ConsumptionCondition consumptionCondition) {
		return pamsAccountDAO.selectAccountByCondition(consumptionCondition);
	}

	@Override
	public void insertConsumptionAccount(ConsumptionAccount consumptionAccount) {
		pamsAccountDAO.insertConsumptionAccount(consumptionAccount);
	}

	@Override
	public void updateConsumptionAccount(ConsumptionAccount consumptionAccount) {
		pamsAccountDAO.updateConsumptionAccount(consumptionAccount);
	}

	@Override
	public void deleteConsumptionAccountByAccountId(Integer accountId) {
		pamsAccountDAO.deleteConsumptionAccountByAccountId(accountId);
	}
	
}
