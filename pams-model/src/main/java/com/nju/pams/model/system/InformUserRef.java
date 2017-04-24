package com.nju.pams.model.system;

/**
 * 默认仅在页面中显示最新的一条有效公告
 * 公开给所有用户
 */
public class InformUserRef {
	private int refId;
	private int informId;
	private int userId;
	private String username;
    
    public InformUserRef() {
    	
    }
    
    public InformUserRef(int informId, int userId, String username) {
    	this.informId = informId;
    	this.userId = userId;
    	this.username = username;
    }

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public int getInformId() {
		return informId;
	}

	public void setInformId(int informId) {
		this.informId = informId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{InformUserRef [")
		.append("refId=" + refId)
		.append(",informId=" + informId)
		.append(",userId=" + userId)
		.append(",username=" + username)
		.append("]}");
		return strBuf.toString();
	}
}
