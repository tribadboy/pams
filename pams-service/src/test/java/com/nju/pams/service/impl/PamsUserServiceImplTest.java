package com.nju.pams.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsPermissionService;
import com.nju.pams.biz.service.PamsRoleService;
import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.model.PamsPermission;
import com.nju.pams.model.PamsRole;
import com.nju.pams.model.PamsUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsUserServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsUserServiceImplTest.class);  
	 
	 @Autowired
	 private PamsUserService pamsUserService;
	 @Autowired
	 private PamsRoleService pamsRoleService;
	 @Autowired
	 private PamsPermissionService pamsPermissionService;
	 
	 //@Ignore
	 @Test
	 public void pamsUserTest() {
		 //如果存在该用户首先删除
		 pamsUserService.deletePamsUserByUsername("testUser"); 
		 PamsUser pamsUser = new PamsUser("testUser","11111",PamsUser.Status.Valid.toIntValue(),
				 "18817766666","aaabbbccc@qq.com");
		 
		 //测试插入用户信息是否成功
		 pamsUserService.insertPamsUser(pamsUser);
		 logger.info("首次创建用户：" + pamsUserService.getPamsUserByUserId(pamsUser.getUserId()));
		 
		 //测试插入相同用户名的用户是否成功
		 pamsUser.setUserId(pamsUser.getUserId() + 1);
		 pamsUser.setPassword("22222");
		 Assert.assertEquals(false, pamsUserService.insertPamsUser(pamsUser));
		 
		 //修改用户信息，5s后重新插入数据库，检查更新用户信息是否成功
		 pamsUser.setUserId(pamsUser.getUserId() - 1);
		 pamsUser.setMail("qwerqwerqwer@163.com");
		 try {
			 long start = System.currentTimeMillis();   
			 Thread.sleep(5000);  
			 long end = System.currentTimeMillis();   
			 logger.info("程序暂停" + (end - start) + "ms");
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 pamsUserService.updatePamsUser(pamsUser);
		 logger.info("修改用户信息后：" + pamsUserService.getPamsUserByUserId(pamsUser.getUserId()));
		 
		 //检查删除用户是否成功
		 pamsUserService.deletePamsUserByUsername("testUser");
		 logger.info("删除用户后：" + pamsUserService.getPamsUserByUserId(pamsUser.getUserId()));
	 }
	 
	 //@Ignore
	 @Test
	 public void pamsUserAndRoleTest() {
		 //如果存在该用户首先删除
		 pamsUserService.deletePamsUserByUsername("testUser"); 
		 PamsUser pamsUser = new PamsUser("testUser","11111",PamsUser.Status.Valid.toIntValue(),
				 "18817766666","aaabbbccc@qq.com");
		 
		 //测试插入用户是否成功
		 pamsUserService.insertPamsUser(pamsUser);
		 int userId = pamsUser.getUserId();
		 String username = pamsUser.getUsername();
		 logger.info("首次创建用户：" + pamsUserService.getPamsUserByUserId(userId));
		 
		 //创建五个角色权限，插入角色表中，并赋予该用户
		 int num = 5;
		 List<PamsRole> rolesList = new ArrayList<PamsRole>(num);
		 for(int i = 1; i <= num; i++) {
			 PamsRole role = new PamsRole("role_name_" + i);
			 rolesList.add(role);
			 pamsRoleService.insertPamsRole(role);
		 }
		 pamsRoleService.addRolesListForUser(userId, rolesList);
		 
		 //查询该用户是否拥有这些角色权限
		 List<PamsRole> queryRoles = pamsRoleService.listRolesForUserByUsername(username);
		 logger.info("用户赋予角色权限后 " + username + "拥有的角色列表：" + queryRoles);
		 
		 //删除用户拥有的这些角色权限
		 pamsRoleService.deleteRolesListForUser(userId, queryRoles);
		 List<PamsRole> queryRoles2 = pamsRoleService.listRolesForUserByUsername(username);
		 logger.info("用户删除角色权限后 " + username + "拥有的角色列表：" + queryRoles2);
		 
		 //删除创建的所有角色和用户
		 for(PamsRole role : rolesList) {
			 pamsRoleService.deletePamsRoleByRoleId(role.getRoleId());
		 }
		 pamsUserService.deletePamsUserByUsername(username);
		 	 
		 
	 }
	 
	 //@Ignore
	 @Test
	 public void pamsRoleAndPermissionTest() {
		 //创建一个角色
		 PamsRole role = new PamsRole("role_test");
			 
		 //测试插入角色是否成功
		 pamsRoleService.insertPamsRole(role);
	     int roleId = role.getRoleId();
		 String roleName = role.getRoleName();
		 logger.info("首次创建角色：" + pamsRoleService.getPamsRoleByRoleId(roleId));
			 
		 //创建五个权限，插入权限表中，并赋予该角色
		 int num = 5;
		 List<PamsPermission> permissionsList = new ArrayList<PamsPermission>(num);
		 for(int i = 1; i <= num; i++) {
			 PamsPermission permission = new PamsPermission("permission_name_" + i);
			 permissionsList.add(permission);
			 pamsPermissionService.insertPamsPermission(permission);
		 }
		 pamsPermissionService.addPermissionsListForRole(roleId, permissionsList);
			 
		 //查询该角色是否拥有这些权限
		 List<PamsPermission> queryPermissions = pamsPermissionService.listPermissionsForRoleByRoleName(roleName);
		 logger.info("角色赋予权限后 " + roleName + "拥有的权限列表：" + queryPermissions);
			 
		 //删除该角色拥有的这些权限
		 pamsPermissionService.deletePermissionsListForRole(roleId, permissionsList);
		 List<PamsPermission> queryPermissions2 = pamsPermissionService.listPermissionsForRoleByRoleName(roleName);
		 logger.info("角色删除权限后 " + roleName + "拥有的权限列表：" + queryPermissions2);
			 
		 //删除创建的所有角色和权限
		 for(PamsPermission permission : permissionsList) {
			 pamsPermissionService.deletePamsPermissionByPermissionId(permission.getPermissionId());
		 }
		 pamsRoleService.deletePamsRoleByRoleId(roleId);
			 	 	 
	 }
	 
//	 @Test
//	 public void testAddAllRolesForAllUser() {
//		 List<PamsUser> allUser = pamsUserService.getPamsUserList();
//		 if(CollectionUtils.isNotEmpty(allUser)) {
//			 for(PamsUser user : allUser) {
//				 pamsRoleService.addAllPamsRoleForNewUser(user.getUserId());
//			 }
//		 }
//	 }
}
