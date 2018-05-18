package com.hy.dto;

import java.util.List;

public class SysRolePermissionWithRidDto {
    private SysRolePermissionDto[] rolePermission;
    private int rid;

    public SysRolePermissionDto[] getRolePermission() {
        return rolePermission;
    }

    public void setRolePermission(SysRolePermissionDto[] rolePermission) {
        this.rolePermission = rolePermission;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
