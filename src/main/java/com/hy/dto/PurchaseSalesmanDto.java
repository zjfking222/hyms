package com.hy.dto;

/**
 * @Auther: 沈超宇
 * @Date: 2019/1/22 10:24
 * @Description:
 */
public class PurchaseSalesmanDto {
    private int id;
    private String salesmannum;
    private String salesmanname;

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
}
