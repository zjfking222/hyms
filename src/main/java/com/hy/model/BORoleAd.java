package com.hy.model;

import java.util.Date;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/13 9:20
 * @Description:角色、AD员工号关系表
 */
public class BORoleAd {
    private int id;
    private int rid;
    private String empnum;
    private String empname;
    private String creater;
    private Date created;
    private String modifier;
    private Date modified;

    public BORoleAd(){

    }

    public BORoleAd(int rid, String empnum, String empname, String creater, String modifier) {
        this.rid = rid;
        this.empnum = empnum;
        this.empname = empname;
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

    public String getEmpnum() {
        return empnum;
    }

    public void setEmpnum(String empnum) {
        this.empnum = empnum;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
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
