package com.nju.pams.model;

public class PamsRole {
	
	private int roleId;
	private String roleName;
    private String  createTime;
    private String  updateTime;
    
    public PamsRole() {
    	
    }
    
    public PamsRole(String roleName) {
    	this.roleName = roleName;
    }
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		strBuf.append("{PamsRole [")
		.append("roleId=" + roleId)
		.append(",roleName=" + roleName)
		.append(",createTime=" + createTime)
		.append(",updateTime=" + updateTime)
		.append("]}");
		return strBuf.toString();
	}
}

