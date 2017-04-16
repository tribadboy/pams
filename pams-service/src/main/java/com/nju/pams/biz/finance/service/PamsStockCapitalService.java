package com.nju.pams.biz.finance.service;

import java.math.BigDecimal;

public interface PamsStockCapitalService {
	//查询用户的股票资金
	BigDecimal getStockCapitalAmountByUserId(Integer userId);
	
	//增加用户的股票资金
	void increaseStockCapitalAmount(Integer userId, BigDecimal num);
	
	//减少用户的股票资金
	void decreaseStockCaptialAmount(Integer userId, BigDecimal num);
	
}
