package com.nju.pams.biz.model.vo;

import java.math.BigDecimal;

public class LoanOverallVO {
	private int countOfValidInflow;				//进行中的贷入贷款
	private int countOfValidOutflow;			//进行中的贷出贷款
	private int countOfInvalidInflow;			//已结束的贷入贷款
	private int countOfInvalidOutflow;			//已结束的贷出贷款
	private BigDecimal validValue;				//进行中的贷款差值
	private BigDecimal invalidValue;			//已结束的贷款差值
	private BigDecimal exceptValue;				//预期剩余的贷款差值
	
	LoanOverallVO() {
		
	}
	
	public LoanOverallVO(int countOfValidInflow, int countOfValidOutflow, int countOfInvalidInflow,
			int countOfInvalidOutflow, BigDecimal validValue, BigDecimal invalidValue, BigDecimal exceptValue) {
		super();
		this.countOfValidInflow = countOfValidInflow;
		this.countOfValidOutflow = countOfValidOutflow;
		this.countOfInvalidInflow = countOfInvalidInflow;
		this.countOfInvalidOutflow = countOfInvalidOutflow;
		this.validValue = validValue;
		this.invalidValue = invalidValue;
		this.exceptValue = exceptValue;
	}



	public int getCountOfValidInflow() {
		return countOfValidInflow;
	}
	public void setCountOfValidInflow(int countOfValidInflow) {
		this.countOfValidInflow = countOfValidInflow;
	}
	public int getCountOfValidOutflow() {
		return countOfValidOutflow;
	}
	public void setCountOfValidOutflow(int countOfValidOutflow) {
		this.countOfValidOutflow = countOfValidOutflow;
	}
	public int getCountOfInvalidInflow() {
		return countOfInvalidInflow;
	}
	public void setCountOfInvalidInflow(int countOfInvalidInflow) {
		this.countOfInvalidInflow = countOfInvalidInflow;
	}
	public int getCountOfInvalidOutflow() {
		return countOfInvalidOutflow;
	}
	public void setCountOfInvalidOutflow(int countOfInvalidOutflow) {
		this.countOfInvalidOutflow = countOfInvalidOutflow;
	}
	public BigDecimal getValidValue() {
		return validValue;
	}
	public void setValidValue(BigDecimal validValue) {
		this.validValue = validValue;
	}
	public BigDecimal getInvalidValue() {
		return invalidValue;
	}
	public void setInvalidValue(BigDecimal invalidValue) {
		this.invalidValue = invalidValue;
	}
	public BigDecimal getExceptValue() {
		return exceptValue;
	}
	public void setExceptValue(BigDecimal exceptValue) {
		this.exceptValue = exceptValue;
	}
	
	
}
