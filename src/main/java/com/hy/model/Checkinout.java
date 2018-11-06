package com.hy.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/3 9:38
 * @Description:原始刷卡记录model
 */
public class Checkinout implements Serializable {

    private Integer id;
    //考勤时间
    private Date checktime;
    //考勤状态
    private String checktype;
    //验证方式
    private Integer verifycode;
    private String newtype;
    private Integer abnormiteid;
    private Integer schid;
    private Integer purpose;
    private String workcode;
    private String reserved;
    //设备序列号
    private String sn;
    //用户表（userinfo）中的主键id
    private Integer userid;
    //标示是否使用多种验证方式。1表示是，0表示不是。
    private Integer autoexporttag;
    //地区
    private String city;
    //地区别名
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getChecktime() {
        return checktime;
    }

    public void setChecktime(Date checktime) {
        this.checktime = checktime;
    }

    public String getChecktype() {
        return checktype;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }

    public Integer getVerifycode() {
        return verifycode;
    }

    public void setVerifycode(Integer verifycode) {
        this.verifycode = verifycode;
    }

    public String getNewtype() {
        return newtype;
    }

    public void setNewtype(String newtype) {
        this.newtype = newtype;
    }

    public Integer getAbnormiteid() {
        return abnormiteid;
    }

    public void setAbnormiteid(Integer abnormiteid) {
        this.abnormiteid = abnormiteid;
    }

    public Integer getSchid() {
        return schid;
    }

    public void setSchid(Integer schid) {
        this.schid = schid;
    }

    public Integer getPurpose() {
        return purpose;
    }

    public void setPurpose(Integer purpose) {
        this.purpose = purpose;
    }

    public String getWorkcode() {
        return workcode;
    }

    public void setWorkcode(String workcode) {
        this.workcode = workcode;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAutoexporttag() {
        return autoexporttag;
    }

    public void setAutoexporttag(Integer autoexporttag) {
        this.autoexporttag = autoexporttag;
    }

}
