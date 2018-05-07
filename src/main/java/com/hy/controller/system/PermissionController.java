package com.hy.controller.system;


import com.hy.common.ResultObj;
import com.hy.dto.PermissionDto;
import com.hy.enums.ResultCode;
import com.hy.service.system.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/system")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 获取菜单列表
     *
     * @param map 查询参数
     * @return
     */
    @RequestMapping(value = "/menus/get", method = RequestMethod.POST)
    public ResultObj getMenus(@RequestBody Map<String, Object> map) {
        if (map.containsKey("p")) {
            List<PermissionDto> list = permissionService.getParentMenus();
            return ResultObj.success(list);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("total", permissionService.getMenusTotal());
        resultMap.put("data", permissionService.getMenus((int) map.get("page"), (int) map.get("pageSize")));
        return ResultObj.success(resultMap);
    }

    /**
     * 添加菜单
     *
     * @param permissionDto 菜单对象
     * @return
     */
    @RequestMapping(value = "/menus/add", method = RequestMethod.POST)
    public ResultObj addMenus(@RequestBody PermissionDto permissionDto) {
        try {
            if (permissionDto.getName() == null || "".equals(permissionDto.getName()))
                return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
            permissionDto = permissionService.addMenus(permissionDto);
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_ADD_FAILED, e.getMessage());
        }
        return ResultObj.success(permissionDto);
    }

    /**
     * 更新菜单
     *
     * @param permissionDto 菜单对象
     * @return
     */
    @RequestMapping(value = "/menus/update", method = RequestMethod.POST)
    public ResultObj updateMenus(@RequestBody PermissionDto permissionDto) {
        try {
            if (permissionDto.getId() == null || permissionDto.getName() == null || "".equals(permissionDto.getName()))
                return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
            int count = permissionService.updateMenus(permissionDto);
            if (count == 0)
                return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED);
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_UPDATE_FAILED, e.getMessage());
        }
        return ResultObj.success(permissionDto);
    }

    /**
     * 删除菜单
     *
     * @param permissionDto
     * @return
     */
    @RequestMapping(value = "/menus/delete", method = RequestMethod.POST)
    public ResultObj deleteMenus(@RequestBody PermissionDto permissionDto) {
        try {
            if (permissionDto.getId() == null)
                return ResultObj.error(ResultCode.ERROR_INVALID_PARAMETER);
            int id = permissionService.deleteById(permissionDto.getId());
        } catch (Exception e) {
            return ResultObj.error(ResultCode.ERROR_DELETE_FAILED, e.getMessage());
        }
        return ResultObj.success(permissionDto);
    }
}

