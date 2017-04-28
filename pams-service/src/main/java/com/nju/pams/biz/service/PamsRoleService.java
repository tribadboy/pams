package com.nju.pams.biz.service;

import java.util.List;

import com.nju.pams.model.PamsRole;

public interface PamsRoleService {
	PamsRole getPamsRoleByRoleId(Integer roleId);
	
	PamsRole getPamsRoleByRoleName(String roleName);
	
	void insertPamsRole(PamsRole pamsRole);
	
	void updatePamsRole(PamsRole pamsRole);
	
	void deletePamsRoleByRoleId(Integer roleId);
	
	List<PamsRole> listRolesForUserByUsername(String username);
	
	int addRolesListForUser(Integer userId,List<PamsRole> rolesList);
	
	int deleteRolesListForUser(Integer userId,List<PamsRole> rolesList);
	
	void addAllPamsRoleForNewUser(Integer userId);
}
