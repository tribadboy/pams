package com.nju.pams.model;

public class PamsPermission {
	
	private int permissionId;
	private String permissionName;
    private String  createTime;
    private String  updateTime;
    
    public PamsPermission() {
    	
    }
    
    public PamsPermission(String permissionName) {
    	this.permissionName = permissionName;
    }
    
	public int getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
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
		strBuf.append("{PamsPermission [")
		.append("permissionId=" + permissionId)
		.append(",permissionName=" + permissionName)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)				
		.append("]}");
		return strBuf.toString();
	}

}
