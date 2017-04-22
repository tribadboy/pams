package com.nju.pams.biz.model.vo;

import java.math.BigDecimal;

public class ConsumptionOverallVO {
	private BigDecimal sum;
	private int count;
	private String minDate;
	private String maxDate;
	
	public ConsumptionOverallVO() {
		
	}
	
	public ConsumptionOverallVO(BigDecimal sum ,int count, String minDate, String maxDate) {
		this.sum = sum;
		this.count = count;
		this.minDate = minDate;
		this.maxDate = maxDate;
	}
	
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMinDate() {
		return minDate;
	}
	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}
	public String getMaxDate() {
		return maxDate;
	}
	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}
	
}
