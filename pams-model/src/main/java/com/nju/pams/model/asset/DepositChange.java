package com.nju.pams.model.asset;

import java.math.BigDecimal;

public class DepositChange {
	private int changeId;
	private int depositId;
	private int changeTypeId;
	private BigDecimal changeAmount;
	private String changeTime;
    private String  createTime;
    private String  updateTime;
    
    public DepositChange() {
    	
    }
	
    public DepositChange(int depositId, int changeTypeId, BigDecimal changeAmount, String changeTime) {
    	this.depositId = depositId;
    	this.changeTypeId = changeTypeId;
    	this.changeAmount = changeAmount;
    	this.changeTime = changeTime;
    }
    
	 public enum ChangeType { 
		 MakeAccount(0),
	     Inflow(1),
	     Outflow(2),
	     CloseAccount(3);

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
	public int getDepositId() {
		return depositId;
	}
	public void setDepositId(int depositId) {
		this.depositId = depositId;
	}
	public int getChangeTypeId() {
		return changeTypeId;
	}
	public void setChangeTypeId(int changeTypeId) {
		this.changeTypeId = changeTypeId;
	}
	public BigDecimal getChangeAmount() {
		return changeAmount;
	}
	public void setChangeAmount(BigDecimal changeAmount) {
		this.changeAmount = changeAmount;
	}
	public String getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(String changeTime) {
		this.changeTime = changeTime;
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
		strBuf.append("{DepositChange [")
		.append("changeId=" + changeId)
		.append(",depositId=" + depositId)
		.append(",changeTypeId=" + changeTypeId)
		.append(",changeAmount=" + changeAmount)
		.append(",changeTime=" + changeTime)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
	 
}
