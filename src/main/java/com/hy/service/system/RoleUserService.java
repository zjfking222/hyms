package com.hy.service.system;

import com.hy.dto.SysRolesUserDto;
import com.hy.dto.SysUserDto;

import java.util.List;

public interface RoleUserService {
    boolean setRoleUser (SysRolesUserDto sysRolesUserDto);
    boolean addRoleUser (SysRolesUserDto sysRolesUserDto);
    List<SysUserDto> getUserByRid(int rid);
}
