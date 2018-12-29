package com.hy.model;

import java.util.Date;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/3 8:52
 * @Description:ad域员工号与BO账号关系表
 */
public class BOAccadRelation {

    //主键
    private Integer id;
    //账号id
    private String accountid;
    //ad域员工号
    private String empnum;
    //ad域员工姓名
    private String empname;
    //创建人
    private String creater;
    //创建时间
    private Date created;
    //修改人
    private String modifier;
    //修改时间
    private Date modified;

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

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }
}
