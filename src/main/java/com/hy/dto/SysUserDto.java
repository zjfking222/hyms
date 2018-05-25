package com.hy.dto;

public class SysUserDto {

    private int id;
    private String loginid;
    private String lastname;

    public SysUserDto(int id, String loginid, String lastname) {
        this.id = id;
        this.loginid = loginid;
        this.lastname = lastname;
    }

    public SysUserDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
