package com.hy.dto;


import java.util.List;

public class AdDepartmentDto {
    private int did;
    private String name;
    private String oldname;
    private int parentid;
    private String dn;
    private String olddn;
    private List<AdDepartmentDto> children;
    private List<AdStaffDto> staff;
    private int state;
    private String date;
    private String time;
    private int operator;

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public List<AdStaffDto> getStaff() {
        return staff;
    }

    public void setStaff(List<AdStaffDto> staff) {
        this.staff = staff;
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

    public List<AdDepartmentDto> getChild() {
        return children;
    }

    public void setChild(List<AdDepartmentDto> child) {
        this.children = child;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldname() {
        return oldname;
    }

    public void setOldname(String oldname) {
        this.oldname = oldname;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
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
