package com.hy.controller.api;

import com.hy.common.ResultObj;
import com.hy.enums.ResultCode;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

@RestController
@RequestMapping("/api")
public class SsoController {

    @Value("${server.port}")
    private String port;

    @PostMapping("/sso")
    public Object SsoPost() {

        return null;
    }

    @GetMapping(value = "/sso", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object SsoGet(@RequestParam String s_user, @RequestParam String s_password) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(s_user, s_password);
        try {
            subject.login(token);
        } catch (UnknownAccountException lae) {
            token.clear();
            return ResultObj.error(ResultCode.ERROR_USER_UNEXISTS);
        } catch (AuthenticationException e) {
            token.clear();
            return ResultObj.error(ResultCode.ERROR_USER_UNMATCH);
        }
        HashMap<String, String> map = new HashMap<>();
        try {
            String address = InetAddress.getLocalHost().getHostAddress();
            map.put("url", address + ":" + port + "/index/index.html");
        } catch (UnknownHostException ex) {
            map.put("url", "");
        }
        return ResultObj.success(map);
    }
}
