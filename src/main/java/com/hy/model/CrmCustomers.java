package com.hy.model;

public class CrmCustomers {

    private int id;
    private String name;
    private String post;
    private String nationality;
    private String address;
    private boolean sex;
    private String mobile;
    private String phone;
    private String email;
    private int btid;
    private int fid;
    private int vip;
    private String remark;
    private int creater;
    private String created;
    private int modifier;
    private String modified;
    private int domain;


    public CrmCustomers() {
    }

    public CrmCustomers(String name, String post, String nationality, String address, boolean sex, String mobile,
                        String phone, String email, int btid, int fid, int vip, String remark, int creater,
                        int modifier, int domain) {
        this.name = name;
        this.post = post;
        this.nationality = nationality;
        this.address = address;
        this.sex = sex;
        this.mobile = mobile;
        this.phone = phone;
        this.email = email;
        this.btid = btid;
        this.fid = fid;
        this.vip = vip;
        this.remark = remark;
        this.creater = creater;
        this.modifier = modifier;
        this.domain = domain;
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

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
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

    public int getBtid() {
        return btid;
    }

    public void setBtid(int btid) {
        this.btid = btid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public int getDomain() {
        return domain;
    }

    public void setDomain(int domain) {
        this.domain = domain;
    }
}
