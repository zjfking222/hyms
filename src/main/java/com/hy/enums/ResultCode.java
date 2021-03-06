package com.hy.enums;

public enum ResultCode {
    ERROR_USER_UNEXISTS(1001, "账号不存在"),
    ERROR_USER_UNMATCH(1002, "帐号密码不匹配"),
    ERROR_USER_VERIFICATIONCODE(1003, "验证码错误"),
    ERROR_USER_PASSWORD_AGREE(1004, "密码不一致"),
    ERROR_USER_AND_PHONE(1005, "员工号与手机号不匹配，请联系人资修改"),
    ERROR_USER_VERCODE_CREATE(1006, "验证码生成失败"),
    ERROR_UNKNOWN(2001, "未知异常"),
    ERROR_INVALID_PARAMETER(2002, "方法参数错误"),
    ERROR_FUNCTION_NO_ACCESS(2003, "无访问权限"),
    ERROR_NO_RESOURCE(2004, "未找到任何资源"),
    ERROR_UPLOAD_FAILED(2006, "文件上传失败"),
    ERROR_DATA_FAILED(2007,"文件数据为空或数据已存在"),
    ERROR_DATA_REPEAT(2008, "数据重复，无法保存"),
    ERROR_ADD_FAILED(2100, "添加失败"),
    ERROR_UPDATE_FAILED(2101, "更新失败"),
    ERROR_DELETE_FAILED(2102, "删除失败"),
    ERROR_SEARCH_FAILED(2103, "查询失败"),
    ERROR_DELETE_FOREIGN(2110, "需要先删除该信息的关联信息"),
    ERROR_DELETE_SUBNODE(2014, "存在子节点，无法删除"),
    ERROR_OPERATE_FAILD(3001, "操作失败");

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
