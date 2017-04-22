package com.nju.pams.util;

/**
 * @author yangyueyang <br>
 * date: 2017-04-20
 */
public class EmptyUtil {
	
	public static final String FormatStr = "--";

	
	public static String notEmtpyProcess(String value) {
		return (null == value || "".equals(value)) ? FormatStr : value;
	}
	
	public static String notEmtpyProcess(String value, String template) {
		return (null == value || "".equals(value)) ? template : value;
	}
	
	
}
