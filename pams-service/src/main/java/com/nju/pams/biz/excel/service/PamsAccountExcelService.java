package com.nju.pams.biz.excel.service;

import java.util.Map;

/**
 * 关于的消费账目的excel下载接口，统一返回map类型的数据
 * @author yangyueayng
 */
public interface PamsAccountExcelService {
	
	/**
	 * 生成消费账目的每日统计报表
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Map<String, Object> getAccountOfDay(Integer userId, String startDate, String endDate);
	
	/**
	 * 生成消费账目的月份报表
	 * @param userId
	 * @param spendMonth
	 * @return
	 */
	Map<String, Object> getAccountOfMonth(Integer userId, String spendMonth);
	
	/**
	 * 生成消费账目的年度报表
	 * @param userId
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	Map<String, Object> getAccountOfYear(Integer userId, String spendYear);
}
