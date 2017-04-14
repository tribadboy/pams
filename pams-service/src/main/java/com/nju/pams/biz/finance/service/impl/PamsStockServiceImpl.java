package com.nju.pams.biz.finance.service.impl;

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
	
	@Override
	public PamsStock getPamsStockBySymbolCodeAndSymbolType(String symbolCode, Integer symbolType) {
		return pamsStockDAO.getPamsStockBySymbolCodeAndSymbolType(symbolCode, symbolType);
	}

	@Override
	public List<PamsStock> getValidPamsStocksBySymbolType(Integer symbolType) {
		return pamsStockDAO.getValidPamsStocksBySymbolType(symbolType);
	}

	@Override
	public int replaceStocksList(List<PamsStock> stocksList) {
		return pamsStockDAO.replaceStocksList(stocksList);
	}

	@Override
	public void setStocksInvalidBySymbolType(Integer symbolType) {
		pamsStockDAO.setStocksInvalidBySymbolType(symbolType);
	}
	
}
