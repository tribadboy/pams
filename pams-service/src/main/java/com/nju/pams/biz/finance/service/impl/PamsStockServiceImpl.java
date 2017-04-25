package com.nju.pams.biz.finance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.finance.service.PamsStockService;
import com.nju.pams.finance.PamsStock;
import com.nju.pams.mapper.dao.PamsStockDAO;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsStockServiceImpl implements PamsStockService {

	@Autowired
	PamsStockDAO pamsStockDAO;
	
	/**
	 * 根据股票类型和股票代码查询股票
	 */
	@Override
	public PamsStock getPamsStockBySymbolCodeAndSymbolType(String symbolCode, Integer symbolType) {
		return pamsStockDAO.getPamsStockBySymbolCodeAndSymbolType(symbolCode, symbolType);
	}

	/**
	 * 获取某个类型下的所有有效的股票
	 */
	@Override
	public List<PamsStock> getValidPamsStocksBySymbolType(Integer symbolType) {
		List<PamsStock> resultList = pamsStockDAO.getValidPamsStocksBySymbolType(symbolType);
		if(null == resultList) {
			return new ArrayList<PamsStock>();
		} else {
			return resultList;
		}
	}

	/**
	 * replace传入的股票信息
	 */
	@Override
	public int replaceStocksList(List<PamsStock> stocksList) {
		return pamsStockDAO.replaceStocksList(stocksList);
	}

	/**
	 * 设置某个类型的所有股票为无效状态
	 */
	@Override
	public void setStocksInvalidBySymbolType(Integer symbolType) {
		pamsStockDAO.setStocksInvalidBySymbolType(symbolType);
	}
	
	/**
	 * 根据关键字查询股票信息，比对股票代码和股票名称
	 */
	@Override
	public List<PamsStock> getPamsStocksByKey(String key) {
		List<PamsStock> resultList = pamsStockDAO.getPamsStocksByKey("%" + key + "%");
		if(null == resultList) {
			return new ArrayList<PamsStock>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取所有股票信息
	 */
	@Override
	public List<PamsStock> getAllPamsStocks() {
		List<PamsStock> resultList = pamsStockDAO.getAllPamsStocks();
		if(null == resultList) {
			return new ArrayList<PamsStock>();
		} else {
			return resultList;
		}
	}
	
}
