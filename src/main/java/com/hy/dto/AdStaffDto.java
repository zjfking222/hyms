package com.hy.dto;

public class AdStaffDto {
    private int sid;
    private String name;
    private String email;
    private String phone;
    private int depid;
    private String duty;
    private String oldduty;
    private String dn;
    private String olddn;
    private int page;
    private int limit;
    private int state;
    private String date;
    private String time;
    private int modifier;
    private int operator;

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
