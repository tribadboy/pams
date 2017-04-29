package com.nju.pams.biz.finance.service;

import java.util.List;

import com.nju.pams.finance.StockHistory;

public interface PamsStockHistoryService {
	
	//根据复合主键查询某一支股票的某天的历史数据
	StockHistory getStockHistoryByPK(String symbolDate, String symbolCode, Integer symbolType);	
	
	//查询某一支股票的某个阶段的历史数据
	List<StockHistory> getPeriodStockHistoryByPK(String symbolCode, Integer symbolType, String startDate,
	    		String endDate);
	
	String getCellDateByPK(String symbolCode, Integer symbolType, String targetDate);
	
	String getFloorDateByPK(String symbolCode, Integer symbolType, String targetDate);
	
	//查询某一支股票的某个阶段的历史数据
	List<StockHistory> getAllStockHistoryByPK(String symbolCode, Integer symbolType);
	
	//查询某一支股票的有数据的最大日期
	String getMaxDateByPK(String symbolCode, Integer symbolType);
	
	//查询某一支股票的有数据的最小日期
	String getMinDateByPK(String symbolCode, Integer symbolType);
	 
	//批量添加或替换股票的历史数据
	int insertIgnoreStockHistoryList(List<StockHistory> stockHistoryList);
	
	//获取历史数据的最大时间
	String getMaxDate();
	
}
