package com.nju.pams.biz.service;

import java.math.BigDecimal;
import java.util.List;

import com.nju.pams.biz.model.vo.RegularIncomeOverallVO;
import com.nju.pams.model.asset.RegularIncome;

public interface PamsRegularIncomeService {
	RegularIncome getRegularIncomeByIncomeId(Integer incomeId);
	
	List<RegularIncome> getRegularIncomeListByUserId(Integer userId);
	
	List<RegularIncome> getRegularIncomeListByUserIdInPeriodTime(Integer userId,String startDate,String endDate);
	
	void insertRegularIncome(RegularIncome regularIncome);
	
	void updateRegularIncome(RegularIncome regularIncome);
	
	void deleteRegularIncomeByIncomeId(Integer incomeId);
	
	BigDecimal computeAllConsumptionValue(Integer userId);
	
	RegularIncomeOverallVO getRegularIncomeOverallVO(Integer userId);
}
