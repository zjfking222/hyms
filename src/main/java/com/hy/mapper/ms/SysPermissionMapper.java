package com.hy.mapper.ms;

import com.hy.model.SysPermission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionMapper extends BaseMapper<Integer,SysPermission> {
    List<SysPermission> selectUserMenus();
    List<SysPermission>selectMenus(boolean parents);
    int  selectMenusTotal();

    List<SysPermission> selectRoleMenus();
}