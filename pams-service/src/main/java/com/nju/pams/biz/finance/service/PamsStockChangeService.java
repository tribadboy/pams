package com.nju.pams.biz.finance.service;

import java.math.BigDecimal;
import java.util.List;

import com.nju.pams.finance.StockChange;

public interface PamsStockChangeService {
	//获取最新变更时间
	String getMaxTradeTimeByUserId(int userId);
	
	//获取某个用户的所有股票变更记录，并按照时间排序
	List<StockChange> getStockChangeListByUserId(Integer userId);
	
	//获取某个用户的累计投入，累计转入减去累计转出
	BigDecimal getTotalInvestmentByUserId(Integer userId);
	
	//计算股票买卖时的税费
	BigDecimal getFeeForStock(int changeTypeId, BigDecimal price, int quantity);
	
	//计算股票买卖时的总价
	BigDecimal getTotalForStock(int changeTypeId, BigDecimal price, int quantity);
	
	//股票的数量验证，卖出时不应超过持有量，且买卖有特殊的要求
	boolean stockQuantityVerification(int userId, int changeTypeId, String symbolCode, 
			int symbolType, int quantity);
	
	//资金验证，转出的资金验证以及买入的资金验证
	boolean stockCaptialVerification(int userId, BigDecimal total);
	
	//时间验证，新增记录的时间必须是最新的
	boolean stockTimeVerification(int userId, String targetTime);
	
	//插入一条股票变更记录，会连带影响股票资金表和用户持股表
	boolean insertStockChange(StockChange stockChange);
	
	//删除最新的股票变更记录，会连带影响股票资金表和用户持股表
	boolean cancelStockChange(int changeId);
}
