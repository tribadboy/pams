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
}
