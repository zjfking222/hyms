package com.hy.controller.index;


import com.hy.common.ResultObj;

import com.hy.common.SecurityUtil;
import com.hy.dto.PermissionDto;
import com.hy.enums.ResultCode;
import com.hy.service.system.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/index/login", method = RequestMethod.POST)
    public ResultObj login(@RequestBody Map<String, String> logininfo) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(logininfo.get("loginid"), logininfo.get("password"));
        try {
            subject.login(token);
        } catch (UnknownAccountException lae) {
            token.clear();
            return ResultObj.error(ResultCode.ERROR_USER_UNEXISTS);
        } catch (AuthenticationException e) {
            token.clear();
            return ResultObj.error(ResultCode.ERROR_USER_UNMATCH, logininfo.get("password"));
        }
        return ResultObj.success();
    }

//    @RequestMapping(value = "/index/logout")
//    public void logout(HttpServletResponse response) throws Exception {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        response.sendRedirect("/index/login.html");
//    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletResponse response) throws Exception {
        response.sendRedirect("/index/login.html");
    }


    @RequestMapping(value = "/index/config", method = RequestMethod.POST)
    public ResultObj getMenus(@RequestBody Map<String, String> info) {
        Subject subject = SecurityUtils.getSubject();
        List<PermissionDto> list = null;
        //根据传入的参数判断当前请求是否为手机端传入，若是则获取手机端或兼容的菜单，若否，则获取web端与兼容的菜单
        String fieldType = info.get("fieldType");
        if("0".equals(fieldType)){
            list = permissionService.getUserMenus(SecurityUtil.getUserId(), 0);
        }else if("1".equals(fieldType)){
            list = permissionService.getUserMenus(SecurityUtil.getUserId(), 1);
        }else{
            //未知参数，待添加
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", SecurityUtil.getUserName());
        map.put("menus", list);
        return ResultObj.success(map);
    }
}

