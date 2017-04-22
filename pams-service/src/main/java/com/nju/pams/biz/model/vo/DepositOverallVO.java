package com.nju.pams.biz.model.vo;

import java.math.BigDecimal;

public class DepositOverallVO {
	private int countOfInvalid;						//已结束的存款数量
	private int countOfValid;						//正在进行中的存款数量
	private BigDecimal invalidValue;				//已结束的存款最终收益值
	private BigDecimal payValue;					//进行中的存款累计投入值
	private BigDecimal exceptValue;					//进行中的存款的预期收益值
	
	public DepositOverallVO() {
		
	}
	
	public DepositOverallVO(int countOfInvalid, int countOfValid, BigDecimal invalidValue, BigDecimal payValue,
			BigDecimal exceptValue) {
		this.countOfInvalid = countOfInvalid;
		this.countOfValid = countOfValid;
		this.invalidValue = invalidValue;
		this.payValue = payValue;
		this.exceptValue = exceptValue;
	}
	
	public int getCountOfInvalid() {
		return countOfInvalid;
	}
	public void setCountOfInvalid(int countOfInvalid) {
		this.countOfInvalid = countOfInvalid;
	}
	public int getCountOfValid() {
		return countOfValid;
	}
	public void setCountOfValid(int countOfValid) {
		this.countOfValid = countOfValid;
	}
	public BigDecimal getInvalidValue() {
		return invalidValue;
	}
	public void setInvalidValue(BigDecimal invalidValue) {
		this.invalidValue = invalidValue;
	}
	public BigDecimal getPayValue() {
		return payValue;
	}
	public void setPayValue(BigDecimal payValue) {
		this.payValue = payValue;
	}
	public BigDecimal getExceptValue() {
		return exceptValue;
	}
	public void setExceptValue(BigDecimal exceptValue) {
		this.exceptValue = exceptValue;
	}
}
