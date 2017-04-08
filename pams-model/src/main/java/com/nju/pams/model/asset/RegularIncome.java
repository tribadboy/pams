package com.nju.pams.model.asset;

import java.math.BigDecimal;

public class RegularIncome {
	
	private int incomeId;
	private int userId;
	private BigDecimal recordAmount;
	private String recordTime;
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public RegularIncome() {
    	
    }
    
    public RegularIncome(int userId, BigDecimal recordAmount, String recordTime, String message) {
    	this.userId = userId;
    	this.recordAmount = recordAmount;
    	this.recordTime = recordTime;
    	this.message = message;
    }
    
	public int getIncomeId() {
		return incomeId;
	}
	public void setIncomeId(int incomeId) {
		this.incomeId = incomeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public BigDecimal getRecordAmount() {
		return recordAmount;
	}
	public void setRecordAmount(BigDecimal recordAmount) {
		this.recordAmount = recordAmount;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
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
		strBuf.append("{RegularIncome [")
		.append("incomeId=" + incomeId)
		.append(",userId=" + userId)
		.append(",recordAmount=" + recordAmount)
		.append(",recordTime=" + recordTime)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
