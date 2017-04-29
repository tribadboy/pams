package com.nju.pams.finance;

import java.math.BigDecimal;

public class PamsStrategy {
	
	private int strategyId;
	private String strategyName;
	private int userId;
	private String username;
	private int status;
	private int strategyType;
	private String startDate;
	private String endDate1;
	private String endDate2;
	private BigDecimal avgProfit;
	private String message;
    private String  createTime;
    private String  updateTime;
  
    public PamsStrategy() {
    	
    }
    
    public PamsStrategy(String strategyName, int userId, String username, int strategyType, 
    		String startDate, String endDate1, String endDate2, String message) {
    	this.strategyName = strategyName;
    	this.userId = userId;
    	this.username = username;
    	this.status = Status.NotConfirm.getIndex();
    	this.strategyType = strategyType;
    	this.startDate = startDate;
    	this.endDate1 = endDate1;
    	this.endDate2 = endDate2;
    	this.message = message;
    }
    
    public enum Status { 
		 NotConfirm(1, "未确认"),
		 NotStart(2, "未开始"),
	     Ongoing(3, "进行中"),
	     WindUp(4, "收尾中"),
	     Closed(5, "已结束");

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
	     public static String getMsgFromIndex(int index) {
	    	 for(Status status : Status.values()) {
	    		 if(index == status.getIndex()) {
	    			 return status.getMsg();
	    		 }
	    	 }
	    	 return "";
	     }    
	 }
    
    public enum Type { 
		 Short(1, "短期策略", 10, 15),
		 Medium(2, "中期策略", 30, 40),
	     Long(3, "长期策略", 80, 100);

	     private final int index;
	     private final String msg;
	     private final int period1;
	     private final int period2;
	     private Type(int index, String msg, int period1, int period2) {
	    	 this.index = index;
	    	 this.msg = msg;
	    	 this.period1 = period1;
	    	 this.period2 = period2;
	     }
	     public int getIndex() {
	    	 return index;
	     }
	     public String getMsg() {
	    	 return msg;
	     }
	     public int getPeriod1() {
	    	 return period1;
	     }
	     public int getPeriod2() {
	    	 return period2;
	     }
	     public static String getMsgFromIndex(int index) {
	    	 for(Type type : Type.values()) {
	    		 if(index == type.getIndex()) {
	    			 return type.getMsg();
	    		 }
	    	 }
	    	 return "";
	     }    
	 }
    
    public int getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(int strategyId) {
		this.strategyId = strategyId;
	}
	
    public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStrategyType() {
		return strategyType;
	}

	public void setStrategyType(int strategyType) {
		this.strategyType = strategyType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate1() {
		return endDate1;
	}

	public void setEndDate1(String endDate1) {
		this.endDate1 = endDate1;
	}

	public String getEndDate2() {
		return endDate2;
	}

	public void setEndDate2(String endDate2) {
		this.endDate2 = endDate2;
	}

	public BigDecimal getAvgProfit() {
		return avgProfit;
	}

	public void setAvgProfit(BigDecimal avgProfit) {
		this.avgProfit = avgProfit;
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
		strBuf.append("{PamsStrategy [")
		.append("strategyId=" + strategyId)
		.append(",strategyName=" + strategyName)
		.append(",userId=" + userId)
		.append(",username=" + username)
		.append(",status=" + status)
		.append(",strategyType=" + strategyType)
		.append(",startDate=" + startDate)
		.append(",endDate1=" + endDate1)
		.append(",endDate2=" + endDate2)
		.append(",avgProfit=" + avgProfit)
		.append(",message=" + message)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
