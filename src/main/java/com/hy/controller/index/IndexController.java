package com.hy.controller.index;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.hy.common.ResultObj;
import com.hy.dto.UserDto;
import com.hy.enums.ResultCode;
import com.hy.service.oa.HrmResourceService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
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
import java.util.Map;
//import org.json.JSONObject;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private HrmResourceService hrmResourceService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResultObj login(@RequestBody Map<String, String> logininfo) {
//      return ResultObj.success(logininfo);
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
}

