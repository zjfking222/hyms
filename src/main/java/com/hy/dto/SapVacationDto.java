package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/8 8:16
 * @Description:从sap接收年休假调休信息
 */
public class SapVacationDto {

    //员工号
    private String pernr;
    //定额类型
    private String zktart;
    //可休时数
    private String zkxss;
    //已休时数
    private String zyxss;
    //剩余时数
    private String zsyss;

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getZktart() {
        return zktart;
    }

    public void setZktart(String zktart) {
        this.zktart = zktart;
    }

    public String getZkxss() {
        return zkxss;
    }

    public void setZkxss(String zkxss) {
        this.zkxss = zkxss;
    }

    public String getZyxss() {
        return zyxss;
    }

    public void setZyxss(String zyxss) {
        this.zyxss = zyxss;
    }

    public String getZsyss() {
        return zsyss;
    }

    public void setZsyss(String zsyss) {
        this.zsyss = zsyss;
    }
}
