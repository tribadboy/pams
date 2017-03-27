package com.nju.pams.util.constant;

/**
 * @author yangyueyang <br>
 * date 2017-03-18
 */
public enum ResultEnum {
	
	/* 作为服务端返回json数据的结果
	 * enum不提供setter方法
	 */
	
    Success(0, "成功"),
    
    //参数校验错误 1--9
    NullParameter(1, "参数存在空值"),
    
    //验证码相关错误 11--19
    ErrorVerificationCode(11, "验证码输入错误"),
    
    //用户信息相关错误 21--29
    DuplicateUsername(21, "该用户名已经存在"),
    UsernameNotExist(22, "该用户名找不到对应的用户信息"),
    
    UnknownError(99, "未知错误");

    private final int code;
    private final String msg;

    ResultEnum(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }

    public final int getCode() {
        return code;
    }

    public final String getMsg() {
        return msg;
    }
}