package com.hy.service.system;

import com.hy.dto.SysRolesDto;
import com.hy.dto.SysRolesUserDto;

import java.util.List;

public interface RoleService {

    List<SysRolesDto> getRoles();

    boolean delRole(int id);

    List<SysRolesDto> searchRoles(String name);
}
