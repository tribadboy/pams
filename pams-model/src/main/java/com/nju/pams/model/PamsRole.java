package com.nju.pams.model;

public class PamsRole {
	
	private int roleId;
	private String roleName;
    private String  createTime;
    private String  updateTime;
    
    public PamsRole() {
    	
    }
    
    public enum RoleType { 
        RoleUser(0, "role_user"),
        RoleHome(1, "role_home"),
        RoleFinance(2, "role_finance"),
        RoleAsset(3, "role_asset"),
        RoleConsumption(4, "role_consumption");

    	private final int index;
        private final String name;
        private RoleType(int index, String name) {
        	this.index = index;
            this.name = name;
        }
        public int getIndex() {
        	return index;
        }
        public String getName() {
            return name;
        }
        public static String getNameFromIndex(int index) {
        	for(RoleType type : RoleType.values()) {
        		if(index == type.getIndex()) {
        			return type.getName();
        		}
        	}
        	return "";
        }
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

