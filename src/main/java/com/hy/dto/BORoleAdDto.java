package com.hy.dto;

/**
 * @Author 沈超宇
 * @Description 角色、AD员工号关系表DTO
 * @Date 2018/12/13 14:10
 **/
public class BORoleAdDto {
    private int id;
    private int rid;
    private String empnum;
    private String empname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getEmpnum() {
        return empnum;
    }

    public void setEmpnum(String empnum) {
        this.empnum = empnum;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }
}
