package com.hy.mapper.ms;

import com.hy.model.SysPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionMapper extends BaseMapper<Integer,SysPermission> {
    List<SysPermission> selectUserMenus();
    List<SysPermission>selectMenus(boolean parents);
    int  selectMenusTotal();

    //用于权限控制树形结构
    List<SysPermission> selectRoleMenus();
}