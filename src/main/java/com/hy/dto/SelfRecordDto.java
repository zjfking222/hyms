package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/10 10:13
 * @Description:打卡记录数据dto
 */
public class SelfRecordDto {

    //员工号
    private String pernr;
    //当天最早打卡时间
    private String earliestTime;
    //地区
    private String ecity;
    //地区别名
    private String ealias;
    //当天最晚打卡时间
    private String latestTime;
    //地区
    private String lcity;
    //地区别名
    private String lalias;
    //原始记录条数
    private Integer recnum;
    //日期
    private String date;

    public String getPernr() {
        return pernr;
    }

    public void setPernr(String pernr) {
        this.pernr = pernr;
    }

    public String getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(String earliestTime) {
        this.earliestTime = earliestTime;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public Integer getRecnum() {
        return recnum;
    }

    public void setRecnum(Integer recnum) {
        this.recnum = recnum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEcity() {
        return ecity;
    }

    public void setEcity(String ecity) {
        this.ecity = ecity;
    }

    public String getEalias() {
        return ealias;
    }

    public void setEalias(String ealias) {
        this.ealias = ealias;
    }

    public String getLcity() {
        return lcity;
    }

    public void setLcity(String lcity) {
        this.lcity = lcity;
    }

    public String getLalias() {
        return lalias;
    }

    public void setLalias(String lalias) {
        this.lalias = lalias;
    }
}
