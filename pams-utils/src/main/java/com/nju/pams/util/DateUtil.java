package com.nju.pams.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final String FormatString = "yyyy-MM-dd";

	/**
	 * 判断某个月份的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysInMonth(int year, int month) {
		Calendar a = Calendar.getInstance();  
	    a.set(Calendar.YEAR, year);  
	    a.set(Calendar.MONTH, month - 1);  
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    return a.get(Calendar.DATE);  
	}
	
	/**
	 * 判断某个月份的天数
	 * @param month eg: 2017-04
	 * @return
	 */
	public static int getDaysInMonth(String month) {
		if(null == month || "".equals(month)) {
			return 0;
		}
		String firstDay = month.concat("-01");
		SimpleDateFormat sdf= new SimpleDateFormat(FormatString); 
		Date date = null;
		try {
			date = sdf.parse(firstDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar first = Calendar.getInstance();
		first.setTime(date);
		first.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
		return first.get(Calendar.DATE);
	}
	
	/**
	 * 返回某个时间段内的随机时间
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date getRandomDate(String startDate, String endDate) {  
        try {  
            SimpleDateFormat format = new SimpleDateFormat(FormatString);  
            Date start = format.parse(startDate);  
            Date end = format.parse(endDate); 
            if (start.getTime() >= end.getTime()) {  
                return null;  
            }  
            long date = random(start.getTime(), end.getTime());  
            return new Date(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
	
	/**
	 * 返回某个时间段内的随机时间的格式化字符串(yyyy-MM-dd)
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String getRandomDateString(String startDate, String endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(FormatString);
		return sdf.format(getRandomDate(startDate, endDate));
	}
  
    private static long random(long begin, long end) {  
        long rtn = begin + (long) (Math.random() * (end - begin));   
        if (rtn == begin || rtn == end) {  
            return random(begin, end);  
        }  
        return rtn;  
    }  
}
