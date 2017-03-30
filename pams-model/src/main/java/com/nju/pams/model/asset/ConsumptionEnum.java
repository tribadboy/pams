package com.nju.pams.model.asset;

/**
 * @author yangyueyang <br>
 * date 2017-03-27
 */
public enum ConsumptionEnum {
	
    MealCost(1,"饮食消费");

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
}