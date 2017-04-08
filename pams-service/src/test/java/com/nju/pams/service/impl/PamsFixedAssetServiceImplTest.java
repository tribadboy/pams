package com.nju.pams.service.impl;

import java.math.BigDecimal;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsFixedAssetService;
import com.nju.pams.model.asset.FixedAsset;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsFixedAssetServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsFixedAssetServiceImplTest.class);  
	 
	 @Autowired
	 private PamsFixedAssetService pamsFixedAssetService;
	 
	 //@Ignore
	 @Test
	 public void fixedAccountTest() {

		 //创建5条固定资产，并插入数据库		 
		 FixedAsset fa1 = new FixedAsset(1, BigDecimal.valueOf(10000.50), "2017-02-05", "msg1");
		 FixedAsset fa2 = new FixedAsset(1, BigDecimal.valueOf(34520.50), "2017-02-09", "msg2");
		 FixedAsset fa3 = new FixedAsset(1, BigDecimal.valueOf(8000.50), "2017-03-12", "msg3");
		 FixedAsset fa4 = new FixedAsset(2, BigDecimal.valueOf(9000.33), "2017-02-07", "msg4");
		 FixedAsset fa5 = new FixedAsset(2, BigDecimal.valueOf(5600.50), "2017-02-15", "msg5");
		 pamsFixedAssetService.insertFixedAsset(fa1);
		 pamsFixedAssetService.insertFixedAsset(fa2);
		 pamsFixedAssetService.insertFixedAsset(fa3);
		 pamsFixedAssetService.insertFixedAsset(fa4);
		 pamsFixedAssetService.insertFixedAsset(fa5);
		 		 
		 //查询资产
		 logger.info("fa1固定资产：" + pamsFixedAssetService.getFixedAssetByAssetId(fa1.getAssetId()));
		 logger.info("用户2的所有固定资产：" + pamsFixedAssetService.getFixedAssetListByUserId(2));
		 
		 //更新固定资产fa1
		 fa1.setRecordValue(BigDecimal.valueOf(200.11));
		 pamsFixedAssetService.updateFixedAsset(fa1);
		 logger.info("更新后的fa1资产：" + pamsFixedAssetService.getFixedAssetByAssetId(fa1.getAssetId()));
		 
		 //删除资产fa4
		 pamsFixedAssetService.deleteFixedAssetByAssetId(fa4.getAssetId());
		 logger.info("删除后的用户2的的资产：" + pamsFixedAssetService.getFixedAssetListByUserId(2));
		 
		 //多条件查询账目
		 logger.info("查询用户1在某个时间段内记录的资产：" + pamsFixedAssetService.getFixedAssetListByUserIdInPeriodTime(
				 1, "2017-02-05", "2017-03-13"));
		 
		 //删除创建的所有资产记录
		 pamsFixedAssetService.deleteFixedAssetByAssetId(fa1.getAssetId());
		 pamsFixedAssetService.deleteFixedAssetByAssetId(fa2.getAssetId());
		 pamsFixedAssetService.deleteFixedAssetByAssetId(fa3.getAssetId());
		 pamsFixedAssetService.deleteFixedAssetByAssetId(fa4.getAssetId());
		 pamsFixedAssetService.deleteFixedAssetByAssetId(fa5.getAssetId());
	 }
}
