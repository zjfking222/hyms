package com.hy.model;

public class CrmFirms {

    private int id;
    private String name;
    private String phone;
    private String address;
    private String contacter;
    private String cmobile;
    private String cphone;
    private String email;
    private int btid;
    private String remark;
    private String creater;
    private String created;
    private String modifier;
    private String modified;
    private int domain;

    public CrmFirms() {
    }

    public CrmFirms(String name, String phone, String address, String contacter, String cmobile, String cphone, String email, int btid, String remark, String creater, String modifier, int domain) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.contacter = contacter;
        this.cmobile = cmobile;
        this.cphone = cphone;
        this.email = email;
        this.btid = btid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter;
    }

    public String getCmobile() {
        return cmobile;
    }

    public void setCmobile(String cmobile) {
        this.cmobile = cmobile;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
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
