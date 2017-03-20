package com.nju.pams.model;

public class PamsUser {
	
	private int id;						//用户数据库存储主键
	private String username;		
	private String password;
	private int status;
	private String phone;
	private String mail;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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
		strBuf.append("{PamsUser [")
		.append("id=" + id)
		.append(",username=" + username)
		.append(",passowrd=" + password)
		.append(",status=" + status)
		.append(",phone=" + phone)
		.append(",mail=" + mail)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
    
    
}
