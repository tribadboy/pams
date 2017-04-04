package com.nju.pams.model.constant;

public enum ExcelEnum {
	TestFile(1, "test_excel", "测试文档"),
	
	
	
	Unknown(99, "", "未知参数");
	
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
	
}
