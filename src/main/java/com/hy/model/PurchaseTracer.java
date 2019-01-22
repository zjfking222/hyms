package com.hy.model;

import java.util.Date;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/22 10:08
 * @Description:
 */
public class PurchaseTracer {
    private int id;
    private int sid;
    private String tracernum;
    private String tracername;
    private String creater;
    private Date created;
    private String modifier;
    private Date modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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
