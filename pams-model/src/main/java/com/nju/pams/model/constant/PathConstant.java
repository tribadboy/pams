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
	//web下面用户相关功能的接口
	final public static String WEB_USER = "/web/user/";

}