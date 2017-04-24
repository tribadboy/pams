package com.nju.pams.service.impl;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsNoticeAndInformService;
import com.nju.pams.model.system.InformUserRef;
import com.nju.pams.model.system.PamsInform;
import com.nju.pams.model.system.PamsNotice;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsNoticeAndInformServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsNoticeAndInformServiceImplTest.class);  
	 
	 @Autowired
	 private PamsNoticeAndInformService pamsNoticeAndInformService;
	 
	 //@Ignore
	 @Test
	 public void noticeServiceTest() {
		 
		 PamsNotice pamsNotice = new PamsNotice("2017-04-24", "xx月xx日系统将进行维护，对此将关闭客户功能，对此为用户造成不便，敬请谅解！");
		 pamsNoticeAndInformService.insertPamsNotice(pamsNotice);
		 logger.info(pamsNoticeAndInformService.getAllPamsNotice());
		 
	 }
	 
	 //@Ignore
	 @Test
	 public void informServiceTest() {
		 
		 PamsInform inform1 = new PamsInform(PamsInform.InformType.Total.toIntValue(), "2017-04-24", "本系统将在4月底上线，敬请期待！");	 
		 PamsInform inform2 = new PamsInform(PamsInform.InformType.Special.toIntValue(), "2017-04-25", "由于您在XXXX时采用了违规操作，故将对您进行为期XX天的封号处理");
		 pamsNoticeAndInformService.insertInform(inform1);
		 pamsNoticeAndInformService.insertInform(inform2);
		 InformUserRef ref = new InformUserRef(inform2.getInformId(), 10, "vvv");
		 pamsNoticeAndInformService.insertInformUserRef(ref);
		 
	 }
}
