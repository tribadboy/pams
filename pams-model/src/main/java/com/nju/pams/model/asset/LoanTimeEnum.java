package com.nju.pams.model.asset;

/**
 * @author yangyueyang <br>
 * date 2017-04-08
 */
public enum LoanTimeEnum {
	
    ThreeMonth			(1,		3,			"3个月"),
    SixMonth			(2,		6,			"6个月"),
    OneYear				(3,		12,			"12个月"),
    TwoYear				(4,		24,			"24个月"),
    ThreeYear			(5,		36,			"36个月"),
    FourYear			(6,		48,			"48个月"),
    FiveYear			(7,		60,			"60个月");

    private final int index;
    private final int monthsCount;
    private final String msg;

    LoanTimeEnum(final int index, final int monthsCount, final String msg) {
        this.index = index;
        this.monthsCount = monthsCount;
        this.msg = msg;
    }
    public final int getIndex() {
        return index;
    }
    public final int getMonthsCount() {
    	return monthsCount;
    }
    public final String getMsg() {
        return msg;
    }
    
    //根据index值返回对应的存款时间
    public static LoanTimeEnum getTimeFromIndex(int index) {
    	for(LoanTimeEnum type : LoanTimeEnum.values()) {
    		if(index == type.getIndex()) {
    			return type;
    		}
    	}
    	return null;
    }
}