package com.nju.pams.model.system;

/**
 * 默认仅在页面中显示最新的一条有效公告
 * 公开给所有用户
 */
public class PamsNotice {
	
	public static final String DefaultMessage = "当前没有任何公告信息";
	
	private int noticeId;						
	private int status;
	private String recordDate;
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public PamsNotice() {
    	
    }
    
    public PamsNotice(String recordDate, String message) {
    	status = PamsNotice.Status.Valid.toIntValue();
    	this.recordDate = recordDate;
    	this.message = message;
    }
       
    public enum Status { 
        Valid(0, "进行中"),
        Invalid(1, "已结束");

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

	public int getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
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

	public static String getDefaultmessage() {
		return DefaultMessage;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{PamsNotice [")
		.append("noticeId=" + noticeId)
		.append(",status=" + status)
		.append(",recordDate=" + recordDate)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
