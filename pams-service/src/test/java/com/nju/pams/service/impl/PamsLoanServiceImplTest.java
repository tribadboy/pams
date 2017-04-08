package com.nju.pams.service.impl;

import java.math.BigDecimal;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nju.pams.biz.service.PamsLoanService;
import com.nju.pams.model.asset.LoanChange;
import com.nju.pams.model.asset.LoanRecord;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})  
public class PamsLoanServiceImplTest {
	
	 private static Logger logger = Logger.getLogger(PamsLoanServiceImplTest.class);  
	 
	 @Autowired
	 private PamsLoanService pamsLoanService;
	 
	 //@Ignore
	 @Test
	 public void loanServiceTest() {
		 
		 //创建一个贷款记录，并插入数据库
		 LoanRecord record = new LoanRecord(1, 0, 0, BigDecimal.valueOf(10000.00), 1, "record1");
		 pamsLoanService.makeLoanRecord(record, BigDecimal.valueOf(8000.00), "2017-02-02");
		 
		 //创建几条还款记录，并插入数据库
		 LoanChange c1 = new LoanChange(record.getLoanId(), 1, BigDecimal.valueOf(1000.00), "2017-03-01");
		 LoanChange c2 = new LoanChange(record.getLoanId(), 1, BigDecimal.valueOf(2000.00), "2017-03-02");
		 LoanChange c3 = new LoanChange(record.getLoanId(), 1, BigDecimal.valueOf(3000.00), "2017-03-03");
		 LoanChange c4 = new LoanChange(record.getLoanId(), 1, BigDecimal.valueOf(4000.00), "2017-03-04");
		 pamsLoanService.insertLoanChange(c1);
		 pamsLoanService.insertLoanChange(c2);
		 pamsLoanService.insertLoanChange(c3);
		 pamsLoanService.insertLoanChange(c4);
		 
		 //查询用户的贷款记录，以及该条贷款的还款记录
		 logger.info("用户的贷款记录： " + pamsLoanService.getValidLoanRecordsByUserId(1));
		 logger.info("该条贷款的所有还款记录： " + pamsLoanService.getLoanChangeListByLoanId(record.getLoanId()));
		 
		 //关闭贷款，再次查询用户的贷款记录
		 pamsLoanService.closeLoanRecord(record.getLoanId());
		 logger.info("再次查询用户的贷款记录： " + pamsLoanService.getValidLoanRecordsByUserId(1));
		 
		 //更新该条贷款记录,第三次查看贷款记录
		 record.setStatus(0);
		 record.setExceptRepayAmount(BigDecimal.valueOf(9000.00));
		 pamsLoanService.updateLoanRecord(record);
		 logger.info("第三次查询用户的贷款记录： " + pamsLoanService.getValidLoanRecordsByUserId(1));
		 
		 //删除一条还款记录
		 pamsLoanService.deleteLoanChange(c4.getChangeId());
		 logger.info("删除一条还款记录后，该条贷款的所有还款记录： " + pamsLoanService.getLoanChangeListByLoanId(record.getLoanId()));
		 
		 //删除整个贷款和还款的数据信息
		 pamsLoanService.deleteLoanRecordAndChange(record.getLoanId());
		 logger.info("第四次查询用户的贷款记录： " + pamsLoanService.getValidLoanRecordsByUserId(1));
		 logger.info("查询用户的还款记录： " + pamsLoanService.getLoanChangeListByLoanId(record.getLoanId()));
	 }
}
