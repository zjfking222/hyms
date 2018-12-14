package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/12 14:19
 * @Description:ad域员工号与BO账号关系DTO
 */
public class BOAccadRelationDto {

    //主键
    private Integer id;
    //账号id
    private String accountid;
    //ad域员工号
    private String empnum;
    //ad域员工姓名
    private String empname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
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
