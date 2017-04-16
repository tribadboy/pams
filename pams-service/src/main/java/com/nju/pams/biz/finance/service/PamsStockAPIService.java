package com.nju.pams.biz.finance.service;

import java.util.List;
import java.util.Map;

import com.nju.pams.finance.StockHistory;

public interface PamsStockAPIService {
	
	//通过api获取[startCode, endCode)的最新股票数据  沪市A股
	String searchStockInfoBetweenStartCodeAndEndCodeUsingAPI(int startCode, int endCode);
	
	//通过api获取codeList中的所有最新的股票数据  沪市A股
	String searchStockInfoBetweenCodeListUsingAPI(List<String> codeList);
	
	//通过api获取某只特定股票的最新数据    沪市A股
	String searchStockOnCertainCodeUsingAPI(String code);
	
	//通过api获取某只股票在某一年的历史数据   沪市A股
	String searchStockHistoryInCertainYearUsingAPI(String code, int year);
	
	//将获取的字符串数据转换成股票对象集合
	List<Map<String, Object>> getStocksFromStockInfo(String result);
	
	//将获取的字符串数据转换成股票历史对象集合
	List<StockHistory> getStockHistorysFromStockHistoryInfo(String result);
	
}
