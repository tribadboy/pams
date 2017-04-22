package com.nju.pams.model.system;

public class Feedback {
	
	public static final String NoTypeStr = "--";
	
	private int backId;					
	private int userId;		
	private String username;
	private String recordTitle;
	private String recordDate;
	private String feedTypeStr;
	private int status;
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public Feedback() {
    	
    }
    
    public Feedback(int userId, String username, String recordTitle, String recordDate, String feedTypeStr,
    		String message) {
    	this.userId = userId;
    	this.username = username;
    	this.recordTitle = recordTitle;
    	this.recordDate = recordDate;
    	this.feedTypeStr = feedTypeStr;
    	this.status = Status.Unchecked.toIntValue();
    	this.message = message;
    }
    
    public enum Status { 
        Unchecked(0, "未查看"),
        Checked(1, "已查看");

        private final int value;
        private final String msg;
        private Status(int value, String msg) {
            this.value = value;
            this.msg = msg;
        }
        public int toIntValue() {
            return value;
        }
        public String toMsgValue() {
            return msg;
        }
        public static String getMsgFromInt(int value) {
        	for(Status s : Status.values()) {
        		if(s.toIntValue() == value) {
        			return s.toMsgValue();
        		}
        	}
        	return "";
        }
    }
    
    public enum FeedType { 
        SystemHalt(1, "系统卡顿"),
        LessFunction(2, "功能太少"),
        ErrorOperation(3, "操作不佳"),
        Suggest(4, "建议与意见");

        private final int value;
        private final String msg;
        private FeedType(int value, String msg) {
            this.value = value;
            this.msg = msg;
        }
        public int toIntValue() {
            return value;
        }
        public String toMsgValue() {
            return msg;
        }
        public static String getMsgFromInt(int value) {
        	for(Status s : Status.values()) {
        		if(s.toIntValue() == value) {
        			return s.toMsgValue();
        		}
        	}
        	return "";
        }
    }
    	
	public int getBackId() {
		return backId;
	}

	public void setBackId(int backId) {
		this.backId = backId;
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

	public String getRecordTitle() {
		return recordTitle;
	}

	public void setRecordTitle(String recordTitle) {
		this.recordTitle = recordTitle;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getFeedTypeStr() {
		return feedTypeStr;
	}

	public void setFeedTypeStr(String feedTypeStr) {
		this.feedTypeStr = feedTypeStr;
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

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{Feedback [")
		.append("backId=" + backId)
		.append(",userId=" + userId)
		.append(",username=" + username)
		.append(",recordTitle=" + recordTitle)
		.append(",recordDate=" + recordDate)
		.append(",feedTypeStr=" + feedTypeStr)
		.append(",status=" + status)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
