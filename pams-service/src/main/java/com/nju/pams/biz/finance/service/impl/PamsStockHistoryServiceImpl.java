package com.nju.pams.biz.finance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.finance.service.PamsStockHistoryService;
import com.nju.pams.finance.StockHistory;
import com.nju.pams.mapper.dao.PamsStockHistoryDAO;


@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsStockHistoryServiceImpl implements PamsStockHistoryService {

	@Autowired
	PamsStockHistoryDAO pamsStockHistoryDAO;

	/**
	 * 查询某只股票某一天的历史数据
	 */
	@Override
	public StockHistory getStockHistoryByPK(String symbolDate, String symbolCode, Integer symbolType) {
		return pamsStockHistoryDAO.getStockHittoryByPK(symbolDate, symbolCode, symbolType);
	}

	/**
	 * 查询某只股票在某个阶段的历史数据
	 */
	@Override
	public List<StockHistory> getPeriodStockHistoryByPK(String symbolCode, Integer symbolType, String startDate,
			String endDate) {
		List<StockHistory> resultList = pamsStockHistoryDAO.getPeriodStockHittoryByPK(symbolCode, symbolType, startDate, endDate);
		if(null == resultList) {
			return new ArrayList<StockHistory>();
		} else {
			return resultList;
		}
	}
	
	/**
	 * 查询某只股票的全部历史数据
	 */
	@Override
	public List<StockHistory> getAllStockHistoryByPK(String symbolCode, Integer symbolType) {
		List<StockHistory> resultList = pamsStockHistoryDAO.getAllStockHittoryByPK(symbolCode, symbolType);
		if(null == resultList) {
			return new ArrayList<StockHistory>();
		} else {
			return resultList;
		}
	}

	/**
	 * 插入股票历史数据，当主键已存在时忽略
	 * 不能插入空值
	 */
	@Override
	public int insertIgnoreStockHistoryList(List<StockHistory> stockHistoryList) {
		if(CollectionUtils.isNotEmpty(stockHistoryList)) {
			return pamsStockHistoryDAO.insertIgnoreStockHistoryList(stockHistoryList);
		} else {
			return 0;
		}	
	}

	/**
	 * 获取某只股票历史数据的最大日期
	 */
	@Override
	public String getMaxDateByPK(String symbolCode, Integer symbolType) {
		return pamsStockHistoryDAO.getMaxDateByPK(symbolCode, symbolType);
	}

	/**
	 * 获取某只股票历史数据的最小日期
	 */
	@Override
	public String getMinDateByPK(String symbolCode, Integer symbolType) {
		return pamsStockHistoryDAO.getMinDateByPK(symbolCode, symbolType);
	}

	/**
	 * 获取历史数据的最大时间
	 */
	@Override
	public String getMaxDate() {
		return pamsStockHistoryDAO.getMaxDate();
	}
	
}
