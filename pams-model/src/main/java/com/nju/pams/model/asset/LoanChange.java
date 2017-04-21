package com.nju.pams.model.asset;

import java.math.BigDecimal;

public class LoanChange {
	private int changeId;
	private int loanId;
	private int changeTypeId;
	private BigDecimal changeAmount;
	private String changeTime;
    private String  createTime;
    private String  updateTime;
    
    public LoanChange() {
    	
    }
    
    /**
     * 创建还款记录
     * @param loanId
     * @param changeAmount
     * @param changeTime
     */
    public LoanChange(int loanId, BigDecimal changeAmount, String changeTime) {
    	this.loanId = loanId;
    	this.changeTypeId = ChangeType.Repay.getIndex();
    	this.changeAmount = changeAmount;
    	this.changeTime = changeTime;
    }
	
	 public enum ChangeType { 
		 MakeLoan(0, ""),
	     Repay(1, "");

	     private final int index;
	     private final String msg;
	     private ChangeType(int index, String msg) {
	    	 this.index = index;
	    	 this.msg = msg;
	     }
	     public int getIndex() {
	    	 return index;
	     }
	     public String getMsg() {
	    	 return msg;
	     }
	     public static String getMsgFromInt(int index) {
	    	 for(ChangeType type : ChangeType.values()) {
	    		 if(index == type.getIndex()) {
	    			 return type.getMsg();
	    		 }
	    	 }
	    	 return "";
	     }
	 }

	public int getChangeId() {
		return changeId;
	}
	public void setChangeId(int changeId) {
		this.changeId = changeId;
	}
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
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
		strBuf.append("{LoanChange [")
		.append("changeId=" + changeId)
		.append(",loanId=" + loanId)
		.append(",changeTypeId=" + changeTypeId)
		.append(",changeAmount=" + changeAmount)
		.append(",changeTime=" + changeTime)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
	 
}
