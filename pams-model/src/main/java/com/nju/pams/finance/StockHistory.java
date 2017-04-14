package com.nju.pams.finance;

import java.math.BigDecimal;

public class StockHistory {
	
	private String symbolDate;
	private String symbolCode;
	private int symbolType;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal risePercent;
	private int	volume;

	public StockHistory() {
		
	}
	
	public StockHistory(String symbolDate, String symbolCode, int symbolType, BigDecimal open, BigDecimal close,
			BigDecimal high, BigDecimal low, BigDecimal risePercent, int volume) {
		super();
		this.symbolDate = symbolDate;
		this.symbolCode = symbolCode;
		this.symbolType = symbolType;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
		this.risePercent = risePercent;
		this.volume = volume;
	}
	public String getSymbolDate() {
		return symbolDate;
	}
	public void setSymbolDate(String symbolDate) {
		this.symbolDate = symbolDate;
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
	public BigDecimal getOpen() {
		return open;
	}
	public void setOpen(BigDecimal open) {
		this.open = open;
	}
	public BigDecimal getClose() {
		return close;
	}
	public void setClose(BigDecimal close) {
		this.close = close;
	}
	public BigDecimal getHigh() {
		return high;
	}
	public void setHigh(BigDecimal high) {
		this.high = high;
	}
	public BigDecimal getLow() {
		return low;
	}
	public void setLow(BigDecimal low) {
		this.low = low;
	}
	public BigDecimal getRisePercent() {
		return risePercent;
	}
	public void setRisePercent(BigDecimal risePercent) {
		this.risePercent = risePercent;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{StockHistory [")
		.append("symbolDate=" + symbolDate)
		.append(",symbolCode=" + symbolCode)
		.append(",symbolType=" + symbolType)
		.append(",open=" + open)
		.append(",close=" + close)
		.append(",high=" + high)
		.append(",low=" + low)
		.append(",risePercent=" + risePercent)
		.append(",volume=" + volume)
		.append("]}");
		return strBuf.toString();
	}
	 
}
