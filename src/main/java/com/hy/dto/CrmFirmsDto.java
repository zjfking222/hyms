package com.hy.dto;

/**
 *  企业信息返回DTO
 */

public class CrmFirmsDto {

    private int id;
    private String name;
    private String phone;
    private String address;
    private String contacter;
    private String cmobile;
    private String cphone;
    private String email;
    private CrmBusinesstypeDto btid;
    private String remark;

    public CrmFirmsDto(int id, String name, String phone, String address, String contacter, String cmobile, String cphone, String email, CrmBusinesstypeDto btid, String remark) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.contacter = contacter;
        this.cmobile = cmobile;
        this.cphone = cphone;
        this.email = email;
        this.btid = btid;
        this.remark = remark;
    }

    public CrmFirmsDto() {
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

    public CrmBusinesstypeDto getBtid() {
        return btid;
    }

    public void setBtid(CrmBusinesstypeDto btid) {
        this.btid = btid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
