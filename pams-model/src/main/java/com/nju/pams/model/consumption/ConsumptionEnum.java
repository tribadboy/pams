package com.nju.pams.model.consumption;

/**
 * @author yangyueyang <br>
 * date 2017-03-27
 */
public enum ConsumptionEnum {
	
    MealCost(1, "饮食消费"),
    ChotheCost(2, "服装消费"),
    HouseCost(3, "住房消费"),
    TrafficCost(4, "交通消费"),
    PhoneCost(5, "电话消费"),
    Commodity(6, "日用品消费"),
    BookCost(7, "书籍消费"),
    TravelCost(8, "旅行消费"),
    LivingCost(9, "生活消费(水电煤)"),
    OtherCost(10, "其他消费");

    private final int code;
    private final String msg;

    ConsumptionEnum(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public final int getCode() {
        return code;
    }

    public final String getMsg() {
        return msg;
    }
    
    //根据code值返回对应的消费分类名称
    public static String getMsgFromInt(int code) {
    	for(ConsumptionEnum c : ConsumptionEnum.values()) {
    		if(code == c.getCode()) {
    			return c.getMsg();
    		}
    	}
    	return "";
    }
}