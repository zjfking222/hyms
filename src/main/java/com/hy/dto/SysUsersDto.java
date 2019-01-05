package com.hy.dto;

public class SysUsersDto {
    private Integer id;

    private String name;

    private Boolean enable;

    private String oaloginid;

    private Integer oauserid;
    //员工号
    private String employeenumber;
    //密码
    private String password;

    public SysUsersDto(int id, String name, String employeenumber) {
        this.id = id;
        this.name = name;
        this.employeenumber = employeenumber;
    }

    public SysUsersDto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOaloginid() {
        return oaloginid;
    }

    public void setOaloginid(String oaloginid) {
        this.oaloginid = oaloginid;
    }

    public Integer getOauserid() {
        return oauserid;
    }

    public void setOauserid(Integer oauserid) {
        this.oauserid = oauserid;
    }

    public String getEmployeenumber() {
        return employeenumber;
    }

    public void setEmployeenumber(String employeenumber) {
        this.employeenumber = employeenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}



