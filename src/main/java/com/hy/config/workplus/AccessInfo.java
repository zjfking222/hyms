package com.hy.config.workplus;

import java.io.Serializable;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/28 17:00
 * @Description:
 */
public class AccessInfo implements Serializable {

    //应用的身份凭证
    private String access_token;
    //更新身份凭证
    private String refresh_token;
    //access_token的生效时间
    private Long issued_time;
    //access_token的过期时间
    private Long expire_time;
    //当前登录用户的标识
    private String client_id;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Long getIssued_time() {
        return issued_time;
    }

    public void setIssued_time(Long issued_time) {
        this.issued_time = issued_time;
    }

    public Long getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Long expire_time) {
        this.expire_time = expire_time;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
