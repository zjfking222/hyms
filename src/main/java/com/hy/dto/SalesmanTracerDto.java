package com.hy.dto;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/21 16:15
 * @Description:业务员、跟单员关系表DTO
 */
public class SalesmanTracerDto {
    private int id;
    private String salesmannum;
    private String salesmanname;
    private String tracernum;
    private String tracername;

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
}
