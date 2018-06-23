package com.xhgx.sdk.entity;

import java.io.Serializable;

/**
 * @ClassName Result
 * @Description 统一的返回信息
 * @author zohan(inlw@sina.com)
 * @date 2017-03-20 18:29
 * @vresion 1.0
 */
public class Result implements Serializable, Cloneable {

    /**成功标示*/
    private static  final String CODE_SUCCESS = "1";
    /**失败标示*/
    private static final String CODE_ERROR = "0";
    /**结果状态*/
    public String code;
    /**消息信息*/
    public String msg;
    /**返回的对象信息*/
    public Object data;

    public Result( String code,String msg) {
        this.msg = msg;
        this.code = code;
    }

    /**
     * 构建一个带有错误标示的对象
     *
     * @param msg
     * @return
     */
    public static Result buildError(String msg) {
        return new Result(CODE_ERROR, msg);
    }


    /**
     * 构建成功的信息
     * @param data
     * @return
     */
    public static Result buildSuccess(Object data) {
        return buildSuccess(null, data);
    }

    /**
     *构建成功的信息
     * @param msg
     * @param data
     * @return
     */
    public static Result buildSuccess(String msg, Object data) {
        Result result = new Result(CODE_SUCCESS, msg);
        result.setData(data);
        return result;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
