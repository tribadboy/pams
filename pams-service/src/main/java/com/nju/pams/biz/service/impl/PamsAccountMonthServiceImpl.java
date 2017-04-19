package com.nju.pams.biz.service.impl;

import java.util.ArrayList;
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
		List<AccountOfMonth> resultList = pamsAccountMonthDAO.getAccountOfMonthListByCertainMonth(userId, spendMonth);
		if(null == resultList) {
			return new ArrayList<AccountOfMonth>();
		} else {
			return resultList;
		}
	}

	@Override
	public List<AccountOfMonth> getAccountOfMonthListByPeriodMonth(Integer userId, String startMonth, String endMonth) {
		List<AccountOfMonth> resultList = pamsAccountMonthDAO.getAccountOfMonthListByPeriodMonth(userId, startMonth, endMonth);
		if(null == resultList) {
			return new ArrayList<AccountOfMonth>();
		} else {
			return resultList;
		}
	}

}
