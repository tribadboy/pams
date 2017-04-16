package com.nju.pams.biz.finance.service;

import java.util.List;

import com.nju.pams.finance.StockHold;

public interface PamsStockHoldService {
	//查询用户的所有持股情况
	List<StockHold> getStockHoldListByUserId(Integer userId);
	
	//查询用户的某支股票的持股情况
	int getStockHoldByUserIdAndStockInfo(Integer userId, String symbolCode,Integer symbolType);
	
	//增加用户的持股（主动购买，或者撤销上一次的卖出）
	void increaseStockHoldQuantity(Integer userId, String symbolCode, Integer symbolType, int num);
	
	//减少用户的持股（主动卖出，或者撤销上一次的买入）
	void decreaseStockHoldQuantity(Integer userId, String symbolCode, Integer symbolType, int num);
	
}
