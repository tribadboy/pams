package com.nju.pams.model.system;

public class FinancialNews {
	
	public static final int MAX_CONTENT_LENGTH = 8000;
	public static final String NonePicture = "--";
	
	private int newsId;					
	private String title;
	private String origin;
	private String recordDate;
	private String pictureName;
	private String content;
    private String  createTime;
    private String  updateTime;
    
    public FinancialNews() {
    	
    }
    
    public FinancialNews(String title, String origin, String recordDate, String content) {
    	this.title = title;
    	this.origin = origin;
    	this.recordDate = recordDate;
    	this.pictureName = FinancialNews.NonePicture;
    	this.content = content;
    }
    
    public FinancialNews(String title, String origin, String recordDate, String pictureName, String content) {
    	this.title = title;
    	this.origin = origin;
    	this.recordDate = recordDate;
    	this.pictureName = pictureName;
    	this.content = content;
    }
    
	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
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

	public static int getMaxContentLength() {
		return MAX_CONTENT_LENGTH;
	}

	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{FinancialNews [")
		.append("newsId=" + newsId)
		.append(",title=" + title)
		.append(",origin=" + origin)
		.append(",recordDate=" + recordDate)
		.append(",pictureName=" + pictureName)
		.append(",content=" + content)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}
