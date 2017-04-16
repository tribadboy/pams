package com.nju.pams.finance;

public class StockHold {
	
	private int holdId;
	private int userId;
	private String symbolCode;
	private int symbolType;
	private int quantity;
    private String  createTime;
    private String  updateTime;
  
    public StockHold() {
    	
    }
    
    public StockHold(int userId, String symbolCode, int symbolType, int quantity) {
    	this.userId = userId;
    	this.symbolCode = symbolCode;
    	this.symbolType = symbolType;
    	this.quantity = quantity;
    }

	public int getHoldId() {
		return holdId;
	}

	public void setHoldId(int holdId) {
		this.holdId = holdId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSymbolCode() {
		return symbolCode;
	}

	public void setSymbolCode(String symbolCode) {
		this.symbolCode = symbolCode;
	}

	public int getSymbolType() {
		return symbolType;
	}

	public void setSymbolType(int symbolType) {
		this.symbolType = symbolType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
		strBuf.append("{StockHold [")
		.append("holdId=" + holdId)
		.append(",userId=" + userId)
		.append(",symbolCode=" + symbolCode)
		.append(",symbolType=" + symbolType)
		.append(",quantity=" + quantity)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
