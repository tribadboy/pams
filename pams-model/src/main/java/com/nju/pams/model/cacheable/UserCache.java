package com.nju.pams.model.cacheable;

import java.io.Serializable;

public class UserCache implements Serializable {

	private static final long serialVersionUID = -702936623359359743L;
	
	private int id;
	private String username;
	private String password;
	private int age;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{id=" + id)
		.append(",username=" + username)
		.append(",password=" + password)
		.append(",age=" + age + "}");
		return strBuf.toString();
	}
}
