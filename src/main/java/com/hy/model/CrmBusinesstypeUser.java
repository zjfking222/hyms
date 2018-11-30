package com.hy.model;

public class CrmBusinesstypeUser {
    private int id;
    private int btid;
    private String uid;
    private String creater;
    private String created;
    private String modifier;
    private String modified;
    private String loginid;
    private String lastname;

    public CrmBusinesstypeUser() {
    }

    public CrmBusinesstypeUser(int btid, String uid, String creater, String modifier, String loginid, String lastname) {
        this.btid = btid;
        this.uid = uid;
        this.creater = creater;
        this.modifier = modifier;
        this.loginid = loginid;
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBtid() {
        return btid;
    }

    public void setBtid(int btid) {
        this.btid = btid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
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
