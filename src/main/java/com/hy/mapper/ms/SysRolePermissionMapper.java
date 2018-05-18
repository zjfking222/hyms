package com.hy.mapper.ms;

import com.hy.model.SysRolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRolePermissionMapper extends BaseMapper<Integer,SysRolePermission>{
    List<SysRolePermission> selectRolePermissionByRid(Integer rid);
    Integer deleteRolePermissionByRid(Integer rid);
}