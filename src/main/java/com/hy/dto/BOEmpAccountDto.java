package com.hy.dto;

import java.util.List;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/12/19 18:34
 * @Description:存放人员BO账号权限
 */
public class BOEmpAccountDto {

    //人员
    private SysUserDto emp;
    //BO账号
    private List<BOAccountDto> accounts;

    public List<BOAccountDto> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<BOAccountDto> accounts) {
        this.accounts = accounts;
    }

    public SysUserDto getEmp() {
        return emp;
    }

    public void setEmp(SysUserDto emp) {
        this.emp = emp;
    }
}
