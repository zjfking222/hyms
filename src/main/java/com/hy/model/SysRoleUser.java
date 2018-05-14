package com.hy.model;

import java.util.Date;

public class SysRoleUser {
    private Integer id;

    private Integer creater;

    private Date created;

    private Integer modifier;

    private Date modified;

    private Integer rid;

    private Integer uid;

    private String loginid;

    private String lastname;

    public SysRoleUser() {
    }

    public SysRoleUser(Integer creater, Integer modifier, Integer rid, Integer uid, String loginid, String lastname) {
        this.creater = creater;
        this.modifier = modifier;
        this.rid = rid;
        this.uid = uid;
        this.loginid = loginid;
        this.lastname = lastname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid == null ? null : loginid.trim();
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname == null ? null : lastname.trim();
    }
}