package com.hy.dto;

public class CrmCustomersDto {
    private int id;
    private String name;
    private String post;
    private String nationality;
    private String address;
    private String sex;
    private String mobile;
    private String phone;
    private String email;
    private CrmBusinesstypeDto btid;
    private CrmFirmsFetchDto fid;
    private boolean vip;
    private String remark;

    public CrmCustomersDto(int id, String name, String post, String nationality, String address, String mobile, String phone, String email, CrmBusinesstypeDto btid, CrmFirmsFetchDto fid, boolean vip, String remark) {
        this.id = id;
        this.name = name;
        this.post = post;
        this.nationality = nationality;
        this.address = address;
        this.mobile = mobile;
        this.phone = phone;
        this.email = email;
        this.btid = btid;
        this.fid = fid;
        this.vip = vip;
        this.remark = remark;
    }

    public CrmCustomersDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CrmBusinesstypeDto getBtid() {
        return btid;
    }

    public void setBtid(CrmBusinesstypeDto btid) {
        this.btid = btid;
    }

    public CrmFirmsFetchDto getFid() {
        return fid;
    }

    public void setFid(CrmFirmsFetchDto fid) {
        this.fid = fid;
    }

    public boolean getVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
