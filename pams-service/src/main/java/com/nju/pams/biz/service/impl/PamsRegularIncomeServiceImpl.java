package com.nju.pams.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.model.vo.RegularIncomeOverallVO;
import com.nju.pams.biz.service.PamsRegularIncomeService;
import com.nju.pams.mapper.dao.PamsRegularIncomeDAO;
import com.nju.pams.model.asset.RegularIncome;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.EmptyUtil;

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
		List<RegularIncome> resultList = pamsRegularIncomeDAO.getRegularIncomeListByUserId(userId);
		if(null == resultList) {
			return new ArrayList<RegularIncome>();
		} else {
			return resultList;
		}
	}

	@Override
	public List<RegularIncome> getRegularIncomeListByUserIdInPeriodTime(Integer userId, String startDate,
			String endDate) {
		List<RegularIncome> resultList = pamsRegularIncomeDAO.getRegularIncomeListByUserIdInPeriodTime(userId, startDate, endDate);
		if(null == resultList) {
			return new ArrayList<RegularIncome>();
		} else {
			return resultList;
		}
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

	@Override
	public BigDecimal computeAllConsumptionValue(Integer userId) {
		BigDecimal result = BigDecimal.ZERO;
		List<RegularIncome> resultList = pamsRegularIncomeDAO.getRegularIncomeListByUserId(userId);
		if(CollectionUtils.isNotEmpty(resultList)) {
			for(RegularIncome income : resultList) {
				result = result.add(income.getRecordAmount());
			}
		}
		return BigDecimalUtil.generateFormatNumber(result);
	}

	@Override
	public RegularIncomeOverallVO getRegularIncomeOverallVO(Integer userId) {
		BigDecimal result = BigDecimal.ZERO;
		int count = 0;
		List<RegularIncome> resultList = pamsRegularIncomeDAO.getRegularIncomeListByUserId(userId);
		if(CollectionUtils.isNotEmpty(resultList)) {
			for(RegularIncome income : resultList) {
				result = result.add(income.getRecordAmount());
			}
			count = resultList.size();
		}
		String maxDate = EmptyUtil.notEmtpyProcess(pamsRegularIncomeDAO.getMaxDateByUserId(userId));
		String minDate = EmptyUtil.notEmtpyProcess(pamsRegularIncomeDAO.getMinDateByUserId(userId));
		return new RegularIncomeOverallVO(BigDecimalUtil.generateFormatNumber(result), count, minDate, maxDate);
	}

	
}
