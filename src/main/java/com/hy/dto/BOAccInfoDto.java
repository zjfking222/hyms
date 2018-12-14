package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/8 10:49
 * @Description:BO账号与报表对应关系表（BO账号所拥有的报表）
 */
public class BOAccInfoDto {

    //主键
    private Integer id;
    //账号id
    private String accountid;
    //ad域员工号
    private String reportid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public String getReportid() {
        return reportid;
    }

    public void setReportid(String reportid) {
        this.reportid = reportid;
    }

}
