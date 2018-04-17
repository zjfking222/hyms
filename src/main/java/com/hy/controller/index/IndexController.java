package com.hy.controller.index;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hy.common.ResultObj;
import com.hy.dto.UserDto;
import com.hy.enums.ResultCode;
import com.hy.service.oa.HrmResourceService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
//import org.json.JSONObject;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    private HrmResourceService hrmResourceService;

    @ResponseBody
    @GetMapping("/login")
    public ResultObj login(String ud, Integer i) {

//        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
//            request.setAttribute("msg", "用户名或密码不能为空！");
//            return "login";
//        }
        UserDto user = new UserDto();
        user.setUserid("zhanjf");
        user.setPassword("a0462110ce6cfcacc706b775d1ef159e---");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserid(), user.getPassword());
        try {
            subject.login(token);
        } catch (UnknownAccountException lae) {
            token.clear();
            return ResultObj.error(ResultCode.ERROR_FUNCTION_NO_ACCESS);
        } catch (AuthenticationException e) {
            token.clear();
            return ResultObj.error(ResultCode.ERROR_FUNCTION_NO_ACCESS);
        }
        return ResultObj.success();
    }
}

