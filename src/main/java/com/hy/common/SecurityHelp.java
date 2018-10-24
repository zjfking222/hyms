package com.hy.common;

import com.hy.model.HrmResource;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class SecurityHelp {

    public static HrmResource getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        //热部署测试配置
        Object key = subject.getPrincipal();
        HrmResource hrmResource = new HrmResource();
        try {
            BeanUtils.copyProperties(hrmResource, key);
        } catch (Exception e) {

        }
        //正式发布配置
//        HrmResource hrmResource = (HrmResource) subject.getPrincipal();
        return hrmResource;
    }

    public static int getDepartmentId() {
        return getUserInfo().getDepartmentid();
    }

    public static int getUserId() {
        return getUserInfo().getId();
    }

    public static String getUserName() {
        return getUserInfo().getLastname();
    }

    public static String getLoginid() {
        return getUserInfo().getLoginid();
    }
}
