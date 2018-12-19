package com.hy.model;

import java.util.Date;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/13 9:18
 * @Description:角色、BO账号关系表
 */
public class BORoleAccount {
    private int id;
    private int rid;
    private String accountid;
    private String creater;
    private Date created;
    private String modifier;
    private Date modified;

    public BORoleAccount(){
    }

    public BORoleAccount(int rid, String accountid, String creater, String modifier) {
        this.rid = rid;
        this.accountid = accountid;
        this.creater = creater;
        this.modifier = modifier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
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
}
