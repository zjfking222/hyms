package com.hy.service.system;

import com.hy.dto.SysRolesUserDto;

public interface RoleUserService {
    boolean setRoleUser (SysRolesUserDto sysRolesUserDto);
    boolean addRoleUser (SysRolesUserDto sysRolesUserDto);
}
