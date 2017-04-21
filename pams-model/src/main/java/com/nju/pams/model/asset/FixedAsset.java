package com.nju.pams.model.asset;

import java.math.BigDecimal;

public class FixedAsset {
	
	private int assetId;
	private int userId;
	private String recordName;
	private BigDecimal recordValue;
	private String recordTime;
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public FixedAsset() {
    	
    }
    
    public FixedAsset(int userId, String recordName, BigDecimal recordValue, String recordTime, String message) {
    	this.userId = userId;
    	this.recordName = recordName;
    	this.recordValue = recordValue;
    	this.recordTime = recordTime;
    	this.message = message;
    }
    
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public BigDecimal getRecordValue() {
		return recordValue;
	}
	public void setRecordValue(BigDecimal recordValue) {
		this.recordValue = recordValue;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
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
		strBuf.append("{FixedAsset [")
		.append("assetId=" + assetId)
		.append(",userId=" + userId)
		.append(",recordName=" + recordName)
		.append(",recordValue=" + recordValue)
		.append(",recordTime=" + recordTime)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
