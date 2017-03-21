package com.nju.pams.biz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nju.pams.biz.service.PamsRoleService;
import com.nju.pams.mapper.dao.PamsRoleDAO;
import com.nju.pams.model.PamsRole;

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class PamsRoleServiceImpl implements PamsRoleService {

	@Autowired
	PamsRoleDAO pamsRoleDAO;
	
    private static final Logger logger = Logger.getLogger(PamsRoleServiceImpl.class);
    
	@Override
	public PamsRole getPamsRoleByRoleId(Integer roleId) {
		return pamsRoleDAO.getPamsRoleByRoleId(roleId);
	}
	
	@Override
	public PamsRole getPamsRoleByRoleName(String roleName) {
		return pamsRoleDAO.getPamsRoleByRoleName(roleName);
	}

	@Override
	public void insertPamsRole(PamsRole pamsRole) {
		if(null == pamsRole || null == pamsRole.getRoleName()) {
			return ;
		} else {
			if(getPamsRoleByRoleName(pamsRole.getRoleName()) != null) {
				logger.info("该角色名已存在，插入角色失败");
				return;
			} else {
				pamsRoleDAO.insertPamsRole(pamsRole);
			}
		}
	}

	@Override
	public void updatePamsRole(PamsRole pamsRole) {
		pamsRoleDAO.updatePamsRole(pamsRole);
	}

	@Override
	public void deletePamsRoleByRoleId(Integer roleId) {
		pamsRoleDAO.deletePamsRoleByRoleId(roleId);
	}

	@Override
	public List<PamsRole> listRolesForUserByUsername(String username) {
		List<PamsRole> rolesList = pamsRoleDAO.listRolesForUserByUsername(username);
		if(null == rolesList) {
			logger.info("用户: " + username + "查询拥有的角色列表为null");
			//为避免返回null，将返回empty的arraylist
			return new ArrayList<PamsRole>();
		} else {
			return rolesList;
		}
	}

	@Override
	public int addRolesListForUser(Integer userId, List<PamsRole> rolesList) {
		if(null == userId || null == rolesList || rolesList.isEmpty()) {
			logger.info("用户批量添加角色权限时发生参数错误");
			return 0;
		} else {
			return pamsRoleDAO.addRolesListForUser(null, userId, rolesList);
		}
	}

	@Override
	public int deleteRolesListForUser(Integer userId, List<PamsRole> rolesList) {
		if(null == userId || null == rolesList || rolesList.isEmpty()) {
			logger.info("用户批量删除角色权限时发生参数错误");
			return 0;
		} else {
			return pamsRoleDAO.deleteRolesListForUser(userId, rolesList);
		}
	}

}
