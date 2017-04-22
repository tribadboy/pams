package com.nju.pams.model.constant;

/**
 * web模块的路径常量
 * @author yangyueyang
 */
public final class PathConstant {
	
	//web路径
	final public static String WEB = "/web/";
	
	//web下不需登录可访问的接口
	final public static String WEB_ANON = "/web/anon/";
	//web下需要登录和对应权限才能访问的接口
	final public static String WEB_AUTHC = "/web/authc/";
	//web下生成验证码的接口
	final public static String WEB_CODE = "/web/code/";
	//web路径用户相关功能的接口
	final public static String WEB_USER = "/web/user/";
	//web路径下载接口
	final public static String WEB_DOWNLOAD = "/web/download/";
	//金融数据更新接口
	final public static String WEB_FINANCE = "/web/finance/";
	
	//登录后的主页分成几个导航栏
	
	//home bar
	final public static String WEB_AUTHC_HOME_SYSTEM = "/web/authc/home/system/";
	final public static String WEB_AUTHC_HOME_ACCOUNT = "/web/authc/home/account/";
	final public static String WEB_AUTHC_HOME_ABOUT = "/web/authc/home/about/";
	
	//consumption bar
	final public static String WEB_AUTHC_CONSUMPTION_RECORD = "/web/authc/consumption/record/";
	final public static String WEB_AUTHC_CONSUMPTION_EXCEL = "/web/authc/consumption/excel/";
	
	//asset bar
	final public static String WEB_AUTHC_ASSET_GENERAL_ASSET = "/web/authc/asset/generalAsset/";
	final public static String WEB_AUTHC_ASSET_FIXED_ASSET = "/web/authc/asset/fixedAsset/";
	final public static String WEB_AUTHC_ASSET_REGULAR_INCOME = "/web/authc/asset/regularIncome/";
	final public static String WEB_AUTHC_ASSET_DEPOSIT = "/web/authc/asset/deposit/";
	final public static String WEB_AUTHC_ASSET_LOAN = "/web/authc/asset/loan/";
	
	//finance bar
	
	
	//user bar
	final public static String WEB_AUTHC_USER_ACCOUNT = "/web/authc/user/account/";

}
