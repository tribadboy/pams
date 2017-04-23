package com.nju.pams.service.impl;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsFinancialNewsService;
import com.nju.pams.model.system.FinancialNews;
import com.nju.pams.util.FileUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsFinancialNewsServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsFinancialNewsServiceImplTest.class);  
	 
	 @Autowired
	 private PamsFinancialNewsService pamsFinancialNewsService;
	 
	 //@Ignore
	 @Test
	 public void financialNewsServiceTest1() {
		 String title = "郑永年撰文：房地产存巨大泡沫 已绑架了中国经济";
		 String origin = "北京晨报(北京)";
		 String recordDate = "2017-04-23";
		 String pictureName = "1492968865905_01.jpg";
		 String content = FileUtil.readTxtFile("/Users/an/Desktop/graduation project/data/file/system/news/txt/01.txt", "utf-8");
		 System.out.println(content.length());
		 FinancialNews fn1 = new FinancialNews(title, origin, recordDate, pictureName, content);
		 pamsFinancialNewsService.insertFinancialNews(fn1);
		 logger.info(pamsFinancialNewsService.getFinancialNewsList());
	 }

}
