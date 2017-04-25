package com.nju.pams.biz.finance.service;

import java.util.List;

import com.nju.pams.finance.PamsStock;

public interface PamsStockService {
	
	//获取某一支股票的信息
	PamsStock getPamsStockBySymbolCodeAndSymbolType(String symbolCode, Integer symbolType);
	
	//获取某个证券市场的所有有效的股票信息
	List<PamsStock> getValidPamsStocksBySymbolType(Integer symbolType);

	//批量添加或替换股票信息
	int replaceStocksList(List<PamsStock> stocksList);
	
	//设置某个股票市场的所有股票为无效状态
	void setStocksInvalidBySymbolType(Integer symbolType);
	
	//模糊查询
	List<PamsStock> getPamsStocksByKey(String key);
	
	//获取所有股票
	List<PamsStock> getAllPamsStocks();
	
}
