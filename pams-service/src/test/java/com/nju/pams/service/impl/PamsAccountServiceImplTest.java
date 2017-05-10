package com.nju.pams.service.impl;

import java.math.BigDecimal;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsAccountService;
import com.nju.pams.model.consumption.ConsumptionAccount;
import com.nju.pams.model.consumption.ConsumptionCondition;
import com.nju.pams.model.consumption.ConsumptionEnum;
import com.nju.pams.util.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsAccountServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsAccountServiceImplTest.class);  
	 
	 @Autowired
	 private PamsAccountService pamsAccountService;
	 
	 //@Ignore
	 @Test
	 public void pamsAccountTest() {

		 //创建5条账目，并插入数据库
		 ConsumptionAccount a1 = new ConsumptionAccount(1,5,BigDecimal.valueOf(10.00),"2017-02-23","msg1");
		 ConsumptionAccount a2 = new ConsumptionAccount(1,6,BigDecimal.valueOf(3.45),"2017-02-20","msg2");
		 ConsumptionAccount a3 = new ConsumptionAccount(1,6,BigDecimal.valueOf(100.90),"2017-02-27","msg3");
		 ConsumptionAccount a4 = new ConsumptionAccount(2,7,BigDecimal.valueOf(103.20),"2017-02-21","msg4");
		 ConsumptionAccount a5 = new ConsumptionAccount(2,8,BigDecimal.valueOf(1.89),"2017-02-23","msg5");
		 pamsAccountService.insertConsumptionAccount(a1);
		 pamsAccountService.insertConsumptionAccount(a2);
		 pamsAccountService.insertConsumptionAccount(a3);
		 pamsAccountService.insertConsumptionAccount(a4);
		 pamsAccountService.insertConsumptionAccount(a5);
		 
		 //查询账目
		 logger.info("a1账目：" + pamsAccountService.getConsumptionAccountByAccountId(a1.getAccountId()));
		 logger.info("用户2的账目：" + pamsAccountService.getConsumptionAccountListByUserId(2));
		 
		 //更新账目a1
		 a1.setCost(BigDecimal.valueOf(7123.45));
		 pamsAccountService.updateConsumptionAccount(a1);
		 logger.info("更新后的a1账目：" + pamsAccountService.getConsumptionAccountByAccountId(a1.getAccountId()));
		 
		 //删除账目a4
		 pamsAccountService.deleteConsumptionAccountByAccountId(a4.getAccountId());
		 logger.info("删除后的用户2的的账目：" + pamsAccountService.getConsumptionAccountListByUserId(2));
		 
		 //多条件查询账目
		 ConsumptionCondition c1 = new ConsumptionCondition();
		 ConsumptionCondition c2 = new ConsumptionCondition(1, Arrays.asList(5,6,7),
				 "2017-02-23", null, null, null);
		 ConsumptionCondition c3 = new ConsumptionCondition(1, null, null, null, 
				 BigDecimal.valueOf(0.00), BigDecimal.valueOf(150.00));
		 logger.info("c1无条件查询的结果：" + pamsAccountService.selectAccountByCondition(c1));
		 logger.info("c2查询的结果：" + pamsAccountService.selectAccountByCondition(c2));
		 logger.info("c3查询的结果：" + pamsAccountService.selectAccountByCondition(c3));
		 
		 //删除创建的所有账目
		 pamsAccountService.deleteConsumptionAccountByAccountId(a1.getAccountId());
		 pamsAccountService.deleteConsumptionAccountByAccountId(a2.getAccountId());
		 pamsAccountService.deleteConsumptionAccountByAccountId(a3.getAccountId());
		 pamsAccountService.deleteConsumptionAccountByAccountId(a4.getAccountId());
		 pamsAccountService.deleteConsumptionAccountByAccountId(a5.getAccountId());
	 }
	 
	//@Ignore
	@Test
	public void makeDataForAccountsTest() {
//		for(int u = 19; u <= 22; u++){
		for(ConsumptionEnum e : ConsumptionEnum.values()) {
			System.out.println(e);
			int count = 0;
			int amount = 0;
			
			switch(e) {
			case BookCost:		count = 30; amount = 50;
				break;
			case ChotheCost:	count = 80; amount = 100;
				break;
			case Commodity:		count = 200; amount = 10;
				break;
			case HouseCost:		count = 12; amount = 1000;
				break;
			case LivingCost:	count = 300; amount = 10;
				break;
			case MealCost:		count = 500; amount = 15;
				break;
			case OtherCost:		count = 20;	amount = 80;
				break;
			case PhoneCost:		count = 20;	amount = 60;
				break;
			case TrafficCost:	count = 200; amount = 5;
				break;
			case TravelCost:	count = 3;	amount = 1000;
				break;
			default:			count = 50; amount = 50;
				break;
			}
			
			for(int i = 1; i <= count; i++) {
				String randomDateStr = DateUtil.getRandomDateString("2015-12-01", "2017-05-10");
				ConsumptionAccount c = new ConsumptionAccount(10, e.getCode(),
						BigDecimal.valueOf((0.8 + Math.random() * 0.4) * amount), 
						randomDateStr, "msg_" + e.getCode() + "_" + i);
				pamsAccountService.insertConsumptionAccount(c);
			}
		}
		//}
	}
}
