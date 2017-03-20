package com.nju.pams.util;

import com.nju.pams.util.constant.ResultEnum;

import net.sf.json.JSONObject;

public class ResultUtil {
	
	/**
	 * 处理正确的json返回值
	 * @param jsonObject
	 */
    public static void addSuccess(final JSONObject jsonObject) {
        processResult(jsonObject, ResultEnum.Success.getCode(), ResultEnum.Success.getMsg());
    }

    /**
     * 未知错误的json返回值
     * @param jsonObject
     */
    public static void addUnknownError(final JSONObject jsonObject) {
        processResult(jsonObject, ResultEnum.UnknownError.getCode(), ResultEnum.UnknownError.getMsg());
    }

    /**
     * 特定错误的json返回值
     * @param jsonObject
     * @param resultEnum
     */
    public static void addResult(final JSONObject jsonObject, final ResultEnum resultEnum) {
        if (null != resultEnum) {
            processResult(jsonObject, resultEnum.getCode(), resultEnum.getMsg());
        } else {
        	addUnknownError(jsonObject);
        }
    }
    
    /**
     * 将返回结果的信息组装进入json
     * @param jsonObj
     * @param resultCode
     * @param resultMsg
     */
    public static void processResult(final JSONObject jsonObject, final int resultCode, final String resultMsg) {
        if (null != jsonObject) {
            jsonObject.put("code", resultCode);
            jsonObject.put("msg", resultMsg);
        }
    }

}

