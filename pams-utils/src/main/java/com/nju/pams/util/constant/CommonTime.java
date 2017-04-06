package com.nju.pams.util.constant;

public class CommonTime {
	public static enum Month {
		January 	(1, 	"01",	"1月"),
		February	(2, 	"02",	"2月"),
		March		(3,		"03",	"3月"),
		April		(4,		"04",	"4月"),
		May			(5,		"05",	"5月"),
		June		(6,		"06",	"6月"),
		July		(7,		"07",	"7月"),
		August		(8,		"08",	"8月"),
		September	(9,		"09",	"9月"),
		October		(10,	"10",	"10月"),
		November	(11,	"11",	"11月"),
		December	(12,	"12",	"12月");
		
		private int index;
		private String name;
		private String message;
		private Month(int index, String name, String message) {
			this.index = index;
			this.name = name;
			this.message = message;
		}
		public int getIndex() {
			return index;
		}
		public String getName() {
			return name;
		}
		public String getMessage() {
			return message;
		}
	}
}
