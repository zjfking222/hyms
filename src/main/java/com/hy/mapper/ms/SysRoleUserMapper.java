package com.hy.mapper.ms;

import com.hy.model.SysRoleUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleUserMapper extends BaseMapper<Integer,SysRoleUser>{
    List<SysRoleUser> selectByRid(@Param("rid")int rid);
    Integer deleteByRidnUid(SysRoleUser sysRoleUser);
}