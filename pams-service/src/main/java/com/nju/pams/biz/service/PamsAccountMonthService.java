package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.asset.AccountOfMonth;

public interface PamsAccountMonthService {
	
	/**
	 * 查询某个用户月度消费列表
	 * @param userId
	 * @param spendMonth
	 * @return
	 */
	List<AccountOfMonth> getAccountOfMonthListByCertainMonth(Integer userId,String spendMonth);
	
	/**
	 * 查询某个用户在某几个月的消费列表	[startMonth, endMonth)
	 * @param userId
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	List<AccountOfMonth> getAccountOfMonthListByPeriodMonth(Integer userId,
			String startMonth, String endMonth);
}
