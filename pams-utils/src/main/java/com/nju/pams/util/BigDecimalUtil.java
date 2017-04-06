package com.nju.pams.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
	
	public static final int FORMAT_SCALE = 2;			//		统一保留两位小数
	
	public static String generateFormatString(BigDecimal decimal) {
		if(null == decimal) {
			return "0.00";
		} else {
			decimal = decimal.setScale(FORMAT_SCALE, RoundingMode.HALF_UP);
			return decimal.toPlainString();
		}
	}
	
	public static BigDecimal generateFormatNumber(BigDecimal decimal) {
		if(null == decimal) {
			decimal = BigDecimal.valueOf(0.00);
		}
		decimal = decimal.setScale(FORMAT_SCALE, RoundingMode.HALF_UP);
		return decimal;
	}
}
