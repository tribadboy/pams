package com.nju.pams.biz.finance.service;

import java.util.List;

import com.nju.pams.finance.StockHistory;

public interface PamsStockHistoryService {
	
	//根据复合主键查询某一支股票的某天的历史数据
	StockHistory getStockHittoryByPK(String symbolDate, String symbolCode, Integer symbolType);	
	
	//查询某一支股票的某个阶段的历史数据
	List<StockHistory> getPeriodStockHittoryByPK(String symbolCode, Integer symbolType, String startDate,
	    		String endDate);
	 
	//批量添加或替换股票的历史数据
	int insertIgnoreStockHistoryList(List<StockHistory> stockHistoryList);
	
}
