package com.nju.pams.model.asset;

public class DepositRecord {
	private int depositId;					
	private int userId;		
	private String depositName;
	private int status;
	private int depositTimeId;
	private float currentProfitPercent;				//活期利率
	private float fixedProfitPercent;				//定期利率
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public DepositRecord() {
    	
    }
    
    public DepositRecord(int userId, String depositName, int depositTimeId, float currentProfitPercent, 
    		float fixedProfitPercent, String message) {
    	this.userId = userId;
    	this.depositName = depositName;
    	this.status = DepositRecord.Status.Valid.toIntValue();
    	this.depositTimeId = depositTimeId;
    	this.currentProfitPercent = currentProfitPercent;
    	this.fixedProfitPercent = fixedProfitPercent;
    	this.message = message;
    }
    
    
    public enum Status { 
        Valid(0, "未结束"),
        InValid(1, "已结束");

        private final int value;
        private final String msg;
        private Status(int value, String msg) {
            this.value = value;
            this.msg = msg;
        }
        public int toIntValue() {
            return value;
        }
        public String toMsgValue() {
            return msg;
        }
        public static String getMsgFromInt(int value) {
        	for(Status s : Status.values()) {
        		if(s.toIntValue() == value) {
        			return s.toMsgValue();
        		}
        	}
        	return "";
        }
    }
	public int getDepositId() {
		return depositId;
	}
	public void setDepositId(int depositId) {
		this.depositId = depositId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDepositName() {
		return depositName;
	}
	public void setDepositName(String depositName) {
		this.depositName = depositName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDepositTimeId() {
		return depositTimeId;
	}
	public void setDepositTimeId(int depositTimeId) {
		this.depositTimeId = depositTimeId;
	}
	public float getCurrentProfitPercent() {
		return currentProfitPercent;
	}
	public void setCurrentProfitPercent(float currentProfitPercent) {
		this.currentProfitPercent = currentProfitPercent;
	}
	public float getFixedProfitPercent() {
		return fixedProfitPercent;
	}
	public void setFixedProfitPercent(float fixedProfitPercent) {
		this.fixedProfitPercent = fixedProfitPercent;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
		strBuf.append("{DepositRecord [")
		.append("depositId=" + depositId)
		.append(",userId=" + userId)
		.append(",status=" + status)
		.append(",depositTimeId=" + depositTimeId)
		.append(",currentProfitPercent=" + currentProfitPercent)
		.append(",fixedProfitPercent=" + fixedProfitPercent)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
