package com.hy.mapper.ms;

import com.hy.model.SysRoles;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRolesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoles record);

    int insertSelective(SysRoles record);

    SysRoles selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoles record);

    int updateByPrimaryKey(SysRoles record);
}