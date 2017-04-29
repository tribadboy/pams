package com.nju.pams.model.constant;

/**
 * redis cache 中的常量
 * @author yangyueyang <br>
 * date 2017-03-12
 */
public final class RedisCacheConstant {
	
	//redis cache 需要在redis-conf.xml文件中注册
	//redis cache 的 key 值后面会跟 #x.id 的参数占位符，拼接字符串时需要将前者包进单引号中,后面接 + 符号
	
	//测试使用的redis   -----------------------------------------------------------------------------------
		
	final public static String CACHE_USER = "cache_user";		
	final public static String CACHE_USER_KEY = "'cache_user_key_'+";
	
	
	//项目中使用的redis   ----------------------------------------------------------------------------------
	
	final public static String CACHE_STOCK = "cache_stock";
	final public static String CACHE_STOCK_KEY = "'cache_stock_key_'";
}
