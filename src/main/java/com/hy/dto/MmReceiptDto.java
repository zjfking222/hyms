package com.hy.dto;


public class MmReceiptDto {
    private int id;
    private int mid;
    private CrmCustomersDto customers;
    private boolean driving;
    private boolean pickup;
    private String arrivaldate;
    private String arrivalinfo;
    private String arrivaltype;
    private String arrivalremark;
    private boolean sendoff;
    private String departuredate;
    private String departureinfo;
    private String returntype;
    private String departureremark;
    private int uid;
    private String remark;
    private String implement;
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

    public CrmCustomersDto getCustomers() {
        return customers;
    }

    public void setCustomers(CrmCustomersDto customers) {
        this.customers = customers;
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

    public String getArrivaldate() {
        return arrivaldate;
    }

    public void setArrivaldate(String arrivaldate) {
        this.arrivaldate = arrivaldate;
    }

    public String getArrivalinfo() {
        return arrivalinfo;
    }

    public void setArrivalinfo(String arrivalinfo) {
        this.arrivalinfo = arrivalinfo;
    }

    public String getArrivaltype() {
        return arrivaltype;
    }

    public void setArrivaltype(String arrivaltype) {
        this.arrivaltype = arrivaltype;
    }

    public String getArrivalremark() {
        return arrivalremark;
    }

    public void setArrivalremark(String arrivalremark) {
        this.arrivalremark = arrivalremark;
    }

    public boolean getSendoff() {
        return sendoff;
    }

    public void setSendoff(boolean sendoff) {
        this.sendoff = sendoff;
    }

    public String getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(String departuredate) {
        this.departuredate = departuredate;
    }

    public String getDepartureinfo() {
        return departureinfo;
    }

    public void setDepartureinfo(String departureinfo) {
        this.departureinfo = departureinfo;
    }

    public String getReturntype() {
        return returntype;
    }

    public void setReturntype(String returntype) {
        this.returntype = returntype;
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
