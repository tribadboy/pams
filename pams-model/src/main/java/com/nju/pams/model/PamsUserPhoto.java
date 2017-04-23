package com.nju.pams.model;

public class PamsUserPhoto {
	
	public static final int MAX_LENGTH = 60;		//照片名字长度上限
	
	private int userId;						
	private String photoName;
	private String createTime;
	private String updateTime;
    
    public PamsUserPhoto() {
    	
    }
    
    public PamsUserPhoto(int userId, String photoName) {
    	this.userId = userId;
    	this.photoName = photoName;
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
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

	public static int getMaxLength() {
		return MAX_LENGTH;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{PamsUserPhoto [")
		.append("userId=" + userId)
		.append("photoName=" + photoName)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
      
}
