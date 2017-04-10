package com.nju.pams.model.socaility;

public class PamsFriend {
	private int id;					
	private int userId;		
	private int friendId;
	private String friendName;
	private String friendMessage;
    private String  createTime;
    private String  updateTime;
    
    public PamsFriend() {
    	
    }
    
    public PamsFriend(int userId, int friendId, String friendName) {
    	this.userId = userId;
    	this.friendId = friendId;
    	this.friendName = friendName;
    	this.friendMessage = "";
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public String getFriendName() {
		return friendName;
	}
	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	public String getFriendMessage() {
		return friendMessage;
	}
	public void setFriendMessage(String friendMessage) {
		this.friendMessage = friendMessage;
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
		strBuf.append("{PamsFriend [")
		.append("id=" + id)
		.append("userId=" + userId)
		.append(",friendId=" + friendId)
		.append(",friendName=" + friendName)
		.append(",friendMessage=" + friendMessage)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
