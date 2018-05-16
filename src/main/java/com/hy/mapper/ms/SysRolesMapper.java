package com.hy.mapper.ms;

import com.hy.model.SysRoles;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRolesMapper extends BaseMapper<Integer,SysRoles>{
    List<SysRoles> selectRoles();
    List<SysRoles> selectRolesByLike(@Param("name")String name);
}