package com.nju.pams.model.asset;

public class DepositRecord {
	private int depositId;					
	private int userId;		
	private int status;
	private int depositTimeId;
	private float currentProfitPercent;				//活期利率
	private float fixedProfitPercent;				//定期利率
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public enum Status { 
        Valid(0),
        InValid(1);

        private final int value;
        private Status(int value) {
            this.value = value;
        }
        public int toIntValue() {
            return value;
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
