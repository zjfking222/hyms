package com.hy.mapper.ms;

import com.hy.model.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionMapper extends BaseMapper<Integer,SysPermission> {
    List<SysPermission> selectAll();
    List<SysPermission> selectByUserId(int userId);
    List<SysPermission> selectUserMenus(int userId);
    List<SysPermission> selectUserFieldMenus(@Param("userId")int userId, @Param("ftype")int ftype);
    List<SysPermission>selectMenus(boolean parents);
    int  selectMenusTotal();

    //用于权限控制树形结构
    List<SysPermission> selectRoleMenus();
}