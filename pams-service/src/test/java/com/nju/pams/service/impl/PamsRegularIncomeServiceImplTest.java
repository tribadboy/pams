package com.nju.pams.service.impl;

import java.math.BigDecimal;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsRegularIncomeService;
import com.nju.pams.model.asset.RegularIncome;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsRegularIncomeServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsRegularIncomeServiceImplTest.class);  
	 
	 @Autowired
	 private PamsRegularIncomeService pamsRegularIncomeService;
	 
	 //@Ignore
	 @Test
	 public void regualrIncomeTest() {

		 //创建5条收入，并插入数据库		 
		 RegularIncome ri1 = new RegularIncome(1, BigDecimal.valueOf(1111.11), "2017-02-07", "msg1");
		 RegularIncome ri2 = new RegularIncome(1, BigDecimal.valueOf(2222.22), "2017-02-14", "msg2");
		 RegularIncome ri3 = new RegularIncome(1, BigDecimal.valueOf(3333.33), "2017-03-11", "msg3");
		 RegularIncome ri4 = new RegularIncome(2, BigDecimal.valueOf(4444.44), "2017-02-09", "msg4");
		 RegularIncome ri5 = new RegularIncome(2, BigDecimal.valueOf(5555.55), "2017-02-19", "msg5");
		 pamsRegularIncomeService.insertRegularIncome(ri1);
		 pamsRegularIncomeService.insertRegularIncome(ri2);
		 pamsRegularIncomeService.insertRegularIncome(ri3);
		 pamsRegularIncomeService.insertRegularIncome(ri4);
		 pamsRegularIncomeService.insertRegularIncome(ri5);
		 		 
		 //查询资产
		 logger.info("ri1收入记录：" + pamsRegularIncomeService.getRegularIncomeByIncomeId(ri1.getIncomeId()));
		 logger.info("用户2的所有收入记录：" + pamsRegularIncomeService.getRegularIncomeListByUserId(2));
		 
		 //更新收入记录ri1
		 ri1.setRecordAmount(BigDecimal.valueOf(9999.99));
		 pamsRegularIncomeService.updateRegularIncome(ri1);
		 logger.info("更新后的ri1收入：" + pamsRegularIncomeService.getRegularIncomeByIncomeId(ri1.getIncomeId()));
		 
		 //删除资产ri4
		 pamsRegularIncomeService.deleteRegularIncomeByIncomeId(ri4.getIncomeId());
		 logger.info("删除后的用户2的的收入：" + pamsRegularIncomeService.getRegularIncomeListByUserId(2));
		 
		 //多条件查询收入记录
		 logger.info("查询用户1在某个时间段内记录的收入：" + pamsRegularIncomeService.getRegularIncomeListByUserIdInPeriodTime(
				 1, "2017-02-05", "2017-03-11"));
		 
		 //删除创建的所有资产记录
		 pamsRegularIncomeService.deleteRegularIncomeByIncomeId(ri1.getIncomeId());
		 pamsRegularIncomeService.deleteRegularIncomeByIncomeId(ri2.getIncomeId());
		 pamsRegularIncomeService.deleteRegularIncomeByIncomeId(ri3.getIncomeId());
		 pamsRegularIncomeService.deleteRegularIncomeByIncomeId(ri4.getIncomeId());
		 pamsRegularIncomeService.deleteRegularIncomeByIncomeId(ri5.getIncomeId());
	 }
}
