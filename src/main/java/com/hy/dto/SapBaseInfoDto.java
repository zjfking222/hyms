package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/7 19:49
 * @Description:从sap接口接收员工基本信息
 */
public class SapBaseInfoDto {

    //员工号
    private String pernr;
    //姓名
    private String name;
    //司龄
    private String zsl;
    //出生日期
    private String birthdate;
    //入职日期
    private String hiredate;
    //籍贯
    private String gbort;
    //部门
    private String zbm;
    //职位
    private String zgw;
    //手机号码
    private String zphone;
    //身份证号
    private String zid;

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZsl() {
        return zsl;
    }

    public void setZsl(String zsl) {
        this.zsl = zsl;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public String getGbort() {
        return gbort;
    }

    public void setGbort(String gbort) {
        this.gbort = gbort;
    }

    public String getZbm() {
        return zbm;
    }

    public void setZbm(String zbm) {
        this.zbm = zbm;
    }

    public String getZgw() {
        return zgw;
    }

    public void setZgw(String zgw) {
        this.zgw = zgw;
    }

    public String getZphone() {
        return zphone;
    }

    public void setZphone(String zphone) {
        this.zphone = zphone;
    }

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }
}
