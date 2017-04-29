package com.nju.pams.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsStrategyService;
import com.nju.pams.finance.PamsStrategy;
import com.nju.pams.finance.StrategyElement;
import com.nju.pams.mapper.dao.PamsStrategyDAO;
import com.nju.pams.mapper.dao.PamsStrategyElementDAO;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsStrategyServiceImpl implements PamsStrategyService {
	
	@Autowired
	PamsStrategyDAO pamsStrategyDAO;
	
	@Autowired
	PamsStrategyElementDAO pamsStrategyElementDAO;

	/**
	 * 获取策略
	 */
	@Override
	public PamsStrategy getPamsStrategyByStrategyId(Integer strategyId) {
		return pamsStrategyDAO.getPamsStrategyByStrategyId(strategyId);
	}

	/**
	 * 获取某个用户的所有策略
	 */
	@Override
	public List<PamsStrategy> getPamsStrategyListByUserId(Integer userId) {
		List<PamsStrategy> resultList = pamsStrategyDAO.getPamsStrategyListByUserId(userId);
		if(null == resultList) {
			return new ArrayList<PamsStrategy>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取某个类别的所有策略
	 */
	@Override
	public List<PamsStrategy> getPamsStrategyListByStrategyType(Integer strategyType) {
		List<PamsStrategy> resultList = pamsStrategyDAO.getPamsStrategyListByStrategyType(strategyType);
		if(null == resultList) {
			return new ArrayList<PamsStrategy>();
		} else {
			return resultList;
		}
	}

	/**
	 * 模糊查询某个类别下的所有策略
	 */
	@Override
	public List<PamsStrategy> getPamsStrategyByKeyAndStrategyType(String key, Integer strategyType) {
		List<PamsStrategy> resultList = pamsStrategyDAO.getPamsStrategyByKeyAndStrategyType(key, strategyType);
		if(null == resultList) {
			return new ArrayList<PamsStrategy>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取某个策略的所有股票组成
	 */
	@Override
	public List<StrategyElement> getStrategyElementListByStrategyId(Integer strategyId) {
		List<StrategyElement> resultList = pamsStrategyElementDAO.getStrategyElementListByStrategyId(strategyId);
		if(null == resultList) {
			return new ArrayList<StrategyElement>();
		} else {
			return resultList;
		}
	}

	/**
	 * 创建策略及其组成的股票元素
	 */
	@Override
	public void insertPamsStrategy(PamsStrategy pamsStrategy, List<StrategyElement> elementList) {
		pamsStrategyDAO.insertPamsStrategy(pamsStrategy);
		if(CollectionUtils.isNotEmpty(elementList)) {
			for(StrategyElement e : elementList) {
				pamsStrategyElementDAO.insertStrategyElement(e);
			}
		}
		
	}

	/**
	 * 设置某个策略的总和收益
	 */
	@Override
	public void setAvgProfitByStrategyId(Integer strategyId, BigDecimal avgProfit) {
		pamsStrategyDAO.setAvgProfitByStrategyId(strategyId, avgProfit);
	}

	/**
	 * 删除策略及其组成的股票元素
	 */
	@Override
	public void deletePamsStrategyByStrategyId(Integer strategyId) {
		pamsStrategyDAO.deletePamsStrategyByStrategyId(strategyId);
		pamsStrategyElementDAO.deleteStrategyElementByStrategyId(strategyId);
	}

	/**
	 * 更新未确认的策略
	 */
	@Override
	public void setNotStartByTodayStr(String today) {
		pamsStrategyDAO.setNotStartByTodayStr(today);
	}

	/**
	 * 更新进行中的策略
	 */
	@Override
	public void setOngoingByTodayStr(String today) {
		pamsStrategyDAO.setOngoingByTodayStr(today);
	}

	/**
	 * 更新收尾中的策略
	 */
	@Override
	public void setWindUpByTodayStr(String today) {
		pamsStrategyDAO.setWindUpByTodayStr(today);
	}

	/**
	 * 更新已结束的股票
	 */
	@Override
	public void setClosedByTodayStr(String today) {
		pamsStrategyDAO.setClosedByTodayStr(today);
	}
	
	
}
