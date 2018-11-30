package com.hy.config.shiro;

import java.io.Serializable;

public class ShiroUserInfo implements Serializable {

    private String loginid;
    private String name;
    private String email;
    private String phone;
    private String depid;
    private String depname;
    private String duty;
    private String password;

    public ShiroUserInfo() {
    }

    public ShiroUserInfo(String loginid, String name) {
        this(loginid, name, null);
    }


    public ShiroUserInfo(String loginid, String name, String password) {
        this(loginid, name, password, null, null, null);
    }

    public ShiroUserInfo(String loginid, String name, String password, String depid, String depname, String duty) {
        this(loginid, name, password, depid, depname, duty, null, null);
    }

    public ShiroUserInfo(String loginid, String name, String password, String depid, String depname, String duty, String email, String phone) {
        this.loginid = loginid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.depname = depname;
        this.depid = depid;
        this.duty = duty;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }

}
