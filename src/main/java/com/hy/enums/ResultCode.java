package com.hy.enums;

public enum ResultCode {
    ERROR_USER_UNEXISTS(1001, "账号不存在"),
    ERROR_USER_UNMATCH(1002, "帐号密码不匹配"),
    ERROR_UNKNOWN(2001, "未知异常"),
    ERROR_INVALID_PARAMETER(2002, "方法参数错误"),
    ERROR_FUNCTION_NO_ACCESS(2003, "对此方法无访问权限"),
    ERROR_NO_RESOURCE(2004, "未找到任何资源"),
    ERROR_UNMATCH(2005, "帐号密码不匹配");

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
