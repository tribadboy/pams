package com.nju.pams.model.constant;

/**
 * web模块的路径常量
 * @author yangyueyang
 */
public final class PathConstant {

	//通用的路径  -----------------------------------------------------------------------------------------
	
	
	//验证码相关的接口
	final public static String WEB_CODE = "/web/code/";
	
	//不需登录可访问的接口
	final public static String WEB_ANON = "/web/anon/";
	
	//需要登录和对应权限才能访问的接口
	final public static String WEB_AUTHC = "/web/authc/";
	
	
	
	//background 后台相关的页面 ---------------------------------------------------------------------------
	
	//system-bar
	final public static String WEB_AUTHC_SYSTEM_HOME = "/web/authc/system/home/";
	final public static String WEB_AUTHC_SYSTEM_NEWS = "/web/authc/system/news/";
	final public static String WEB_AUTHC_SYSTEM_INFORM = "/web/authc/system/inform/";
	final public static String WEB_AUTHC_SYSTEM_NOTICE = "/web/authc/system/notice/";
	
	//user-bar
	final public static String WEB_AUTHC_USER_DATA = "/web/authc/user/data/";
	final public static String WEB_AUTHC_USER_FEEDBACK = "/web/authc/user/feedback/";
	
	//finance-bar
	final public static String WEB_AUTHC_FINANCE_STOCK_INFO = "/web/authc/finance/stockInfo/";
	
	
	
	
	//web 前台的相关页面 ----------------------------------------------------------------------------------
	
	//web路径用户注册的接口
	final public static String WEB_USER = "/web/user/";
	
	//web路径下载接口
	final public static String WEB_DOWNLOAD = "/web/download/";
	
	//登录后的主页分成几个导航栏
	
	//home bar
	final public static String WEB_AUTHC_HOME_SYSTEM = "/web/authc/home/system/";
	final public static String WEB_AUTHC_HOME_ACCOUNT = "/web/authc/home/account/";
	final public static String WEB_AUTHC_HOME_ABOUT = "/web/authc/home/about/";
	
	//consumption bar
	final public static String WEB_AUTHC_CONSUMPTION_RECORD = "/web/authc/consumption/record/";
	final public static String WEB_AUTHC_CONSUMPTION_EXCEL = "/web/authc/consumption/excel/";
	final public static String WEB_AUTHC_CONSUMPTION_DATA = "/web/authc/consumption/data/";
	
	//asset bar
	final public static String WEB_AUTHC_ASSET_GENERAL_ASSET = "/web/authc/asset/generalAsset/";
	final public static String WEB_AUTHC_ASSET_FIXED_ASSET = "/web/authc/asset/fixedAsset/";
	final public static String WEB_AUTHC_ASSET_REGULAR_INCOME = "/web/authc/asset/regularIncome/";
	final public static String WEB_AUTHC_ASSET_DEPOSIT = "/web/authc/asset/deposit/";
	final public static String WEB_AUTHC_ASSET_LOAN = "/web/authc/asset/loan/";
	
	//finance bar
	final public static String WEB_AUTHC_FINANCE_STOCK_DATA = "/web/authc/finance/stockData/";
	final public static String WEB_AUTHC_FINANCE_POSITION = "/web/authc/finance/position";
	
	//user bar
	final public static String WEB_AUTHC_USER_ACCOUNT = "/web/authc/user/account/";
	
	
	

}
