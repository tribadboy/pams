package com.nju.pams.service.impl;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsUserService;
import com.nju.pams.model.PamsUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsUserServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsUserServiceImplTest.class);  
	 
	 @Autowired
	 private PamsUserService pamsUserService;
	 
	 //@Ignore
	 @Test
	 public void pamsUserTest() {
		 //如果存在该用户首先删除
		 pamsUserService.deletePamsUserByUsername("testUser");
		 PamsUser pamsUser = new PamsUser();
		 pamsUser.setUsername("testUser");
		 pamsUser.setPassword("11111");
		 pamsUser.setStatus(PamsUser.Status.Valid.toIntValue());
		 pamsUser.setPhone("18817766666");
		 pamsUser.setMail("aaabbbccc@qq.com");
		 
		 //测试拆入数据是否成功
		 pamsUserService.insertPamsUser(pamsUser);
		 logger.info("首次创建用户：" + pamsUserService.getPamsUserById(pamsUser.getId()));
		 
		 //测试插入相同用户名的用户是否成功
		 pamsUser.setId(pamsUser.getId() + 1);
		 pamsUser.setPassword("22222");
		 Assert.assertEquals(false, pamsUserService.insertPamsUser(pamsUser));
		 
		 //修改用户信息，5s后重新插入数据库，检查更新用户信息是否成功
		 pamsUser.setId(pamsUser.getId() - 1);
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
		 logger.info("修改用户信息后：" + pamsUserService.getPamsUserById(pamsUser.getId()));
		 
		 //检查删除用户是否成功
		 pamsUserService.deletePamsUserByUsername("testUser");
		 logger.info("删除用户后：" + pamsUserService.getPamsUserById(pamsUser.getId()));
	 }
}
