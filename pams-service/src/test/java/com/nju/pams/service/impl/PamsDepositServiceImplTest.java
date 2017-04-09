package com.nju.pams.service.impl;

import java.math.BigDecimal;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsDepositService;
import com.nju.pams.model.asset.DepositChange;
import com.nju.pams.model.asset.DepositRecord;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsDepositServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsDepositServiceImplTest.class);  
	 
	 @Autowired
	 private PamsDepositService pamsDepositService;
	 
	 //@Ignore
	 @Test
	 public void depositServiceTest1() {
		 
		 //创建一个活期存款记录，并插入数据库		 
		 DepositRecord record = new DepositRecord(10,0,1,(float)2.25,0,"msg1");
		 pamsDepositService.makeDepositRecord(record, BigDecimal.valueOf(10000), "2016-01-01");
		 int depositId = record.getDepositId();
		 
		 //创建几条还款记录，并插入数据库
		 DepositChange c1 = new DepositChange(depositId,1,BigDecimal.valueOf(1000), "2016-02-02");
		 DepositChange c2 = new DepositChange(depositId,1,BigDecimal.valueOf(1000), "2016-03-02");
		 DepositChange c3 = new DepositChange(depositId,2,BigDecimal.valueOf(3000), "2016-04-02");
		 pamsDepositService.insertDepositChangeForInflowAndOutflow(c1);
		 logger.info("查询最大转出数额: " + pamsDepositService.getMaxValidAmountForOutflow(depositId, "2016-02-05"));
		 pamsDepositService.insertDepositChangeForInflowAndOutflow(c2);
		 logger.info("查询最大转出数额: " + pamsDepositService.getMaxValidAmountForOutflow(depositId, "2016-03-05"));
		 pamsDepositService.insertDepositChangeForInflowAndOutflow(c3);
		
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-01-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-02-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-03-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-04-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-10-10"));
		 
		 System.out.println(pamsDepositService.checkDateValid(depositId, "2016-01-01"));
		 System.out.println(pamsDepositService.checkDateValid(depositId, "2016-02-01"));
		 System.out.println(pamsDepositService.checkDateValid(depositId, "2016-02-02"));
		 System.out.println(pamsDepositService.checkDateValid(depositId, "2016-09-03"));
		 
		 logger.info("查询用户的有效存款：" + pamsDepositService.getValidDepositRecordsByUserId(10));
		 
		 logger.info("查询最大转出数额: " + pamsDepositService.getMaxValidAmountForOutflow(depositId, "2017-01-01"));
		 logger.info("查询最大转出数额: " + pamsDepositService.getMaxValidAmountForOutflow(depositId, "2017-02-02"));

		 
		 pamsDepositService.closeDepositRecord(depositId, "2017-01-01");
		 
		 System.out.println(pamsDepositService.checkDateValid(depositId, "2017-02-02"));
		 System.out.println(pamsDepositService.checkDateValid(depositId, "2017-02-03"));
		 
		 logger.info("第二次用户的有效存款：" + pamsDepositService.getValidDepositRecordsByUserId(10));
		 logger.info("查询存款的所有变动记录：" + pamsDepositService.getDepositChangeListByDepositId(depositId));
		 
		 pamsDepositService.deleteDepositRecordAndChange(depositId);
		 
		 logger.info("第三次查询用户的有效存款：" + pamsDepositService.getValidDepositRecordsByUserId(10));	 
		
	 }
	 
	 //@Ignore
	 @Test
	 public void depositServiceTest2() {
		 
		 //创建一个定期存款记录，并插入数据库	(3个月)	 
		 DepositRecord record = new DepositRecord(10,0,2,(float)1.0,(float)5.0,"msg1");
		 pamsDepositService.makeDepositRecord(record, BigDecimal.valueOf(10000), "2016-01-01");
		 int depositId = record.getDepositId();
		 
		 //创建几条还款记录，并插入数据库
		 DepositChange c1 = new DepositChange(depositId,1,BigDecimal.valueOf(1000), "2016-02-05");
		 DepositChange c2 = new DepositChange(depositId,1,BigDecimal.valueOf(1000), "2016-04-05");
		 DepositChange c3 = new DepositChange(depositId,2,BigDecimal.valueOf(3000), "2016-08-05");
		 pamsDepositService.insertDepositChangeForInflowAndOutflow(c1);
		 logger.info("查询最大转出数额: " + pamsDepositService.getMaxValidAmountForOutflow(depositId, "2016-02-08"));
		 pamsDepositService.insertDepositChangeForInflowAndOutflow(c2);
		 logger.info("查询最大转出数额: " + pamsDepositService.getMaxValidAmountForOutflow(depositId, "2016-04-08"));
		 pamsDepositService.insertDepositChangeForInflowAndOutflow(c3);
		
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-01-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-02-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-03-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-04-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-05-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-06-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-07-10"));
		 logger.info("计算存款总价值: " + pamsDepositService.computeDepositRecordValue(depositId, "2016-10-10"));
		 
		 logger.info("查询最大转出数额: " + pamsDepositService.getMaxValidAmountForOutflow(depositId, "2017-01-01"));
		 logger.info("查询最大转出数额: " + pamsDepositService.getMaxValidAmountForOutflow(depositId, "2017-02-02"));

		 
		 pamsDepositService.closeDepositRecord(depositId, "2018-01-01");		 
		 pamsDepositService.deleteDepositRecordAndChange(depositId);
		
	 }
}
