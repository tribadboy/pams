package com.nju.pams.model.asset;

import java.math.BigDecimal;

public class AccountOfMonth {
	
	private int id;
	private int userId;
	private int consumptionId;
	private BigDecimal cost;
	private String spendMonth;
	private int numberOfAccount;
	private int daysOfMonth;
    private String  createTime;
    private String  updateTime;
    
    public AccountOfMonth() {
    }
    
    public AccountOfMonth(int userId, int consumptionId, BigDecimal cost, String spendMonth,
    		int numberOfAccount, int daysOfMonth) {
    	this.userId = userId;
    	this.consumptionId = consumptionId;
    	this.cost = cost;
    	this.spendMonth = spendMonth;
    	this.numberOfAccount = numberOfAccount;
    	this.daysOfMonth = daysOfMonth;
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getConsumptionId() {
		return consumptionId;
	}
	public void setConsumptionId(int consumptionId) {
		this.consumptionId = consumptionId;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getSpendMonth() {
		return spendMonth;
	}
	public void setSpendMonth(String spendMonth) {
		this.spendMonth = spendMonth;
	}
	public void setNumberOfAccount(int numberOfAccount) {
		this.numberOfAccount = numberOfAccount;
	}
	public int getNumberOfAccount() {
		return numberOfAccount;
	}
	public void setDaysOfMonth(int daysOfMonth) {
		this.daysOfMonth = daysOfMonth;
	}
	public int getDaysOfMonth() {
		return daysOfMonth;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
    
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{AccountOfMonth [")
		.append("id=" + id)
		.append(",userId=" + userId)
		.append(",consumptionId=" + consumptionId)
		.append(",cost=" + cost)
		.append(",spendMonth=" + spendMonth)
		.append(",numberOfAccount=" + numberOfAccount)
		.append(",daysOfMonth=" + daysOfMonth)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
