package com.nju.pams.biz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.model.vo.ConsumptionOverallVO;
import com.nju.pams.biz.service.PamsAccountService;
import com.nju.pams.mapper.dao.PamsAccountDAO;
import com.nju.pams.mapper.dao.PamsAccountMonthDAO;
import com.nju.pams.model.consumption.AccountOfDay;
import com.nju.pams.model.consumption.AccountOfMonth;
import com.nju.pams.model.consumption.ConsumptionAccount;
import com.nju.pams.model.consumption.ConsumptionCondition;
import com.nju.pams.util.BigDecimalUtil;
import com.nju.pams.util.DateUtil;
import com.nju.pams.util.EmptyUtil;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsAccountServiceImpl implements PamsAccountService {
	
	private static final Logger logger = Logger.getLogger(PamsAccountServiceImpl.class);
	
	@Autowired
	PamsAccountDAO pamsAccountDAO;
	
	@Autowired
	PamsAccountMonthDAO pamsAccountMonthDAO;

	@Override
	public ConsumptionAccount getConsumptionAccountByAccountId(Integer accountId) {
		return pamsAccountDAO.getConsumptionAccountByAccountId(accountId);
	}

	@Override
	public List<ConsumptionAccount> getConsumptionAccountListByUserId(Integer userId) {
		List<ConsumptionAccount> list = pamsAccountDAO.getConsumptionAccountListByUserId(userId);
		if(null == list) {
			return new ArrayList<ConsumptionAccount>();
		} else {
			return list;
		}
	}

	@Override
	public List<ConsumptionAccount> selectAccountByCondition(ConsumptionCondition consumptionCondition) {
		List<ConsumptionAccount> list = pamsAccountDAO.selectAccountByCondition(consumptionCondition);
		if(null == list) {
			return new ArrayList<ConsumptionAccount>();
		} else {
			return list;
		}
	}
	
	/**
	 * 插入某一条用户账目，同时更新用户月份总和账目
	 */
	@Override
	public void insertConsumptionAccount(ConsumptionAccount consumptionAccount) {
		BigDecimal cost = consumptionAccount.getCost();
		//插入当前单笔账目
		pamsAccountDAO.insertConsumptionAccount(consumptionAccount);
		//根据单笔账目查询当月总和账目
		AccountOfMonth accountOfMonth = getMonthAccountBySingleAccount(consumptionAccount);
		
		if(null == accountOfMonth) {
			//没有当月记录，创建一条新的记录插入
			int userId = consumptionAccount.getUserId();
			int consumptionId = consumptionAccount.getConsumptionId();		
			String spendMonth = consumptionAccount.getSpendTime().substring(0, 7);
			int daysOfMonth = DateUtil.getDaysInMonth(spendMonth);
			pamsAccountMonthDAO.insertAccountOfMonth(new AccountOfMonth(userId, consumptionId, 
					cost, spendMonth, 1, daysOfMonth));
			
		} else {
			//更新当月总和账目的cost和numberOfAccount
			accountOfMonth.setCost(accountOfMonth.getCost().add(cost));
			accountOfMonth.setNumberOfAccount(accountOfMonth.getNumberOfAccount() + 1);
			pamsAccountMonthDAO.updateAccountOfMonth(accountOfMonth);
		}
	}
	
	/**
	 * 修改单笔账目，同时修改月份账目总和
	 * @param consumptionAccount
	 */
	@Override
	public void updateConsumptionAccount(ConsumptionAccount consumptionAccount) {	
		//删除旧的单笔账目
		deleteConsumptionAccountByAccountId(consumptionAccount.getAccountId());
		//添加新的单笔账目
		insertConsumptionAccount(consumptionAccount);
	}
	
	/**
	 * 删除单笔账目，同时修改月份账目总和
	 * @param accountId
	 */
	@Override
	public void deleteConsumptionAccountByAccountId(Integer accountId) {
		//删除前先获取单笔账目的数据
		ConsumptionAccount account = pamsAccountDAO.getConsumptionAccountByAccountId(accountId);
		//删除数据库中的单笔账目
		pamsAccountDAO.deleteConsumptionAccountByAccountId(accountId);
		//根据单笔账目获取当月总和账目
		AccountOfMonth accountOfMonth = getMonthAccountBySingleAccount(account);
		if(null == accountOfMonth) {
			logger.error("删除单笔账目时没有找到对应的月份总和账目");
		} else {
			int num = accountOfMonth.getNumberOfAccount();
			BigDecimal singleCost = account.getCost();
			if(num == 1) {
				//月份总和账目由一笔账目组成，则删除记录
				pamsAccountMonthDAO.deleteAccountOfMonthById(accountOfMonth.getId());			
			} else if (num > 1) {
				//月份总和账目由多笔账目组成，更新记录
				accountOfMonth.setNumberOfAccount(num - 1);
				accountOfMonth.setCost(accountOfMonth.getCost().subtract(singleCost));
				pamsAccountMonthDAO.updateAccountOfMonth(accountOfMonth);
			} else {
				logger.error("删除单笔账目时，月份总额账目不为空，但账目计数小于1");
			}
		}	
	}
	
	/**
	 * 根据单笔账目查询对应的月份总和账目
	 * @param account
	 * @return
	 */
	public AccountOfMonth getMonthAccountBySingleAccount(ConsumptionAccount consumptionAccount) {
		if(null == consumptionAccount) {
			return null;
		} else {
			int userId = consumptionAccount.getUserId();
			int consumptionId = consumptionAccount.getConsumptionId();
			String spendMonth = consumptionAccount.getSpendTime().substring(0, 7);
			return pamsAccountMonthDAO.getAccountOfMonthByMonth(userId, consumptionId, spendMonth);
		}
	}

	@Override
	public BigDecimal computeAllConsumptionValue(Integer userId) {
		BigDecimal result = BigDecimal.ZERO;
		List<ConsumptionAccount> list = pamsAccountDAO.getConsumptionAccountListByUserId(userId);
		if(CollectionUtils.isNotEmpty(list)) {
			for(ConsumptionAccount account : list) {
				result = result.add(account.getCost());
			}
		}
		return BigDecimalUtil.generateFormatNumber(result);
	}

	@Override
	public ConsumptionOverallVO getConsumptionOverall(Integer userId) {
		BigDecimal result = BigDecimal.ZERO;
		int count = 0;
		List<ConsumptionAccount> list = pamsAccountDAO.getConsumptionAccountListByUserId(userId);
		if(CollectionUtils.isNotEmpty(list)) {
			for(ConsumptionAccount account : list) {
				result = result.add(account.getCost());
			}
			count = list.size();
		}
		String maxDate = EmptyUtil.notEmtpyProcess(pamsAccountDAO.getMaxDateByUserId(userId));
		String minDate = EmptyUtil.notEmtpyProcess(pamsAccountDAO.getMinDateByUserId(userId));
		return new ConsumptionOverallVO(BigDecimalUtil.generateFormatNumber(result), count, minDate, maxDate);
	}

	/**
	 * 获取某个用户在某个消费类别下的所有开销
	 */
	@Override
	public BigDecimal getSumCostByConsumptionIdAndUserId(Integer consumptionId, Integer userId) {
		return pamsAccountDAO.getSumCostByConsumptionIdAndUserId(consumptionId, userId);
	}

	/**
	 * 获取所有用户在某个消费类别下的所有开销
	 */
	@Override
	public BigDecimal getSumCostByConsumptionId(Integer consumptionId) {
		return pamsAccountDAO.getSumCostByConsumptionId(consumptionId);
	}

	/**
	 * 获取某个用户的总开销
	 */
	@Override
	public BigDecimal getSumCostByUserId(Integer userId) {
		return pamsAccountDAO.getSumCostByUserId(userId);
	}

	/**
	 * 获取所用用户的总开销
	 */
	@Override
	public BigDecimal getSumCost() {
		return pamsAccountDAO.getSumCost();
	}

	/**
	 * 获取某个用户的每一天的消费总额
	 */
	@Override
	public List<AccountOfDay> getDaySpendByUserId(Integer userId) {
		List<AccountOfDay> resultList = pamsAccountDAO.getDaySpendByUserId(userId);
		if(null == resultList) {
			return new ArrayList<AccountOfDay>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取所用用户的在某个阶段下的每一天的消费总额
	 */
	@Override
	public List<AccountOfDay> getDaySpendInPeriod(String minDate, String maxDate) {
		List<AccountOfDay> resultList = pamsAccountDAO.getDaySpendInPeriod(minDate, maxDate);
		if(null == resultList) {
			return new ArrayList<AccountOfDay>();
		} else {
			return resultList;
		}
	}

	/**
	 * 获取某个用户记录的最大日期
	 */
	@Override
	public String getMaxDateByUserId(Integer userId) {
		return pamsAccountDAO.getMaxDateByUserId(userId);
	}

	/**
	 * 获取某个用户记录的最小日期
	 */
	@Override
	public String getMinDateByUserId(Integer userId) {
		return pamsAccountDAO.getMinDateByUserId(userId);
	}
	
}
