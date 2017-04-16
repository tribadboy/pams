package com.nju.pams.biz.finance.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.finance.service.PamsStockHoldService;
import com.nju.pams.finance.StockHold;
import com.nju.pams.mapper.dao.PamsStockHoldDAO;



@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsStockHoldServiceImpl implements PamsStockHoldService {

	@Autowired
	PamsStockHoldDAO pamsStockHoldDAO;

	@Override
	public List<StockHold> getStockHoldListByUserId(Integer userId) {
		List<StockHold> resultList = pamsStockHoldDAO.getStockHoldListByUserId(userId);
		if(null == resultList) {
			return new ArrayList<StockHold>();
		} else {
			return resultList;
		}
	}

	@Override
	public int getStockHoldByUserIdAndStockInfo(Integer userId, String symbolCode, Integer symbolType) {
		StockHold hold = pamsStockHoldDAO.getStockHoldByUserIdAndStockInfo(userId, symbolCode, symbolType);
		if(null == hold) {
			return 0;
		} else {
			return hold.getQuantity();
		}
	}

	@Override
	public void increaseStockHoldQuantity(Integer userId, String symbolCode, Integer symbolType, int num) {
		StockHold hold = pamsStockHoldDAO.getStockHoldByUserIdAndStockInfo(userId, symbolCode, symbolType);
		if(null == hold) {
			StockHold newHold = new StockHold(userId, symbolCode, symbolType, num);
			pamsStockHoldDAO.insertStockHold(newHold);
		} else {
			hold.setQuantity(hold.getQuantity() + num);
			pamsStockHoldDAO.updateStockHold(hold);
		}
	}

	@Override
	public void decreaseStockHoldQuantity(Integer userId, String symbolCode, Integer symbolType, int num) {
		StockHold hold = pamsStockHoldDAO.getStockHoldByUserIdAndStockInfo(userId, symbolCode, symbolType);
		if(null == hold) {
			return;
		} else {
			int result = hold.getQuantity() - num;
			if(result < 0) {
				return;
			} else if (result == 0){
				pamsStockHoldDAO.deleteStockHoldByHoldId(hold.getHoldId());
			} else {
				hold.setQuantity(result);
				pamsStockHoldDAO.updateStockHold(hold);
			}
		}
		
	}

	

	
	
}
