package com.nju.pams.util.test;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import com.nju.pams.util.DateUtil;

/**
 * @author yueyang.yang
 * date : 2017-04-05
 */
public class DateUtilTest {
	
	@Test
	public void testGetDaysInMonth() {
		Assert.assertEquals(30, DateUtil.getDaysInMonth(2017,4));
		Assert.assertEquals(31, DateUtil.getDaysInMonth(2017,10));
		Assert.assertEquals(29, DateUtil.getDaysInMonth(2016,2));
		Assert.assertEquals(28, DateUtil.getDaysInMonth(2017,2));
		Assert.assertEquals(30, DateUtil.getDaysInMonth("2017-04"));
		Assert.assertEquals(31, DateUtil.getDaysInMonth("2017-10"));
		Assert.assertEquals(29, DateUtil.getDaysInMonth("2016-02"));
		Assert.assertEquals(28, DateUtil.getDaysInMonth("2017-02"));
	}
	
	@Test
	public void testGetCurrentTime() {
		System.out.println(DateUtil.getCurrentTime(DateUtil.FormatString));
		System.out.println(DateUtil.getCurrentTime(DateUtil.FormatString2));
	}
	
	@Test
	public void testGetMillionSecondsFromDate() {
		System.out.println(DateUtil.getMillionSecondsFromDate("2011-04-14"));
		System.out.println(LocalDate.parse("2016-01-05").getYear());
		System.out.println(LocalDate.parse("2016-01-05").getMonthOfYear());
		System.out.println(LocalDate.parse("2016-01-05").getDayOfMonth());
	}
}
