package com.nju.pams.biz.service;

import java.math.BigDecimal;
import java.util.List;

import com.nju.pams.finance.PamsStrategy;
import com.nju.pams.finance.StrategyElement;

public interface PamsStrategyService {
	PamsStrategy getPamsStrategyByStrategyId(Integer strategyId);
	
	List<PamsStrategy> getPamsStrategyList();
	
	List<PamsStrategy> getPamsStrategyListByUserId(Integer userId);
	
	List<PamsStrategy> getPamsStrategyListByUserIdAndStrategyType(Integer userId, Integer strategyType);
	
	List<PamsStrategy> getPamsStrategyListByStrategyType(Integer strategyType);
	
	List<PamsStrategy> getPamsStrategyListByStatus(Integer status);
	
	List<PamsStrategy> getPamsStrategyListByStatusAndStrategyType(Integer status, Integer strategyType);
	
	List<PamsStrategy> getPamsStrategyListByStatusAndStrategyTypeAndUserId(Integer status, 
			Integer strategyType, Integer userId);
	
	List<PamsStrategy> getPamsStrategyByKeyAndStrategyType(String key, Integer strategyType);
	
	List<StrategyElement> getStrategyElementListByStrategyId(Integer strategyId);
	
	void insertPamsStrategy(PamsStrategy pamsStrategy, List<StrategyElement> elementList);
	
	void setAvgProfitByStrategyId(Integer strategyId, BigDecimal avgProfit);
	
	void deletePamsStrategyByStrategyId(Integer strategyId);
	
	void setNotStartByTodayStr(String today);
	
	void setOngoingByTodayStr(String today);
	
	void setWindUpByTodayStr(String today);
	
	void setClosedByTodayStr(String today);
	
}
