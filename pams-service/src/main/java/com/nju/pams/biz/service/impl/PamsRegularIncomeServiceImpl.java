package com.nju.pams.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsRegularIncomeService;
import com.nju.pams.mapper.dao.PamsRegularIncomeDAO;
import com.nju.pams.model.asset.RegularIncome;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsRegularIncomeServiceImpl implements PamsRegularIncomeService {
	
	@Autowired
	PamsRegularIncomeDAO pamsRegularIncomeDAO;

	@Override
	public RegularIncome getRegularIncomeByIncomeId(Integer incomeId) {
		return pamsRegularIncomeDAO.getRegularIncomeByIncomeId(incomeId);
	}

	@Override
	public List<RegularIncome> getRegularIncomeListByUserId(Integer userId) {
		return pamsRegularIncomeDAO.getRegularIncomeListByUserId(userId);
	}

	@Override
	public List<RegularIncome> getRegularIncomeListByUserIdInPeriodTime(Integer userId, String startDate,
			String endDate) {
		return pamsRegularIncomeDAO.getRegularIncomeListByUserIdInPeriodTime(userId, startDate, endDate);
	}

	@Override
	public void insertRegularIncome(RegularIncome regularIncome) {
		pamsRegularIncomeDAO.insertRegularIncome(regularIncome);
	}

	@Override
	public void updateRegularIncome(RegularIncome regularIncome) {
		pamsRegularIncomeDAO.updateRegularIncome(regularIncome);
	}

	@Override
	public void deleteRegularIncomeByIncomeId(Integer incomeId) {
		pamsRegularIncomeDAO.deleteRegularIncomeByIncomeId(incomeId);
	}

	
}
