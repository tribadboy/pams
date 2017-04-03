package com.nju.pams.model.asset;

import java.math.BigDecimal;

public class ConsumptionAccount {
	
	private int accountId;
	private int userId;
	private int consumptionId;
	private BigDecimal cost;
	private String spendTime;
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public ConsumptionAccount() {
    }
    
    public ConsumptionAccount(int userId, int consumptionId, BigDecimal cost, String spendTime, String message) {
    	this.userId = userId;
    	this.consumptionId = consumptionId;
    	this.cost = cost;
    	this.spendTime = spendTime;
    	this.message = message;
    }
    
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
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
	public String getSpendTime() {
		return spendTime;
	}
	public void setSpendTime(String spendTime) {
		this.spendTime = spendTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
		strBuf.append("{ConsumptionAccount [")
		.append("accountId=" + accountId)
		.append(",userId=" + userId)
		.append(",consumptionId=" + consumptionId)
		.append(",cost=" + cost)
		.append(",spendTime=" + spendTime)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
