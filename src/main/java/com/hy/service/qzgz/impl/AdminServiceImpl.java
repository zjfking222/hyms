package com.hy.service.qzgz.impl;

import com.hy.mapper.ms.QzgzAdminMapper;
import com.hy.model.QzgzAdmin;
import com.hy.service.qzgz.AdminService;
import com.hy.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    private QzgzAdminMapper adminMapper;

    @Override
    public String login(String username, String userpass) {
       QzgzAdmin admin =  adminMapper.selectAdminByUsername(username);
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
