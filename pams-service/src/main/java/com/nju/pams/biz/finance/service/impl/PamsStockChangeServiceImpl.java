package com.nju.pams.biz.finance.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.finance.service.PamsStockCapitalService;
import com.nju.pams.biz.finance.service.PamsStockChangeService;
import com.nju.pams.biz.finance.service.PamsStockHoldService;
import com.nju.pams.finance.StockChange;
import com.nju.pams.mapper.dao.PamsStockChangeDAO;
import com.nju.pams.util.BigDecimalUtil;



@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsStockChangeServiceImpl implements PamsStockChangeService {
	
	private static final Logger logger = Logger.getLogger(PamsStockChangeServiceImpl.class);

	@Autowired
	PamsStockChangeDAO pamsStockChangeDAO;
	
	@Autowired
	PamsStockCapitalService pamsStockCapitalService;
	
	@Autowired
	PamsStockHoldService pamsStockHoldService;

	/**
	 * 获取最新的变更时间
	 */
	@Override
	public String getMaxTradeTimeByUserId(int userId) {
		return pamsStockChangeDAO.getMaxTradeTimeByUserId(userId);
	}

	/**
	 * 按照时间顺序获取某个用户的所有股票变更记录
	 */
	@Override
	public List<StockChange> getStockChangeListByUserId(Integer userId) {
		List<StockChange> resultList = pamsStockChangeDAO.getStockChangeListByUserId(userId);
		if(null == resultList) {
			return new ArrayList<StockChange>();
		} else {
			return resultList;
		}
	}
	
	/**
	 * 获取用户的累计投入，累计转入减去累计转出
	 * @param userId
	 * @return
	 */
	@Override
	public BigDecimal getTotalInvestmentByUserId(Integer userId) {
		BigDecimal result = BigDecimal.ZERO;
		List<StockChange> resultList = pamsStockChangeDAO.getStockChangeListByUserId(userId);
		if(CollectionUtils.isNotEmpty(resultList)) {
			for(StockChange change : resultList) {
				if(change.getChangeTypeId() == StockChange.ChangeType.Inflow.toIntValue()) {
					result = result.add(change.getTotal());
				} else if(change.getChangeTypeId() == StockChange.ChangeType.Outflow.toIntValue()){
					result = result.subtract(change.getTotal());
				}
			}
		}
		return BigDecimalUtil.generateFormatNumber(result);
	}

	/**
	 * 计算股票购买和卖出的税费,默认股数已经通过验证
	 * 购买股票：  交易佣金：总价值的 千分之五 且至少5元   +    过户费  1元／1000股， 至少1元
	 * 卖出股票：  交易佣金 + 过户费  + 印花税（总价值的千分之一）
	 */
	@Override
	public BigDecimal getFeeForStock(int changeTypeId, BigDecimal price, int quantity) {
		double price_d = price.doubleValue();
		if(changeTypeId == StockChange.ChangeType.Purchase.toIntValue()) {
			double result1 = price_d * quantity;
			result1 = result1 * 3 / 1000;
			result1 = (result1 < 5) ? 5 : result1;
			double result2 = (double) quantity / 1000;
			result2 = (result2 < 1) ? 1 : result2;
			double result = result1 + result2;
			return BigDecimalUtil.generateFormatNumber(BigDecimal.valueOf(result));					
		} else if(changeTypeId == StockChange.ChangeType.Sell.toIntValue()) {
			double result1 = price_d * quantity;
			result1 = result1 * 3 / 1000;
			result1 = (result1 < 5) ? 5 : result1;
			double result2 = (double) quantity / 1000;
			result2 = (result2 < 1) ? 1 : result2;
			double result3 = price_d * quantity / 1000;
			double result = result1 + result2 + result3;
			return BigDecimalUtil.generateFormatNumber(BigDecimal.valueOf(result));	
		} else {
			logger.info("非股票买卖计算税费，股票变更类型错误");
			return BigDecimal.valueOf(0.0);
		}
	}

	/**
	 * 计算买卖股票的总价钱（单价 * 数量 + 税费）
	 * @param changeTypeId
	 * @param price
	 * @param quantity
	 * @return
	 */
	@Override
	public BigDecimal getTotalForStock(int changeTypeId, BigDecimal price, int quantity) {
		BigDecimal fee = getFeeForStock(changeTypeId, price, quantity);
		BigDecimal result = price.multiply(BigDecimal.valueOf(quantity)).add(fee);
		return BigDecimalUtil.generateFormatNumber(result);
	}

	/**
	 * 买卖的股票数量验证， 买入必须为100的整数，卖出时不能大于持股数，若小于等于100必须全部卖出
	 * @param userId
	 * @param ChangeTypeId
	 * @param symbolCode
	 * @param symbolType
	 * @param quantity
	 * @return
	 */
	@Override
	public boolean stockQuantityVerification(int userId, int changeTypeId, String symbolCode, int symbolType,
			int quantity) {
		if(quantity <= 0) {
			return false;
		}
		if(changeTypeId == StockChange.ChangeType.Purchase.toIntValue()) {
			if(quantity % 100 == 0) {
				return true;
			} else {
				return false;
			}
		} else if(changeTypeId == StockChange.ChangeType.Sell.toIntValue()) {
			int holdQuantity = pamsStockHoldService.getStockHoldByUserIdAndStockInfo(userId, symbolCode, symbolType);
			if(holdQuantity <= 100 && holdQuantity == quantity) {
				return true;
			} else if(holdQuantity > 100 && holdQuantity >= quantity) {
				return true;
			} else {
				return false;
			}		
		} else {
			logger.info("非买卖股票而执行股数验证，股票变更类型错误");
			return false;
		}
	}

	/**
	 * 对于转出和股票买入进行资金验证
	 * @param userId
	 * @param total
	 * @return
	 */
	@Override
	public boolean stockCaptialVerification(int userId, BigDecimal total) {
		BigDecimal capital = pamsStockCapitalService.getStockCapitalAmountByUserId(userId);
		if(capital.compareTo(total) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 时间验证
	 * @param userId
	 * @param targetTime
	 * @return
	 */
	@Override
	public boolean stockTimeVerification(int userId, String targetTime) {
		String maxTime = getMaxTradeTimeByUserId(userId);
		if(null == maxTime || maxTime.compareTo(targetTime) < 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 插入股票变更记录，同时更新资金表和持股表
	 * @param stockChange
	 * @return
	 */
	@Override
	public boolean insertStockChange(StockChange stockChange) {
		//首先进行时间验证
		int userId = stockChange.getUserId();
		int changeTypeId = stockChange.getChangeTypeId();
		BigDecimal total = stockChange.getTotal();
		if(!stockTimeVerification(userId, stockChange.getTradeTime())) {
			return false;
		}
		if(changeTypeId == StockChange.ChangeType.Inflow.toIntValue()) {
			//转入资金不需要其他验证
			pamsStockChangeDAO.insertStockChange(stockChange);
			pamsStockCapitalService.increaseStockCapitalAmount(userId, total);
			return true;
			
		} else if(changeTypeId == StockChange.ChangeType.Outflow.toIntValue()) {
			//转出资金需要进行资金验证
			if(!stockCaptialVerification(userId, total)) {
				return false;
			}
			pamsStockChangeDAO.insertStockChange(stockChange);
			pamsStockCapitalService.decreaseStockCaptialAmount(userId, total);
			return true;
			
		} else if(changeTypeId == StockChange.ChangeType.Purchase.toIntValue()) {
			//购买股票需要进行资金验证和股数验证
			if(!stockCaptialVerification(userId, total)) {
				return false;
			}
			String symbolCode = stockChange.getSymbolCode();
			int symbolType = stockChange.getSymbolType();
			int quantity = stockChange.getQuantity();
			if(!stockQuantityVerification(userId, changeTypeId, symbolCode, symbolType, quantity)) {
				return false;
			}
			pamsStockChangeDAO.insertStockChange(stockChange);
			pamsStockCapitalService.decreaseStockCaptialAmount(userId, total);
			pamsStockHoldService.increaseStockHoldQuantity(userId, symbolCode, symbolType, quantity);
			return true;
			
		} else if(changeTypeId == StockChange.ChangeType.Sell.toIntValue()) {
			//卖出股票需要进行股数验证
			String symbolCode = stockChange.getSymbolCode();
			int symbolType = stockChange.getSymbolType();
			int quantity = stockChange.getQuantity();
			if(!stockQuantityVerification(userId, changeTypeId, symbolCode, symbolType, quantity)) {
				return false;
			}
			pamsStockChangeDAO.insertStockChange(stockChange);
			pamsStockCapitalService.increaseStockCapitalAmount(userId, total);
			pamsStockHoldService.decreaseStockHoldQuantity(userId, symbolCode, symbolType, quantity);
			return true;
			
		} else {
			logger.info("插入的股票变更类型错误，插入失败");
			return false;
		}
	}

	/**
	 * 撤销最新的股票变更记录，同时更新用户资金表和持股表
	 * @param changeId
	 */
	@Override
	public boolean cancelStockChange(int changeId) {
		StockChange change = pamsStockChangeDAO.getStockChangeByChangeId(changeId);
		if(null == change) {
			return false;
		}
		String maxTime = getMaxTradeTimeByUserId(change.getUserId());
		if(maxTime.equals(change.getTradeTime())) {
			BigDecimal total = change.getTotal();
			int changeTypeId = change.getChangeTypeId();
			int userId = change.getUserId();
			if(changeTypeId == StockChange.ChangeType.Inflow.toIntValue()) {
				pamsStockCapitalService.decreaseStockCaptialAmount(userId, total);
				pamsStockChangeDAO.deleteStockChangeByChangeId(changeId);
				return true;
				
			} else if(changeTypeId == StockChange.ChangeType.Outflow.toIntValue()) {
				pamsStockCapitalService.increaseStockCapitalAmount(userId, total);
				pamsStockChangeDAO.deleteStockChangeByChangeId(changeId);
				return true;
				
			} else if(changeTypeId == StockChange.ChangeType.Purchase.toIntValue()) {
				int quantity = change.getQuantity();
				String symbolCode = change.getSymbolCode();
				int symbolType = change.getSymbolType();
				pamsStockCapitalService.increaseStockCapitalAmount(userId, total);
				pamsStockHoldService.decreaseStockHoldQuantity(userId, symbolCode, symbolType, quantity);
				pamsStockChangeDAO.deleteStockChangeByChangeId(changeId);
				return true;
				
			} else if(changeTypeId == StockChange.ChangeType.Sell.toIntValue()) {
				int quantity = change.getQuantity();
				String symbolCode = change.getSymbolCode();
				int symbolType = change.getSymbolType();
				pamsStockCapitalService.decreaseStockCaptialAmount(userId, total);
				pamsStockHoldService.increaseStockHoldQuantity(userId, symbolCode, symbolType, quantity);
				pamsStockChangeDAO.deleteStockChangeByChangeId(changeId);
				return true;
				
			} else {
				logger.info("撤销时发现股票变更类型错误，无法撤销");
				return false;
			}
		} else {
			logger.info("要撤销的股票变更记录不是最新的，不执行撤销");
			return false;
		}	
	}
	
	

	
}
