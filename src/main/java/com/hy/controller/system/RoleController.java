package com.hy.controller.system;

import com.hy.common.ResultObj;
import com.hy.service.system.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/roles/getAll")
    public ResultObj getAllRoles()
    {
        return ResultObj.success(roleService.getRoles());
    }
}
