package com.nju.pams.model.shiro;

import java.io.Serializable;

public class ShiroPermission implements Serializable {

	private static final long serialVersionUID = -102604590216249087L;
	
	private int permissionId;
	private String permissionName;
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
	
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{permissionId=" + permissionId)
		.append(",permissionName=" + permissionName);
		return strBuf.toString();
	}

}
