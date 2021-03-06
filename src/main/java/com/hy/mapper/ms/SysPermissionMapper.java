package com.hy.mapper.ms;

import com.hy.model.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysPermissionMapper extends BaseMapper<Integer,SysPermission> {

    List<SysPermission> selectAll();
    List<SysPermission> selectByUserId(String userId);
    List<SysPermission> selectUserMenus(String userId);
    List<SysPermission>selectMenus(@Param("filters")String filters, boolean parents, @Param("sort")String sort, @Param("dir")String dir);
    int  selectMenusTotal(@Param("filters")String filters);
    //用于权限控制树形结构
    List<SysPermission> selectRoleMenus();
    List<SysPermission> selectUserFieldMenus(@Param("userId")String userId, @Param("ftype")int ftype);
}