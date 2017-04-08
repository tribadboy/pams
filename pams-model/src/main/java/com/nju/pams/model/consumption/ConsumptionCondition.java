package com.nju.pams.model.consumption;

import java.math.BigDecimal;
import java.util.List;

/**
 * 消费账目条件类
 * @author yangyueyang
 * @date 2017-04-04
 */
public class ConsumptionCondition {
	
	private int userId;
	private List<Integer> consumptionIds;
	private String startTime;
	private String endTime;
	private BigDecimal minCost;
	private BigDecimal maxCost;
	
	public ConsumptionCondition() {
		
	}
	
	public ConsumptionCondition(int userId, List<Integer> consumptionIds, String startTime, String endTime,
			BigDecimal minCost, BigDecimal maxCost) {
		this.userId = userId;
		this.consumptionIds = consumptionIds;
		this.startTime = startTime;
		this.endTime = endTime;
		this.minCost = minCost;
		this.maxCost = maxCost;
	}



	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public List<Integer> getConsumptionIds() {
		return consumptionIds;
	}
	public void setConsumptionIds(List<Integer> consumptionIds) {
		this.consumptionIds = consumptionIds;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public BigDecimal getMinCost() {
		return minCost;
	}
	public void setMinCost(BigDecimal minCost) {
		this.minCost = minCost;
	}
	public BigDecimal getMaxCost() {
		return maxCost;
	}
	public void setMaxCost(BigDecimal maxCost) {
		this.maxCost = maxCost;
	}
	
	
}
