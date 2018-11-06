package com.hy.model;

public class LdapStaff {

    private String id;
    private String name;
    private String state;
    private String email;
    private String phone;
    private String depid;
    private String depname;
    private String duty;
    private String dn;
    private String newDn;

    public LdapStaff() {
    }

    public LdapStaff(String id, String name, String state, String email, String phone, String depid, String depname, String duty) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.email = email;
        this.phone = phone;
        this.depid = depid;
        this.depname = depname;
        this.duty = duty;
    }

    public LdapStaff(String id, String name, String state, String email, String phone, String depid, String depname, String duty, String dn) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.email = email;
        this.phone = phone;
        this.depid = depid;
        this.depname = depname;
        this.duty = duty;
        this.dn = dn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getNewDn() {
        return newDn;
    }

    public void setNewDn(String newDn) {
        this.newDn = newDn;
    }
}
