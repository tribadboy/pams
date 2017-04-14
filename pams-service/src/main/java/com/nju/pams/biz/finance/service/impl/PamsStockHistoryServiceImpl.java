package com.nju.pams.biz.finance.service.impl;

import java.util.List;

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

	@Override
	public StockHistory getStockHittoryByPK(String symbolDate, String symbolCode, Integer symbolType) {
		return pamsStockHistoryDAO.getStockHittoryByPK(symbolDate, symbolCode, symbolType);
	}

	@Override
	public List<StockHistory> getPeriodStockHittoryByPK(String symbolCode, Integer symbolType, String startDate,
			String endDate) {
		return pamsStockHistoryDAO.getPeriodStockHittoryByPK(symbolCode, symbolType, startDate, endDate);
	}

	@Override
	public int insertIgnoreStockHistoryList(List<StockHistory> stockHistoryList) {
		return pamsStockHistoryDAO.insertIgnoreStockHistoryList(stockHistoryList);
	}
	
}
