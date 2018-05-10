package com.hy.mapper.ms;

import com.hy.model.SysRoles;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRolesMapper extends BaseMapper<Integer,SysRoles>{
    List<SysRoles> selectRoles();
}