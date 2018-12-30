package com.hy.dto;

public class SysUserDto {

    private String id;
    private String loginid;
    private String lastname;

    public SysUserDto(String id, String loginid, String lastname) {
        this.id = id;
        this.loginid = loginid;
        this.lastname = lastname;
    }

    public SysUserDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
