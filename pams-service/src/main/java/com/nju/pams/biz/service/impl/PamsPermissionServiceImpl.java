package com.nju.pams.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsPermissionService;
import com.nju.pams.mapper.dao.PamsPermissionDAO;
import com.nju.pams.model.PamsPermission;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsPermissionServiceImpl implements PamsPermissionService {

	@Autowired
	PamsPermissionDAO pamsPermissionDAO;
	
	private static final Logger logger = Logger.getLogger(PamsPermissionServiceImpl.class);
	
	@Override
	public PamsPermission getPamsPermissionByPermissionId(Integer permissionId) {
		return pamsPermissionDAO.getPamsPermissionByPermissionId(permissionId);
	}
	
	@Override
	public PamsPermission getPamsPermissionByPermissionName(String permissionName) {
		return pamsPermissionDAO.getPamsPermissionByPermissionName(permissionName);
	}

	@Override
	public void insertPamsPermission(PamsPermission pamsPermission) {
		if(null == pamsPermission || null == pamsPermission.getPermissionName()) {
			return;
		} else {
			if(getPamsPermissionByPermissionName(pamsPermission.getPermissionName()) != null) {
				logger.info("该权限名已经存在，插入权限失败");
				return;
			} else {
				pamsPermissionDAO.insertPamsPermission(pamsPermission);
			}
		}	
	}

	@Override
	public void updatePamsPermission(PamsPermission pamsPermission) {
		pamsPermissionDAO.updatePamsPermission(pamsPermission);
	}

	@Override
	public void deletePamsPermissionByPermissionId(Integer permissionId) {
		pamsPermissionDAO.deletePamsPermissionByPermissionId(permissionId);
	}

	@Override
	public List<PamsPermission> listPermissionsForRoleByRoleName(String roleName) {
		List<PamsPermission> permissionsList = pamsPermissionDAO.listPermissionsForRoleByRoleName(roleName);
		if(null == permissionsList) {
			logger.info("角色: " + roleName + "查询拥有的权限列表为null");
			//为避免返回null，将返回empty的arraylist
			return new ArrayList<PamsPermission>();
		} else {
			return permissionsList;
		}
	}

	@Override
	public int addPermissionsListForRole(Integer roleId, List<PamsPermission> permissionsList) {
		if(null == roleId || null == permissionsList || permissionsList.isEmpty()) {
			logger.info("角色批量添加权限时发生参数错误");
			return 0;
		} else {
			return pamsPermissionDAO.addPermissionsListForRole(null, roleId, permissionsList);
		}
	}

	@Override
	public int deletePermissionsListForRole(Integer roleId, List<PamsPermission> permissionsList) {
		if(null == roleId || null == permissionsList || permissionsList.isEmpty()) {
			logger.info("角色批量删除权限时发生参数错误");
			return 0;
		} else {
			return pamsPermissionDAO.deletePermissionsListForRole(roleId, permissionsList);
		}
	}

}
