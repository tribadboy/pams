package com.nju.pams.biz.finance.service.impl;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.finance.service.PamsStockCapitalService;
import com.nju.pams.finance.StockCapital;
import com.nju.pams.mapper.dao.PamsStockCapitalDAO;


@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsStockCapitalServiceImpl implements PamsStockCapitalService {

	@Autowired
	PamsStockCapitalDAO pamsStockCapitalDAO;

	@Override
	public BigDecimal getStockCapitalAmountByUserId(Integer userId) {
		StockCapital capital = pamsStockCapitalDAO.getStockCapitalByUserId(userId);
		if(null == capital) {
			return BigDecimal.valueOf(0.0);
		} else {
			return capital.getAmount();
		}
	}

	@Override
	public void increaseStockCapitalAmount(Integer userId, BigDecimal num) {
		StockCapital capital = pamsStockCapitalDAO.getStockCapitalByUserId(userId);
		if(null == capital) {
			StockCapital newCapital = new StockCapital(userId, num);
			pamsStockCapitalDAO.insertStockCapital(newCapital);
		} else {
			capital.setAmount(capital.getAmount().add(num));
			pamsStockCapitalDAO.updateStockCapital(capital);
		}
	}

	@Override
	public void decreaseStockCaptialAmount(Integer userId, BigDecimal num) {
		StockCapital capital = pamsStockCapitalDAO.getStockCapitalByUserId(userId);
		if(null == capital) {
			return;
		} else {
			BigDecimal result = capital.getAmount().subtract(num);
			if(result.compareTo(BigDecimal.valueOf(0)) < 0) {
				return;
			} else {
				capital.setAmount(result);
				pamsStockCapitalDAO.updateStockCapital(capital);
			}
		}
	}
	

	
}
