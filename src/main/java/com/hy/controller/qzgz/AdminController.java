package com.hy.controller.qzgz;

import com.hy.common.ResultObj;
import com.hy.enums.ResultCode;
import com.hy.service.qzgz.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/login")
    public ResultObj login(String username,String userpass)
    {
        String token = adminService.login(username, userpass);
        return token != null ?
                ResultObj.success(token) :
                ResultObj.error(ResultCode.ERROR_UNMATCH, ResultCode.ERROR_UNMATCH.getMsg());
    }

}
