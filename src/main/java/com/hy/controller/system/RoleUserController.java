package com.hy.controller.system;

import com.hy.common.ResultObj;
import com.hy.dto.SysRolesUserDto;
import com.hy.enums.ResultCode;
import com.hy.service.system.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/system")
public class RoleUserController {

    @Autowired
    private RoleUserService roleUserService;

    @PostMapping("/roleUser/set")
    public ResultObj setRoleUser(SysRolesUserDto sysRolesUserDto){

        try{
            roleUserService.setRoleUser(sysRolesUserDto);
            return ResultObj.success();
        }catch (Exception e){
            return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
        }
    }
    @PostMapping("/roleUser/add")
    public ResultObj addRoleUser(SysRolesUserDto sysRolesUserDto){

        try{
            roleUserService.addRoleUser(sysRolesUserDto);
            return ResultObj.success();
        }catch (Exception e){
            return ResultObj.error(ResultCode.ERROR_ADD_FAILED);
        }
    }
}
