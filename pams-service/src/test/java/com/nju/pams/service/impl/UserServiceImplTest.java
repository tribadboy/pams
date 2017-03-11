package com.nju.pams.service.impl;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.cacheable.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class UserServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(UserServiceImplTest.class);  
	 
	 @Autowired
	 private UserService userService;
	 
	 //@Ignore
	 @Test
	 public void getUserByIdTest() {
		 System.out.println(userService.getUserById(1));
		 logger.error(userService.getUserById(1));
	 }
}
