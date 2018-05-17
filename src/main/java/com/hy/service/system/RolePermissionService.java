package com.hy.service.system;

import com.hy.dto.SysRolePermissionDto;
import com.hy.dto.SysRolePermissionTreeDto;

import java.util.List;

public interface RolePermissionService {
    List<SysRolePermissionTreeDto> getRolePermission(Integer rid);
    boolean setRolePermission(List<SysRolePermissionDto> sysRolePermissionDtos);
}
