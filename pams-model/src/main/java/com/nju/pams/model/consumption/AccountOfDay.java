package com.nju.pams.model.consumption;

import java.math.BigDecimal;

public class AccountOfDay {
	
	private int countOfUser;			//人数
	private BigDecimal cost;			//一天记录的消费总额
	private String spendTime;			//日期
    
    public AccountOfDay() {
    	
    }
    
    public AccountOfDay(int countOfUser, BigDecimal cost, String spendTime) {
    	this.countOfUser = countOfUser;
    	this.cost = cost;
    	this.spendTime = spendTime;
    }
    
	public int getCountOfUser() {
		return countOfUser;
	}

	public void setCountOfUser(int countOfUser) {
		this.countOfUser = countOfUser;
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

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{AccountOfDay [")
		.append("countOfUser=" + countOfUser)
		.append(",cost=" + cost)
		.append(",spendTime=" + spendTime)
		.append("]}");
		return strBuf.toString();
	}
}
