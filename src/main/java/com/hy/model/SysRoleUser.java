package com.hy.model;

import java.util.Date;

public class SysRoleUser {
    private Integer id;

    private String creater;

    private Date created;

    private String modifier;

    private Date modified;

    private Integer rid;

    private String uid;

    private String loginid;

    private String lastname;

    public SysRoleUser() {
    }

    public SysRoleUser(String creater, String modifier, Integer rid, String uid, String loginid, String lastname) {
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
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