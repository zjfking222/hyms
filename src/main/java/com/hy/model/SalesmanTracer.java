package com.hy.model;

import java.util.Date;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/21 14:42
 * @Description:业务员、跟单员关系表model
 */
public class SalesmanTracer {
    private int id;
    private String salesmannum;
    private String salesmanname;
    private String tracernum;
    private String tracername;
    private String creater;
    private Date created;
    private String modifier;
    private Date modified;

    public SalesmanTracer() {
    }

    public SalesmanTracer(String salesmannum, String salesmanname, String tracernum, String tracername, String creater, String modifier) {
        this.salesmannum = salesmannum;
        this.salesmanname = salesmanname;
        this.tracernum = tracernum;
        this.tracername = tracername;
        this.creater = creater;
        this.modifier = modifier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSalesmannum() {
        return salesmannum;
    }

    public void setSalesmannum(String salesmannum) {
        this.salesmannum = salesmannum;
    }

    public String getSalesmanname() {
        return salesmanname;
    }

    public void setSalesmanname(String salesmanname) {
        this.salesmanname = salesmanname;
    }

    public String getTracernum() {
        return tracernum;
    }

    public void setTracernum(String tracernum) {
        this.tracernum = tracernum;
    }

    public String getTracername() {
        return tracername;
    }

    public void setTracername(String tracername) {
        this.tracername = tracername;
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
