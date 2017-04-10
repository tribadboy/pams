package com.nju.pams.model.socaility;

public class FriendLetter {
	private int letterId;					
	private int sendUserId;		
	private String sendUserName;
	private int receiveUserId;
	private String receiveUserName;
	private int status;
	private String content;
    private String  createTime;
    private String  updateTime;
    
    public FriendLetter() {
    	
    }
    
    public FriendLetter(int sendUserId, String sendUserName, int receiveUserId, String receiveUserName,
    		String content) {
    	this.sendUserId = sendUserId;
    	this.sendUserName = sendUserName;
    	this.receiveUserId = receiveUserId;
    	this.receiveUserName = receiveUserName;
    	this.content = content;
    	this.status = FriendLetter.Status.Unread.getIndex();
    }
      
    public int getLetterId() {
		return letterId;
	}
	public void setLetterId(int letterId) {
		this.letterId = letterId;
	}
	public int getSendUserId() {
		return sendUserId;
	}
	public void setSendUserId(int sendUserId) {
		this.sendUserId = sendUserId;
	}
	public String getSendUserName() {
		return sendUserName;
	}
	public void setSendUserName(String sendUserName) {
		this.sendUserName = sendUserName;
	}
	public int getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(int receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public String getReceiveUserName() {
		return receiveUserName;
	}
	public void setReceiveUserName(String receiveUserName) {
		this.receiveUserName = receiveUserName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
	public enum Status { 
        Unread(0),			//正在请求	
        Read(1);

        private final int index;
        private Status(int index) {
            this.index = index;
        }
        public int getIndex() {
            return index;
        }
    }
   
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{FriendLetter [")
		.append("letterId=" + letterId)
		.append(",sendUserId=" + sendUserId)
		.append(",sendUserName=" + sendUserName)
		.append(",receiveUserId=" + receiveUserId)
		.append(",receiveUserName=" + receiveUserName)
		.append(",status=" + status)
		.append(",content=" + content)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
