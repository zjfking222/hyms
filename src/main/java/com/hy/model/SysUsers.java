package com.hy.model;


import java.util.Date;

public class SysUsers {
    private Integer id;

    private String name;

    private String password;

    private Boolean sex;

    private String mobile;

    private Boolean enable;

    private Short deptid;

    private Integer creater;

    private Date created;

    private Integer modifier;

    private Date modified;

    private String oaloginid;

    private Integer oauserid;

    public SysUsers(String name, Integer creater, Integer modifier, String oaloginid, Integer oauserid) {
        this.name = name;
        this.creater = creater;
        this.modifier = modifier;
        this.oaloginid = oaloginid;
        this.oauserid = oauserid;
    }

    public SysUsers() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getDeptid() {
        return deptid;
    }

    public void setDeptid(Short deptid) {
        this.deptid = deptid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }


    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }


}