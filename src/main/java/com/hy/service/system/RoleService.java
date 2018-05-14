package com.hy.service.system;

import com.hy.dto.SysRolesDto;

import java.util.List;

public interface RoleService {
    List<SysRolesDto> getRoles();
    boolean delRole(int id);
    boolean setRole(String name, Integer[] uid);
}
