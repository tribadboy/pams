package com.nju.pams.finance;

import java.math.BigDecimal;

public class StockCapital {
	
	private int userId;
	private BigDecimal amount;
    private String  createTime;
    private String  updateTime;
    
    public StockCapital() {
    	
    }
    
    public StockCapital(int userId, BigDecimal amount) {
    	this.userId = userId;
    	this.amount = amount;
    }  

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
		strBuf.append("{StockCapital [")
		.append("userId=" + userId)
		.append(",amount=" + amount)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
