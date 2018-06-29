package com.hy.model;

public class CrmBusinesstypeUser {
    private int id;
    private int btid;
    private int uid;
    private int creater;
    private String created;
    private int modifier;
    private String modified;
    private String loginid;
    private String lastname;

    public CrmBusinesstypeUser() {
    }

    public CrmBusinesstypeUser(int btid, int uid, int creater, int modifier, String loginid, String lastname) {
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getCreater() {
        return creater;
    }

    public void setCreater(int creater) {
        this.creater = creater;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
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
