package com.hy.service.ms.impl;

import com.hy.mapper.ms.AdminMapper;
import com.hy.model.Admin;
import com.hy.service.ms.AdminService;
import com.hy.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public String login(String username, String userpass) {
       Admin admin =  adminMapper.selectAdminByUsername(username);
        if (admin.getUserpass().equals(userpass))
        {
            String token = MD5Util.md5(username + new java.util.Date().getTime() + userpass);
            adminMapper.updateToken(token, username);
            return token;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean checkToken(String token) {
        return adminMapper.selectToken(token) != 0;
    }
}
