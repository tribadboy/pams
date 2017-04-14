package com.nju.pams.model.constant;

public final class DatabaseConstant {
	
	//测试使用的表名称
	final public static String T_USER = "user_t";
	

	
	//正式使用的数据库与表格 -----------------------------------------------------------------
	
	//数据库
	final public static String DB_NAME = "pams_db";
	
	//用户-角色-权限 相关表格
	final public static String T_PAMS_USER = DB_NAME + "." + "t_pams_user";
	final public static String T_PAMS_ROLE = DB_NAME + "." + "t_pams_role";
	final public static String T_PAMS_PERMISSION = DB_NAME + "." + "t_pams_permission";
	final public static String T_PAMS_USER_ROLE = DB_NAME + "." + "t_pams_user_role";
	final public static String T_PAMS_ROLE_PERMISSION = DB_NAME + "." + "t_pams_role_permission";
	
	//消费账目相关表格
	final public static String T_PAMS_ACCOUNT = DB_NAME + "." + "t_pams_account";
	final public static String T_PAMS_ACCOUNT_MONTH = DB_NAME + "." + "t_pams_account_month";
	
	//资产管理相关表格
	final public static String T_PAMS_FIXED_ASSET = DB_NAME + "." + "t_pams_fixed_asset";
	final public static String T_PAMS_REGULAR_INCOME = DB_NAME + "." + "t_pams_regular_income";
	final public static String T_PAMS_DEPOSIT_RECORD = DB_NAME + "." + "t_pams_deposit_record";
	final public static String T_PAMS_DEPOSIT_CHANGE = DB_NAME + "." + "t_pams_deposit_change";
	final public static String T_PAMS_LOAN_RECORD = DB_NAME + "." + "t_pams_loan_record";
	final public static String T_PAMS_LOAN_CHANGE = DB_NAME + "." + "t_pams_loan_change";
	
	//社交相关的表格
	final public static String T_PAMS_FRIEND = DB_NAME + "." + "t_pams_friend";
	final public static String T_PAMS_FRIEND_REQUEST = DB_NAME + "." + "t_pams_friend_request";
	final public static String T_PAMS_FRIEND_LETTER = DB_NAME + "." + "t_pams_friend_letter";
	
	//金融相关的表格
	final public static String T_PAMS_STOCK = DB_NAME + "." + "t_pams_stock";
	final public static String T_PAMS_STOCK_HISTORY = DB_NAME + "." + "t_pams_stock_history";
	
}
