package com.hy.service.system;

import com.hy.dto.SysRolePermissionTreeDto;
import com.hy.dto.SysRolePermissionWithRidDto;

import java.util.List;

public interface RolePermissionService {
    List<SysRolePermissionTreeDto> getRolePermission(Integer rid);
    boolean setRolePermission(SysRolePermissionWithRidDto sysRolePermissionWithRidDto);
}
