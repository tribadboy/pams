package com.nju.pams.model.constant;

public final class DatabaseConstant {
	
	//测试使用的表名称
	final public static String T_USER = "user_t";
	
	//正式使用的数据库名称
	final public static String DB_NAME = "pams_db";
	
	//正式使用的表名称
	final public static String T_PAMS_USER = DB_NAME + "." + "t_pams_user";
	final public static String T_PAMS_ROLE = DB_NAME + "." + "t_pams_role";
	final public static String T_PAMS_PERMISSION = DB_NAME + "." + "t_pams_permission";
	final public static String T_PAMS_USER_ROLE = DB_NAME + "." + "t_pams_user_role";
	final public static String T_PAMS_ROLE_PERMISSION = DB_NAME + "." + "t_pams_role_permission";
	final public static String T_PAMS_ACCOUNT = DB_NAME + "." + "t_pams_account";
}
