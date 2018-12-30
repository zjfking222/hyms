package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/8 8:54
 * @Description:从sap接收排班表信息
 */
public class SapSchedulingDto {

    //员工号
    private String pernr;
    //日期
    private String zrq;
    //班次
    private String tprog;
    //班次描述
    private String ztext;
    //班次开始时间
    private String begda;
    //班次结束时间
    private String endda;
    //计划工作时间
    private String stdaz;

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getZrq() {
        return zrq;
    }

    public void setZrq(String zrq) {
        this.zrq = zrq;
    }

    public String getTprog() {
        return tprog;
    }

    public void setTprog(String tprog) {
        this.tprog = tprog;
    }

    public String getZtext() {
        return ztext;
    }

    public void setZtext(String ztext) {
        this.ztext = ztext;
    }

    public String getBegda() {
        return begda;
    }

    public void setBegda(String begda) {
        this.begda = begda;
    }

    public String getEndda() {
        return endda;
    }

    public void setEndda(String endda) {
        this.endda = endda;
    }

    public String getStdaz() {
        return stdaz;
    }

    public void setStdaz(String stdaz) {
        this.stdaz = stdaz;
    }
}
