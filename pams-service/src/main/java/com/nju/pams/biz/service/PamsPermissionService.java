package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.PamsPermission;

public interface PamsPermissionService {
	PamsPermission getPamsPermissionByPermissionId(Integer permissionId);
	
	PamsPermission getPamsPermissionByPermissionName(String permissionName);
	
	void insertPamsPermission(PamsPermission pamsPermission);
	
	void updatePamsPermission(PamsPermission pamsPermission);
	
	void deletePamsPermissionByPermissionId(Integer permissionId);
	
	List<PamsPermission> listPermissionsForRoleByRoleName(String roleName);
	
	int addPermissionsListForRole(Integer roleId,List<PamsPermission> permissionsList);
	
	int deletePermissionsListForRole(Integer roleId,List<PamsPermission> permissionsList);
}
