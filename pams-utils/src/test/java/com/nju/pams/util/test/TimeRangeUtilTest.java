package com.nju.pams.util.test;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.nju.pams.util.TimeRangeUtil;
import com.nju.pams.util.constant.TimeRange.TimeRangeEnum;

/**
 * @author yueyang.yang
 * date : 2017-04-05
 */
public class TimeRangeUtilTest {
	
	@Test
	public void testGetTimeRange() {
		LocalDate d1 = new LocalDate("2017-02-24");
		for(TimeRangeEnum timeEnum : TimeRangeEnum.values()) {
			System.out.println(TimeRangeUtil.getTimeRange(timeEnum, d1));
		}	
	}
	
	@Test
	public void testGetSomedayPlusDays() {
		System.out.println(TimeRangeUtil.getSomedayPlusDays("2017-08-20", 20));
		System.out.println(TimeRangeUtil.getSomedayPlusDays("2017-04-12", 30));
		System.out.println(TimeRangeUtil.getSomedayPlusDays("2017-02-01", 15));
		System.out.println(TimeRangeUtil.getSomedayPlusDays("2017-03-25", 2));
	}
	
	@Test
	public void testGetPeriodDaysBetweenTwoDate() {
		System.out.println(TimeRangeUtil.getPeriodDaysBetweenTwoDate("2017-08-20", "2017-09-09"));
		System.out.println(TimeRangeUtil.getPeriodDaysBetweenTwoDate("2017-04-12", "2017-05-12"));
		System.out.println(TimeRangeUtil.getPeriodDaysBetweenTwoDate("2017-02-01", "2017-02-16"));
		System.out.println(TimeRangeUtil.getPeriodDaysBetweenTwoDate("2017-03-25", "2017-03-27"));
		System.out.println(TimeRangeUtil.getPeriodDaysBetweenTwoDate("2017-03-27", "2017-03-27"));
		
	}
	
}
