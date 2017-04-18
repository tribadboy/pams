package com.nju.pams.model;

public class PamsLoginInfo {
	
	private int infoId;
	private int userId;
	private String ip;
	private String loginTime;		//yyyy-MM-dd HH:mm
    private String  createTime;
    private String  updateTime;
  
    public PamsLoginInfo() {
    	
    }
    
    public PamsLoginInfo(int userId, String ip, String loginTime) {
    	this.userId = userId;
    	this.ip = ip;
    	this.loginTime = loginTime;
    }

	public int getInfoId() {
		return infoId;
	}

	public void setInfoId(int infoId) {
		this.infoId = infoId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
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
		strBuf.append("{PamsUserLoginInfo [")
		.append("infoId=" + infoId)
		.append(",userId=" + userId)
		.append(",ip=" + ip)
		.append(",loginTime=" + loginTime)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
    
    
}
