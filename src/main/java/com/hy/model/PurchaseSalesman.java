package com.hy.model;

import java.util.Date;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/22 10:07
 * @Description:业务员表model
 */
public class PurchaseSalesman {
    private int id;
    private String salesmannum;
    private String salesmanname;
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
