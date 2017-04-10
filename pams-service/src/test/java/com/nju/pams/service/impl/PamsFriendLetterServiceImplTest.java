package com.nju.pams.service.impl;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsFriendLetterService;
import com.nju.pams.model.socaility.FriendLetter;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsFriendLetterServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsFriendLetterServiceImplTest.class);  
	 
	 @Autowired
	 private PamsFriendLetterService pamsFriendLetterService;
	 
	 //@Ignore
	 @Test
	 public void pamsFriendLetterTest() {

		 //创建5条私信	 
		 FriendLetter fl1 = new FriendLetter(1,"aaa",2,"bbb","hello1");
		 FriendLetter fl2 = new FriendLetter(1,"aaa",2,"bbb","hello2");
		 FriendLetter fl3 = new FriendLetter(1,"aaa",2,"bbb","hello3");
		 FriendLetter fl4 = new FriendLetter(1,"aaa",2,"bbb","hello4");
		 FriendLetter fl5 = new FriendLetter(1,"aaa",2,"bbb","hello5");
		 pamsFriendLetterService.insertFriendLetter(fl1);
		 pamsFriendLetterService.insertFriendLetter(fl2);
		 pamsFriendLetterService.insertFriendLetter(fl3);
		 pamsFriendLetterService.insertFriendLetter(fl4);
		 pamsFriendLetterService.insertFriendLetter(fl5);
		 
		 logger.info("用户1发送的私信：" + pamsFriendLetterService.getFriendLettersBySendUserName("aaa"));
		 logger.info("用户2接收的私信：" + pamsFriendLetterService.getFriendLettersByReceiveUserName("bbb"));
		 
		 
		 //删除几条私信
		 pamsFriendLetterService.deleteFriendLetterByLetterId(fl1.getLetterId());
		 pamsFriendLetterService.deleteFriendLetterByLetterId(fl5.getLetterId());
		 
		 //设置几条私信为已读状态
		 pamsFriendLetterService.setReadForFriendLetterByLetterId(fl2.getLetterId());
		 pamsFriendLetterService.setReadForFriendLetterByLetterId(fl3.getLetterId());
		 
		 logger.info("用户1发送的私信：" + pamsFriendLetterService.getFriendLettersBySendUserName("aaa"));
		 logger.info("用户2接收的私信：" + pamsFriendLetterService.getFriendLettersByReceiveUserName("bbb"));
		 
	 }
	
}
