package com.hy.model;

import java.util.Date;

public class AdStaff {
    private int id;
    private String sid;
    private String name;
    private int state;
    private String email;
    private String phone;
    private int depid;
    private String depname;
    private String duty;
    private String oldduty;
    private String dn;
    private String olddn;
    private String date;
    private String time;
    private String creater;
    private Date created;
    private String modifier;
    private Date modified;
    private int count;
    private int operator;

    public AdStaff(int id, String name, String email, String phone, int depid, String depname, String duty) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.depid = depid;
        this.depname = depname;
        this.duty = duty;
    }

    public AdStaff() {
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
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

    public int getDepid() {
        return depid;
    }

    public void setDepid(int depid) {
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

    public String getOldduty() {
        return oldduty;
    }

    public void setOldduty(String oldduty) {
        this.oldduty = oldduty;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getOlddn() {
        return olddn;
    }

    public void setOlddn(String olddn) {
        this.olddn = olddn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
