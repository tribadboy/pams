package com.nju.pams.biz.model.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.nju.pams.model.asset.AccountOfMonth;
import com.nju.pams.util.BigDecimalUtil;

public class AccountOfMonthExcelVO {
	
	private BigDecimal cost;
	private String numberOfAccount;
	private String avgAccountCost;
	private String daysOfMonth;
	private String avgDayCost;
	
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getNumberOfAccount() {
		return numberOfAccount;
	}
	public void setNumberOfAccount(String numberOfAccount) {
		this.numberOfAccount = numberOfAccount;
	}
	public String getAvgAccountCost() {
		return avgAccountCost;
	}
	public void setAvgAccountCost(String avgAccountCost) {
		this.avgAccountCost = avgAccountCost;
	}
	public String getDaysOfMonth() {
		return daysOfMonth;
	}
	public void setDaysOfMonth(String daysOfMonth) {
		this.daysOfMonth = daysOfMonth;
	}
	public String getAvgDayCost() {
		return avgDayCost;
	}
	public void setAvgDayCost(String avgDayCost) {
		this.avgDayCost = avgDayCost;
	}
	
	public static AccountOfMonthExcelVO generateVOFromObj(AccountOfMonth account) {
		AccountOfMonthExcelVO vo = new AccountOfMonthExcelVO();
		if(null != account) {
			BigDecimal cost = account.getCost();
			int daysOfMonth = account.getDaysOfMonth();
			int numberOfAccount = account.getNumberOfAccount();
			if(0 != daysOfMonth && 0 != numberOfAccount) {
				vo.setCost(BigDecimalUtil.generateFormatNumber(cost));
				vo.setDaysOfMonth(daysOfMonth + "");
				vo.setNumberOfAccount(numberOfAccount + "");
				vo.setAvgDayCost(BigDecimalUtil.generateFormatString(
						cost.divide(BigDecimal.valueOf(daysOfMonth), 
								BigDecimalUtil.FORMAT_SCALE, RoundingMode.HALF_UP)));
				vo.setAvgAccountCost(BigDecimalUtil.generateFormatString(
						cost.divide(BigDecimal.valueOf(numberOfAccount), 
								BigDecimalUtil.FORMAT_SCALE, RoundingMode.HALF_UP)));
			}			
		}
		return vo;
	}

}
