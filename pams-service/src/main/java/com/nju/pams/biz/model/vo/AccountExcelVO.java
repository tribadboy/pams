package com.nju.pams.biz.model.vo;

import com.nju.pams.model.consumption.ConsumptionAccount;
import com.nju.pams.model.consumption.ConsumptionEnum;
import com.nju.pams.util.BigDecimalUtil;

public class AccountExcelVO {
	
	private String spendTime;
	private String consumptionName;
	private String cost;
	private String message;
	
	public String getSpendTime() {
		return spendTime;
	}
	public void setSpendTime(String spendTime) {
		this.spendTime = spendTime;
	}
	public String getConsumptionName() {
		return consumptionName;
	}
	public void setConsumptionName(String consumptionName) {
		this.consumptionName = consumptionName;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static AccountExcelVO generateVOFromObj(ConsumptionAccount account) {
		AccountExcelVO vo = new AccountExcelVO();
		if(null != account) {
			vo.setSpendTime(account.getSpendTime());
			vo.setConsumptionName(ConsumptionEnum.getMsgFromInt(account.getConsumptionId()));
			vo.setCost(BigDecimalUtil.generateFormatString(account.getCost()));
			vo.setMessage(account.getMessage());
		}
		return vo;
	}

}
