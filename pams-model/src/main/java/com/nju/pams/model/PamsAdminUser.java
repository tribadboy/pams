package com.nju.pams.model;

public class PamsAdminUser {
	
	private int userId;						//对应PamsUser中的属性		
	private String username;		
	
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
		strBuf.append("{PamsAdminUser [")
		.append("userId=" + userId)
		.append(",username=" + username)
		.append("]}");
		return strBuf.toString();
	}
    
    
}
