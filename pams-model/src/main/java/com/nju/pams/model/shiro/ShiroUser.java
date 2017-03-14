package com.nju.pams.model.shiro;

import java.io.Serializable;

public class ShiroUser implements Serializable {
	
	private static final long serialVersionUID = 153518439532340756L;
	
	private int userId;
	private String userName;
	private String password;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{userId=" + userId)
		.append(",userName=" + userName)
		.append(",password=" + password);
		return strBuf.toString();
	}
}
