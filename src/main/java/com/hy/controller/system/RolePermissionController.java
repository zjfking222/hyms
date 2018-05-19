package com.hy.controller.system;

import com.hy.common.ResultObj;
import com.hy.dto.SysRolePermissionDto;
import com.hy.dto.SysRolePermissionWithRidDto;
import com.hy.enums.ResultCode;
import com.hy.service.system.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    @PostMapping("/rolesPm/get")
    public ResultObj getRolesPm(int rid){

        return ResultObj.success(rolePermissionService.getRolePermission(rid));
    }
    @PostMapping("/rolesPm/set")
    public ResultObj getRolesPm(@RequestBody SysRolePermissionWithRidDto sysRolePermissionWithRidDto){

        return rolePermissionService.setRolePermission(sysRolePermissionWithRidDto)?
                ResultObj.success():
                ResultObj.error(ResultCode.ERROR_UNKNOWN);
    }
}
