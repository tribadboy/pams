package com.nju.pams.model.asset;

import java.math.BigDecimal;

public class LoanRecord {
	private int loanId;					
	private int userId;		
	private int status;
	private int direction;
	private BigDecimal exceptRepayAmount;
	private int exceptKeepTime;
	private String message;
    private String  createTime;
    private String  updateTime;
    
    public LoanRecord() {
    	
    }
    
    public LoanRecord(int userId, int status, int direction, BigDecimal exceptRepayAmount, 
    		int  exceptKeepTime, String message) {
    	this.userId = userId;
    	this.status = status;
    	this.direction = direction;
    	this.exceptRepayAmount = exceptRepayAmount;
    	this.exceptKeepTime = exceptKeepTime;
    	this.message = message;
    }
    
    
    public enum Status { 
        Valid(0),
        InValid(1);

        private final int index;
        private Status(int index) {
            this.index = index;
        }
        public int getIndex() {
            return index;
        }
    }
    
    public enum Direction { 
        Inflow(0),
        Outflow(1);

        private final int index;
        private Direction(int index) {
            this.index = index;
        }
        public int toIndex() {
            return index;
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
