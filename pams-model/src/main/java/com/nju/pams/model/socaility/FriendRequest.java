package com.nju.pams.model.socaility;

public class FriendRequest {
	private int requestId;					
	private int requestUserId;		
	private String requestUserName;
	private int responseUserId;
	private String responseUserName;
	private int status;
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public FriendRequest() {
    	
    }
    
    public FriendRequest(int requestUserId, String requestUserName, int responseUserId, String responseUserName,
    		String message) {
    	this.requestUserId = requestUserId;
    	this.requestUserName = requestUserName;
    	this.responseUserId = responseUserId;
    	this.responseUserName = responseUserName;
    	this.status = FriendRequest.Status.Requesting.getIndex();
    	this.message = message;
    }
      
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getRequestUserId() {
		return requestUserId;
	}
	public void setRequestUserId(int requestUserId) {
		this.requestUserId = requestUserId;
	}
	public String getRequestUserName() {
		return requestUserName;
	}
	public void setRequestUserName(String requestUserName) {
		this.requestUserName = requestUserName;
	}
	public int getResponseUserId() {
		return responseUserId;
	}
	public void setResponseUserId(int responseUserId) {
		this.responseUserId = responseUserId;
	}
	public String getResponseUserName() {
		return responseUserName;
	}
	public void setResponseUserName(String responseUserName) {
		this.responseUserName = responseUserName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
	
	public enum Status { 
        Requesting(0),			//请求中	
        Rejected(1);			//已拒绝

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
		strBuf.append("{FriendRequest [")
		.append("requestId=" + requestId)
		.append(",requestUserId=" + requestUserId)
		.append(",requestUserName=" + requestUserName)
		.append(",responseUserId=" + responseUserId)
		.append(",responseUserName=" + responseUserName)
		.append(",status=" + status)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
