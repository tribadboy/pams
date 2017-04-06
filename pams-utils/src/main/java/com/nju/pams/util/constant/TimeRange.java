package com.nju.pams.util.constant;

import org.joda.time.LocalDate;

public class TimeRange {
	TimeRangeEnum typeEnum;
	LocalDate startDate;
	LocalDate endDate;
	
	public TimeRangeEnum getTypeEnum() {
		return typeEnum;
	}
	public void setTypeEnum(TimeRangeEnum typeEnum) {
		this.typeEnum = typeEnum;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public TimeRange(TimeRangeEnum typeEnum, LocalDate startDate, LocalDate endDate) {
		this.typeEnum = typeEnum;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("{TimeRange [")
		.append("typeEnum=" + typeEnum.getDescription())
		.append(",startDate=" + startDate.toString("yyyy-MM-dd"))
		.append(",endDate=" + endDate.toString("yyyy-MM-dd"))			
		.append("]}");
		return strBuf.toString();
	}
	
	public static enum TimeRangeEnum {
		TODAY_AGO_1_WEEK		(101,		"todayAgo1Week",		"过去的一周"),
		TODAY_AGO_1_MONTH		(201, 		"todayAgo1Month",		"过去的一个月"),
		TODAY_AGO_3_MONTH		(203,		"todayAgo3Month",		"过去的三个月"),
		TODAY_AGO_6_MONTH		(206,		"todayAgo6Month",		"过去的六个月"),
		TODAY_AGO_1_YEAR		(301,		"todayAgo1year",		"过去的一年"),
		TODAY_AGO_2_YEAR		(302,		"todayAgo2Year",		"过去的两年"),
		CURRENT_YEAR			(401,		"currentYear",			"今年以来"),
		LAST_WHOLE_MONTH_1		(501,		"lastWholeMonth1",		"上个月"),
		LAST_WHOLE_MONTH_2		(502,		"lastWholeMonth2",		"上上个月"),
		LAST_WHOLE_MONTH_3		(503,		"lastWholeMonth3",		"上上上月"),
		LAST_WHOLE_QUARTER_1	(601, 		"lastWholeQuarter1",	"上一个完整季度"),
		LAST_WHOLE_QUARTER_2	(602, 		"lastWholeQuarter2",	"上上个完整季度"),
		LAST_WHOLE_QUARTER_3	(603, 		"lastWholeQuarter3",	"上上上完整季度"),
		LAST_WHOLE_QUARTER_4	(604,    	"lastWholeQuarter4",	"倒推的第4个完整季度"),
		LAST_WHOLE_YEAR_1		(701,    	"lastWholeYear1",		"去年"),
		LAST_WHOLE_YEAR_2		(702,    	"lastWholeYear2",		"前年"),
		LAST_WHOLE_YEAR_3		(703,    	"lastWholeYear3",		"大前年");
	
		private int index;
		private String name;
		private String description;
		private TimeRangeEnum (final int index, final String name, final String description) {
			this.index   = index;
			this.name    = name;
			this.description = description;
		}
		public final int getIndex() {
			return index;
		}
		public final String getName() {
			return name;
		}
		public final String getDescription() {
			return description;
		}
	}
}