package com.nju.pams.model.shiro;

import java.io.Serializable;

public class ShiroRole implements Serializable {

	private static final long serialVersionUID = -3078822248920031371L;
	
	private int roleId;
	private String roleName;
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
	
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{roleId=" + roleId)
		.append(",roleName=" + roleName);
		return strBuf.toString();
	}
}

