package com.nju.pams.model.asset;

import java.math.BigDecimal;

public class LoanRecord {
	private int loanId;					
	private int userId;		
	private int status;
	private int direction;
	private BigDecimal exceptRepayAmount;
	private int exceptKeepTime;					//暂时不考虑预期还款时间
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public LoanRecord() {
    	
    }
    
    public LoanRecord(int userId, int direction, BigDecimal exceptRepayAmount, String message) {
    	this.userId = userId;
    	this.status = LoanRecord.Status.Valid.getIndex();
    	this.direction = direction;
    	this.exceptRepayAmount = exceptRepayAmount;
    	this.exceptKeepTime = 0;
    	this.message = message;
    } 
    
    public enum Status { 
        Valid(0, "进行中"),
        InValid(1 ,"已结束");

        private final int index;
        private final String msg;
        private Status(int index, String msg) {
            this.index = index;
            this.msg = msg;
        }
        public int getIndex() {
            return index;
        }
        public String getMsg() {
        	return msg;
        }
        public static String getMsgFromInt(int index) {
        	for(Status status : Status.values()) {
        		if(index == status.getIndex()) {
        			return status.getMsg();
        		}
        	}
        	return "";
        }
    }
    
    public enum Direction { 
        Inflow(0, "贷入"),
        Outflow(1, "贷出");

        private final int index;
        private final String msg;
        private Direction(int index, String msg) {
            this.index = index;
            this.msg = msg;
        }
        public int toIndex() {
            return index;
        }
        public String getMsg() {
        	return msg;
        }
        public static String getMsgFromInt(int index) {
        	for(Direction d : Direction.values()) {
        		if(index == d.toIndex()) {
        			return d.getMsg();
        		}
        	}
        	return "";
        }
    }
	
	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public BigDecimal getExceptRepayAmount() {
		return exceptRepayAmount;
	}

	public void setExceptRepayAmount(BigDecimal exceptRepayAmount) {
		this.exceptRepayAmount = exceptRepayAmount;
	}

	public int getExceptKeepTime() {
		return exceptKeepTime;
	}

	public void setExceptKeepTime(int exceptKeepTime) {
		this.exceptKeepTime = exceptKeepTime;
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
		strBuf.append("{LoanRecord [")
		.append("loanId=" + loanId)
		.append(",userId=" + userId)
		.append(",status=" + status)
		.append(",direction=" + direction)
		.append(",exceptRepayAmount=" + exceptRepayAmount)
		.append(",exceptKeepTime=" + exceptKeepTime)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
