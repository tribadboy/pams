package com.nju.pams.model.asset;

/**
 * @author yangyueyang <br>
 * date 2017-04-08
 */
public enum DepositTimeEnum {
	
    NoTime			(1,		0,			"活期：无时间限制"),
    ThreeMonth		(2,		90,			"定期：3个月(90天)"),
    SixMonth		(3,		180,		"定期：6个月(180天)"),
    OneYear			(4,		360,		"定期：1年(360天)"),
    ThreeYear		(5,		1080,		"定期：3年(1080天)"),
    FiveYear		(6,		1800,		"定期：5年(1800天)");

    private final int index;
    private final int daysCount;
    private final String msg;

    DepositTimeEnum(final int index, final int daysCount, final String msg) {
        this.index = index;
        this.daysCount = daysCount;
        this.msg = msg;
    }
    public final int getIndex() {
        return index;
    }
    public final int getDaysCount() {
    	return daysCount;
    }
    public final String getMsg() {
        return msg;
    }
    
    //根据index值返回对应的存款时间
    public static DepositTimeEnum getTimeFromIndex(int index) {
    	for(DepositTimeEnum type : DepositTimeEnum.values()) {
    		if(index == type.getIndex()) {
    			return type;
    		}
    	}
    	return null;
    }
}