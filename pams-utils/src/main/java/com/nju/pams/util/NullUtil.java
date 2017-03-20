package com.nju.pams.util;

import java.math.BigDecimal;

/**
 * @author yangyueyang <br>
 * date: 2017-03-05
 */
public class NullUtil {

	/**
	 * 非null处理
	 * @param value
	 * @return
	 */
	public static Integer notNullProcess(Integer value) {
		return (null == value) ? 0 : value;
	}
	
	/**
	 * 非null处理
	 * @param value
	 * @return
	 */
	public static Double notNullProcess(Double value) {
		return (null == value) ? 0.0 : value;
	}
	
	/**
	 * 非null处理
	 * @param value
	 * @return
	 */
	public static BigDecimal notNullProcess(BigDecimal value) {
		return (null == value) ? BigDecimal.ZERO : value;
	}
	
	/**
	 * 非null处理
	 * @param value
	 * @return
	 */
	public static String notNullProcess(String value) {
		return (null == value) ? "" : value;
	}
	
	/**
	 * 检查是否为null或者0
	 * @param value
	 * @return
	 */
	public static boolean isNullOrZero(Integer value) {
		return (null == value || 0 == value) ? true : false;
	}
	
	/**
	 * 检查是否为null或者0
	 * @param value
	 * @return
	 */
	public static boolean isNullOrZero(Double value) {
		return (null == value || 0.0 == value) ? true : false;
	}
	
	/**
	 * 检查是否为null或者0
	 * @param value
	 * @return
	 */
	public static boolean isNullOrZero(BigDecimal value) {
		return (null == value || BigDecimal.ZERO.compareTo(value) == 0) ? true : false;
	}
}
