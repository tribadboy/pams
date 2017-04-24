package com.nju.pams.model.system;

/**
 * 默认显示针对该用户的所有有效通知
 */
public class PamsInform {
	
	private int informId;						
	private int status;
	private int informTypeId;
	private String recordDate;
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public PamsInform() {
    	
    }
   
    public PamsInform(int informTypeId, String recordDate, String message) {
    	this.status = Status.Valid.toIntValue();
    	this.informTypeId = informTypeId;
    	this.recordDate = recordDate;
    	this.message = message;
    } 
       
    public enum Status { 
        Valid(0, "有效"),
        Invalid(1, "无效");

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
    
    public enum InformType { 
        Total(0, "有效"),
        Special(1, "无效");

        private final int value;
        private final String msg;
        private InformType(int value, String msg) {
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
    
	public int getInformId() {
		return informId;
	}

	public void setInformId(int informId) {
		this.informId = informId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getInformTypeId() {
		return informTypeId;
	}

	public void setInformTypeId(int informTypeId) {
		this.informTypeId = informTypeId;
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

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{PamsInform [")
		.append("informId=" + informId)
		.append(",status=" + status)
		.append(",informTypeId=" + informTypeId)
		.append(",recordDate=" + recordDate)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
