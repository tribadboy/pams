package com.nju.pams.biz.excel.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nju.pams.biz.excel.service.PamsAccountExcelService;
import com.nju.pams.biz.model.vo.AccountExcelVO;
import com.nju.pams.biz.model.vo.AccountOfMonthExcelVO;
import com.nju.pams.biz.service.PamsAccountMonthService;
import com.nju.pams.biz.service.PamsAccountService;
import com.nju.pams.model.asset.AccountOfMonth;
import com.nju.pams.model.asset.ConsumptionAccount;
import com.nju.pams.model.asset.ConsumptionCondition;
import com.nju.pams.model.asset.ConsumptionEnum;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.constant.CommonTime;

import net.sf.json.JSONArray;

@Service
//@Transactional(propagation=Propagation.REQUIRED)
public class PamsAccountExcelServiceImpl implements PamsAccountExcelService {
	
	@Autowired
	PamsAccountService pamsAccountService;
	
	@Autowired
	PamsAccountMonthService pamsAccountMonthService;

	@Override
	public Map<String, Object> getAccountOfDay(Integer userId, String startDate, String endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//创建搜索条件
		ConsumptionCondition condition = new ConsumptionCondition(userId, null, startDate, endDate, null, null);
		//得到返回结果list
		List<ConsumptionAccount> accountList = pamsAccountService.selectAccountByCondition(condition);
		JSONArray dataArray = new JSONArray();
		if(CollectionUtils.isNotEmpty(accountList)) {
			for(ConsumptionAccount account : accountList) {
				dataArray.add(AccountExcelVO.generateVOFromObj(account));
			}
		}
		resultMap.put("accountList", dataArray);
		return resultMap;	
	}

	@Override
	public Map<String, Object> getAccountOfMonth(Integer userId, String spendMonth) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//得到返回结果list
		List<AccountOfMonth> accountMonthList = pamsAccountMonthService
				.getAccountOfMonthListByCertainMonth(userId, spendMonth);
		//将list转化成以type为key的map结构
		Map<Integer, AccountOfMonth> typeMap = new HashMap<Integer, AccountOfMonth>();
		if(CollectionUtils.isNotEmpty(accountMonthList)) {
			for(AccountOfMonth am : accountMonthList) {
				typeMap.put(am.getConsumptionId(), am);
			}
		}
		//遍历枚举，没有在map中找到的创建新对象
		for(ConsumptionEnum type : ConsumptionEnum.values()) {
			int key = type.getCode();
			if(typeMap.containsKey(key)) {
				resultMap.put("item" + key, AccountOfMonthExcelVO.generateVOFromObj(typeMap.get(key)));
			} else {
				resultMap.put("item" + key, new AccountOfMonthExcelVO());
			}
		}
		resultMap.put("spendMonth", spendMonth);
		return resultMap;
	}

	@Override
	public Map<String, Object> getAccountOfYear(Integer userId, String spendYear) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//得到返回结果list
		List<AccountOfMonth> accountMonthList = pamsAccountMonthService
				.getAccountOfMonthListByPeriodMonth(userId, spendYear+"-01", spendYear+"-13");
		//填写每个月下不同类别的消费总额
		if(CollectionUtils.isNotEmpty(accountMonthList)) {
			for(AccountOfMonth account : accountMonthList) {
				//取出 2017-07  中的  07
				String month = account.getSpendMonth().substring(5, 7);
				resultMap.put("item" + month + "_" + account.getConsumptionId(), 
						BigDecimalUtil.generateFormatNumber(account.getCost()));
			}
		}
		//计算不同类别的总和以及不同月份的总额
		BigDecimal all = BigDecimal.ZERO;
		for(ConsumptionEnum type : ConsumptionEnum.values()) {
			BigDecimal typeAll = BigDecimal.ZERO;
			for(CommonTime.Month month : CommonTime.Month.values()) {
				String key = "item" + month.getName() + "_" + type.getCode();
				if(resultMap.containsKey(key)) {
					typeAll = typeAll.add((BigDecimal) resultMap.get(key));
				}
			}
			all = all.add(typeAll);
			resultMap.put("itemall_" + type.getCode(), BigDecimalUtil.generateFormatNumber(typeAll));
		}
		for(CommonTime.Month month : CommonTime.Month.values()) {
			BigDecimal monthAll = BigDecimal.ZERO;
			for(ConsumptionEnum type : ConsumptionEnum.values()) {
				String key = "item" + month.getName() + "_" + type.getCode();
				if(resultMap.containsKey(key)) {
					monthAll = monthAll.add((BigDecimal) resultMap.get(key));
				}
			}
			resultMap.put("item" + month.getName() + "_all", BigDecimalUtil.generateFormatNumber(monthAll));
		}
		resultMap.put("all", BigDecimalUtil.generateFormatNumber(all));
		resultMap.put("spendYear", spendYear);
		return resultMap;
	}
	

}
