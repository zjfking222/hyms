package com.hy.controller.system;

import com.hy.common.ResultObj;
import com.hy.service.system.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    @PostMapping("/rolesPm/get")
    public ResultObj getRolesPm(int rid){

        return ResultObj.success(rolePermissionService.getRolePermission(rid));
    }
}
