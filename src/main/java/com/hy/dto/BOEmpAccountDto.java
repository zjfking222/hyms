package com.hy.dto;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/19 18:34
 * @Description:存放人员BO账号权限
 */
public class BOEmpAccountDto {

    //员工号
    private String empnum;
    //人员名称
    private String empname;
    //新增的BO账号
    private List<String> addAccounts;
    //删除的BO账号
    private List<String> delAccounts;

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

    public List<String> getAddAccounts() {
        return addAccounts;
    }

    public void setAddAccounts(List<String> addAccounts) {
        this.addAccounts = addAccounts;
    }

    public List<String> getDelAccounts() {
        return delAccounts;
    }

    public void setDelAccounts(List<String> delAccounts) {
        this.delAccounts = delAccounts;
    }
}
