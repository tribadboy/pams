package com.nju.pams.finance;

import java.math.BigDecimal;

public class StockChange {
	private int changeId;
	private int userId;
	private int changeTypeId;
	private String symbolCode;
	private int symbolType;
	private BigDecimal price;
	private int quantity;
	private BigDecimal fee;
	private BigDecimal total;
	private String tradeTime;							//yyyy-MM-dd hh:mm:ss
    private String  createTime;
    private String  updateTime;
   
    public StockChange() {
    	
    }
    
    public StockChange(int userId, int changeTypeId, String symbolCode, int symbolType, 
    		BigDecimal price, int quantity, BigDecimal fee, BigDecimal total, String tradeTime) {
    	this.userId = userId;
    	this.changeTypeId = changeTypeId;
    	this.symbolCode = symbolCode;
    	this.symbolType = symbolType;
    	this.price = price;
    	this.quantity = quantity;
    	this.fee = fee;
    	this.total = total;
    	this.tradeTime = tradeTime;
    }
    
    public StockChange(int userId, int changeTypeId, BigDecimal total, String tradeTime) {
    	this.userId = userId;
    	this.changeTypeId = changeTypeId;
    	this.symbolCode = "";
    	this.symbolType = 0;
    	this.price = BigDecimal.valueOf(0);
    	this.quantity = 0;
    	this.fee = BigDecimal.valueOf(0);
    	this.total = total;
    	this.tradeTime = tradeTime;
    }
    
    public enum ChangeType { 
		 Purchase(0),
		 Sell(1),
	     Inflow(2),
	     Outflow(3);

	     private final int index;
	     private ChangeType(int index) {
	    	 this.index = index;
	     }
	     public int toIntValue() {
	    	 return index;
	     }
	 }
    
	public int getChangeId() {
		return changeId;
	}

	public void setChangeId(int changeId) {
		this.changeId = changeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getChangeTypeId() {
		return changeTypeId;
	}

	public void setChangeTypeId(int changeTypeId) {
		this.changeTypeId = changeTypeId;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
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
		strBuf.append("{StockChange [")
		.append("changeId=" + changeId)
		.append(",userId=" + userId)
		.append(",changeTypeId=" + changeTypeId)
		.append(",symbolCode=" + symbolCode)
		.append(",symbolType=" + symbolType)
		.append(",price=" + price)
		.append(",quantity=" + quantity)
		.append(",fee=" + fee)
		.append(",total=" + total)
		.append(",tradeTime=" + tradeTime)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
	 
}
