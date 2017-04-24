package com.nju.pams.service.impl;

import org.apache.log4j.Logger;
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
	 //@Test
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
	 
	 //@Ignore
	 //@Test
	 public void financialNewsServiceTest2() {
		 String title = "股市早知道：影响下周市场重磅新闻汇总（04.23）";
		 String origin = " 网易财经";
		 String recordDate = "2017-04-23";
		 String pictureName = FinancialNews.NonePicture;
		 String content = FileUtil.readTxtFile("/Users/an/Desktop/graduation project/data/file/system/news/txt/02.txt", "utf-8");
		 System.out.println(content.length());
		 FinancialNews fn2 = new FinancialNews(title, origin, recordDate, pictureName, content);
		 pamsFinancialNewsService.insertFinancialNews(fn2);
		 logger.info(pamsFinancialNewsService.getFinancialNewsList());
	 }
	 
	 //@Ignore
	 //@Test
	 public void financialNewsServiceTest3() {
		 String title = "鑫根资本合伙人希望贾跃亭辞职？ 被断章取义";
		 String origin = "搜狐财经公众号";
		 String recordDate = "2017-04-22";
		 String pictureName = "1492968865910_02.jpeg";
		 String content = FileUtil.readTxtFile("/Users/an/Desktop/graduation project/data/file/system/news/txt/03.txt", "utf-8");
		 System.out.println(content.length());
		 FinancialNews fn3 = new FinancialNews(title, origin, recordDate, pictureName, content);
		 pamsFinancialNewsService.insertFinancialNews(fn3);
		 logger.info(pamsFinancialNewsService.getFinancialNewsList());
	 }
	 
	 //@Ignore
	 //@Test
	 public void financialNewsServiceTest4() {
		 String title = "近900只股创2638点以来新低 游资散户惨痛割肉雄安概念股";
		 String origin = "新浪综合 ";
		 String recordDate = "2017-04-24";
		 String pictureName = "1492968865915_03.jpg";
		 String content = FileUtil.readTxtFile("/Users/an/Desktop/graduation project/data/file/system/news/txt/04.txt", "utf-8");
		 System.out.println(content.length());
		 FinancialNews fn4 = new FinancialNews(title, origin, recordDate, pictureName, content);
		 pamsFinancialNewsService.insertFinancialNews(fn4);
		 logger.info(pamsFinancialNewsService.getFinancialNewsList());
	 }
	 
	 //@Ignore
	 //@Test
	 public void financialNewsServiceTest5() {
		 String title = "三只松鼠冲刺IPO:年营收44亿净利超2亿 章燎原做对了什么";
		 String origin = "天下网商";
		 String recordDate = "2017-04-24";
		 String pictureName = "1492968865920_04.jpg";
		 String content = FileUtil.readTxtFile("/Users/an/Desktop/graduation project/data/file/system/news/txt/05.txt", "utf-8");
		 System.out.println(content.length());
		 FinancialNews fn5 = new FinancialNews(title, origin, recordDate, pictureName, content);
		 pamsFinancialNewsService.insertFinancialNews(fn5);
		 logger.info(pamsFinancialNewsService.getFinancialNewsList());
	 }

}
