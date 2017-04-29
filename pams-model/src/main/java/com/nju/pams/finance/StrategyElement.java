package com.nju.pams.finance;

import java.math.BigDecimal;

public class StrategyElement {
	private int elementId;
	private int strategyId;
	private String symbolCode;
	private int symbolType;
	private BigDecimal percent;						
    private String  createTime;
    private String  updateTime;
   
    public StrategyElement() {
    	
    }
    
    public StrategyElement(int strategyId, String symbolCode, int symbolType, BigDecimal percent) {
    	this.strategyId = strategyId;
    	this.symbolCode = symbolCode;
    	this.symbolType = symbolType;
    	this.percent = percent;
    }
     
  
	public int getElementId() {
		return elementId;
	}

	public void setElementId(int elementId) {
		this.elementId = elementId;
	}

	public int getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(int strategyId) {
		this.strategyId = strategyId;
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

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
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
		strBuf.append("{StrategyElement [")
		.append("elementId=" + elementId)
		.append(",strategyId=" + strategyId)
		.append(",symbolCode=" + symbolCode)
		.append(",symbolType=" + symbolType)
		.append(",percent=" + percent)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
	 
}
