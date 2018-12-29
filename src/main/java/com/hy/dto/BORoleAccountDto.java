package com.hy.dto;

/**
 * @Author 沈超宇
 * @Description 角色、BO账号关系表DTO
 * @Date 2018/12/15 8:39
 **/
public class BORoleAccountDto {
    private int id;
    private int rid;
    private String accountid;

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
}
