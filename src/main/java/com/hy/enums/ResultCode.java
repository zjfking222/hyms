package com.hy.enums;

public enum ResultCode {
    ERROR_UNKNOWN(2001, "未知异常"),
    ERROR_INVALID_PARAMETER(2002, "方法参数错误"),
    ERROR_FUNCTION_NO_ACCESS(2003, "对此方法无访问权限");

    private String msg;
    private int code;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return this.msg + ":" + this.code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }
}