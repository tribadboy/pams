package com.nju.pams.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsAccountMonthService;
import com.nju.pams.mapper.dao.PamsAccountMonthDAO;
import com.nju.pams.model.consumption.AccountOfMonth;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsAccountMonthServiceImpl implements PamsAccountMonthService {
	
	@Autowired
	PamsAccountMonthDAO pamsAccountMonthDAO;

	@Override
	public List<AccountOfMonth> getAccountOfMonthListByCertainMonth(Integer userId, String spendMonth) {
		return pamsAccountMonthDAO.getAccountOfMonthListByCertainMonth(userId, spendMonth);
	}

	@Override
	public List<AccountOfMonth> getAccountOfMonthListByPeriodMonth(Integer userId, String startMonth, String endMonth) {
		return pamsAccountMonthDAO.getAccountOfMonthListByPeriodMonth(userId, startMonth, endMonth);
	}

}
