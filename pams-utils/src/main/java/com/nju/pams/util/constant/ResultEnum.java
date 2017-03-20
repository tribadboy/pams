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
    NullParameter(1, "参数存在空值"),
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