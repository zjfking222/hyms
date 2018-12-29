package com.hy.dto;

import java.awt.*;

/**
 *   nHrmResources 新增的角色用户组 n = new
 *   rHrmResources 移除的角色用户组 r = remove
 */
public class SysRolesUserDto {

//    private int id;
    private int rid;
    private String name;
    private String description;//description仅用于BO报表角色管理描述
    private BORoleAccountDto[] boRoleAccounts;//用于角色BO账号添加
    private int[] delAcc;//用于角色BO账号删除
    private HrmResourceDto[] nHrmResources;
    private SysRolesUserDelDto[] rHrmResources;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BORoleAccountDto[] getBoRoleAccounts() {
        return boRoleAccounts;
    }

    public void setBoRoleAccounts(BORoleAccountDto[] boRoleAccounts) {
        this.boRoleAccounts = boRoleAccounts;
    }

    public int[] getDelAcc() {
        return delAcc;
    }

    public void setDelAcc(int[] delAcc) {
        this.delAcc = delAcc;
    }

    public HrmResourceDto[] getnHrmResources() {
        return nHrmResources;
    }

    public void setnHrmResources(HrmResourceDto[] nHrmResources) {
        this.nHrmResources = nHrmResources;
    }

    public SysRolesUserDelDto[] getrHrmResources() {
        return rHrmResources;
    }

    public void setrHrmResources(SysRolesUserDelDto[] rHrmResources) {
        this.rHrmResources = rHrmResources;
    }
}
