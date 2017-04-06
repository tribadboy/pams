package com.nju.pams.util;

import org.joda.time.LocalDate;

import com.nju.pams.util.constant.TimeRange;
import com.nju.pams.util.constant.TimeRange.TimeRangeEnum;

public class TimeRangeUtil {
	
	//返回以某一天为源头，计算该对应阶段的起始日期和结束日期
	public static TimeRange getTimeRange(TimeRangeEnum timeEnum, LocalDate sourceDate) {
	    if(null == sourceDate) {
	    	return null;
	    }
	    LocalDate startDate = null;
	    LocalDate endDate = null;
	    switch(timeEnum) {
			case CURRENT_YEAR:	{
				startDate = sourceDate.withDayOfYear(1);
				return new TimeRange(timeEnum,startDate,sourceDate);
			}
			case LAST_WHOLE_QUARTER_1:				
			case LAST_WHOLE_QUARTER_2:
			case LAST_WHOLE_QUARTER_3:
			case LAST_WHOLE_QUARTER_4:{
				LocalDate curDate = sourceDate.plusDays(1);
				int curQuarter = (curDate.getMonthOfYear()-1) / 3;	//0--3,本季度
				LocalDate firstDateInQuarter = new LocalDate(curDate.getYear(),curQuarter * 3 + 1,1);
				int minusMonth = timeEnum.getIndex() % 100 * 3;
				startDate = firstDateInQuarter.minusMonths(minusMonth);
				endDate = startDate.plusMonths(3).minusDays(1);
				return new TimeRange(timeEnum,startDate,endDate);
			}			
			case LAST_WHOLE_YEAR_1:
			case LAST_WHOLE_YEAR_2:
			case LAST_WHOLE_YEAR_3:	{
				int yearNum = timeEnum.getIndex() % 100;
				int yearTime = sourceDate.plusDays(1).minusYears(yearNum).getYear();
				startDate = new LocalDate(yearTime,1,1);
				endDate = new LocalDate(yearTime,12,31);
				return new TimeRange(timeEnum,startDate,endDate);
			}
			case LAST_WHOLE_MONTH_1:
			case LAST_WHOLE_MONTH_2:
			case LAST_WHOLE_MONTH_3: {
				int monthNum = timeEnum.getIndex() % 100;
				int monthTime = sourceDate.plusDays(1).minusMonths(monthNum).getMonthOfYear();
				int yearTime = sourceDate.plusDays(1).minusMonths(monthNum).getYear();
				startDate = new LocalDate(yearTime, monthTime, 1);
				endDate = startDate.plusMonths(1).minusDays(1);
				return new TimeRange(timeEnum,startDate,endDate);
			}
			case TODAY_AGO_1_WEEK: {
				startDate = sourceDate.plusDays(1).minusWeeks(1);
				return new TimeRange(timeEnum,startDate,sourceDate);
			}
			case TODAY_AGO_1_MONTH:					
			case TODAY_AGO_3_MONTH:
			case TODAY_AGO_6_MONTH:	{
				int monthNum = timeEnum.getIndex() % 100;
				startDate = sourceDate.plusDays(1).minusMonths(monthNum);
				return new TimeRange(timeEnum,startDate,sourceDate);
			}
			case TODAY_AGO_1_YEAR:				
			case TODAY_AGO_2_YEAR: {
				int yearNum = timeEnum.getIndex() % 100;
				startDate = sourceDate.plusDays(1).minusYears(yearNum);
				return new TimeRange(timeEnum,startDate,sourceDate);
			}
			default:								return null;	
	    }
	} 
	
}
