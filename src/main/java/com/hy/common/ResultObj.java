package com.hy.common;

import com.hy.enums.ResultCode;

public class ResultObj<T> {
    private int code;
    private T data;
    private String msg;

    private ResultObj() {
        this.code = 0;
        this.msg = "成功";
    }
    private ResultObj(T data) {
        this.code = 0;
        this.msg = "成功";
        this.data = data;
    }

    private ResultObj(ResultCode cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    private ResultObj(ResultCode cm, String msg) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg() + "--" + msg;
    }

    private ResultObj(ResultCode cm, String msg, T data) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg() + "--" + msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    /**
     * 成功，不需要传入参数
     *
     * @return ResultObj
     */
    @SuppressWarnings("unchecked")
    public static ResultObj success() {
        return  new ResultObj();
    }

    /**
     * 成功时候的调用
     *
     * @return ResultObj
     */
    public static <T> ResultObj<T> success(T data) {
        return new ResultObj<T>(data);
    }

    /**
     * 失败时候的调用
     *
     * @return ResultObj
     */
    public static <T> ResultObj<T> error(ResultCode cm) {
        return new ResultObj<T>(cm);
    }

    /**
     * 失败时候的调用,扩展消息参数
     *
     * @param cm  ResultCode：结果枚举对象
     * @param msg 结果描述
     * @return ResultObj
     */
    public static <T> ResultObj<T> error(ResultCode cm, String msg) {
        return new ResultObj<T>(cm, msg);
    }

    /**
     * 失败时候的调用,扩展消息参数
     *
     * @param cm  ResultCode：结果枚举对象
     * @param msg 结果描述
     * @return ResultObj
     */
    public static <T> ResultObj<T> error(ResultCode cm, String msg, T data) {
        return new ResultObj<T>(cm, msg, data);
    }

}
