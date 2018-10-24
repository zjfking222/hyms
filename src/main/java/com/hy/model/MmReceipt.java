package com.hy.model;

import java.util.Date;

public class MmReceipt {

    private int id;
    private int mid;
    private int cid;
    private boolean driving;
    private boolean pickup;
    private Date arrivaldate;
    private String arrivalinfo;
    private String arrivalremark;
    private String arrivaltype;
    private int arrivalfollower;
    private boolean sendoff;
    private Date departuredate;
    private String departureinfo;
    private String departureremark;
    private String returntype;
    private int returnfollower;
    private int uid;
    private String remark;
    private String implement;
    private int domain;
    private int creater;
    private String created;
    private int modifier;
    private String modified;
    private boolean state;
    private String lastname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public boolean getDriving() {
        return driving;
    }

    public void setDriving(boolean driving) {
        this.driving = driving;
    }

    public boolean getPickup() {
        return pickup;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }

    public Date getArrivaldate() {
        return arrivaldate;
    }

    public void setArrivaldate(Date arrivaldate) {
        this.arrivaldate = arrivaldate;
    }

    public String getArrivalinfo() {
        return arrivalinfo;
    }

    public void setArrivalinfo(String arrivalinfo) {
        this.arrivalinfo = arrivalinfo;
    }

    public String getArrivalremark() {
        return arrivalremark;
    }

    public void setArrivalremark(String arrivalremark) {
        this.arrivalremark = arrivalremark;
    }

    public String getArrivaltype() {
        return arrivaltype;
    }

    public void setArrivaltype(String arrivaltype) {
        this.arrivaltype = arrivaltype;
    }

    public int getArrivalfollower() {
        return arrivalfollower;
    }

    public void setArrivalfollower(int arrivalfollower) {
        this.arrivalfollower = arrivalfollower;
    }

    public String getReturntype() {
        return returntype;
    }

    public void setReturntype(String returntype) {
        this.returntype = returntype;
    }

    public boolean getSendoff() {
        return sendoff;
    }

    public void setSendoff(boolean sendoff) {
        this.sendoff = sendoff;
    }

    public Date getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(Date departuredate) {
        this.departuredate = departuredate;
    }

    public String getDepartureinfo() {
        return departureinfo;
    }

    public void setDepartureinfo(String departureinfo) {
        this.departureinfo = departureinfo;
    }

    public int getReturnfollower() {
        return returnfollower;
    }

    public void setReturnfollower(int returnfollower) {
        this.returnfollower = returnfollower;
    }

    public String getDepartureremark() {
        return departureremark;
    }

    public void setDepartureremark(String departureremark) {
        this.departureremark = departureremark;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImplement() {
        return implement;
    }

    public void setImplement(String implement) {
        this.implement = implement;
    }

    public int getDomain() {
        return domain;
    }

    public void setDomain(int domain) {
        this.domain = domain;
    }

    public int getCreater() {
        return creater;
    }

    public void setCreater(int creater) {
        this.creater = creater;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
