package com.nju.pams.model.constant;

public enum ExcelEnum {
	TestFile(			1, "test_excel", 			"测试文档"),
	
	//关于消费账目有关的excel文档
	AccountDayFile(		11,	"account_day_excel",	"每日消费报表"),
	AccountMonthFile(	12,	"account_month_excel",	"某月的消费报表"),
	AccountYearFile(	13,	"account_year_excel",	"某年的消费报表"),
	
	
	
	Unknown(			99, "", 					"未知参数");
	
	private int code;
	private String model;
	private String message;
	
	ExcelEnum(final int code, final String model, final String message) {
		this.code = code;
		this.model = model;
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	public String getModel() {
		return model;
	}
	public String getMessage() {
		return message;
	}
	
	public static ExcelEnum getExcelEnumFromModel(String model) {
		for(ExcelEnum e : ExcelEnum.values()) {
			if(e.getModel().equals(model)) {
				return e;
			}
		}
		return Unknown;
	}
	
	public static ExcelEnum getExcelEnumFromCode(Integer code) {
		for(ExcelEnum e : ExcelEnum.values()) {
			if(code == e.getCode()) {
				return e;
			}
		}
		return Unknown;
	}
	
	public static ExcelEnum getExcelEnumFromCode(String code) {
		for(ExcelEnum e : ExcelEnum.values()) {
			if(String.valueOf(e.getCode()).equals(code)) {
				return e;
			}
		}
		return Unknown;
	}
	
}
