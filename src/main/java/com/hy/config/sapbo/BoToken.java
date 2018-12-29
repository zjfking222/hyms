package com.hy.config.sapbo;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 詹继锋
 * @Date: 2018/12/4 17:03
 * @Description: BO token认证对象
 */
@Component
public class BoToken implements Serializable {

    public  BoToken() {
    }

    public  BoToken(String token,Date issued,Date expire) {
        this.token=token;
        this.issued=issued;
        this.expire=expire;
    }

    /*token*/
    private String token;
    /*认证时间*/
    private Date issued;
    /*到期时间*/
    private Date expire;

    private int num;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public Date getIssued() {
        return issued;
    }

    public void setIssued(Date issued) {
        this.issued = issued;
    }

}
