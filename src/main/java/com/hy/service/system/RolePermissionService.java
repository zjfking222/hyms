package com.hy.service.system;

import com.hy.dto.SysRolePermissionTreeDto;
import com.hy.dto.SysRolePermissionWithRidDto;

import java.util.List;

public interface RolePermissionService {
    /**
     * 获取树结构视图
     * @return 树结构
     */
    List<SysRolePermissionTreeDto> getRolePermission(Integer rid);
    /**
     * 设置树结构视图
     * @return boolean
     */
    boolean setRolePermission(SysRolePermissionWithRidDto sysRolePermissionWithRidDto);
}
