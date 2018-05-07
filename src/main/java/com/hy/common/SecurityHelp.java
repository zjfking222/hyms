package com.hy.common;

import com.hy.model.HrmResource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class SecurityHelp {

    public static HrmResource getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        HrmResource hrmResource = (HrmResource) subject.getPrincipal();
        return hrmResource;
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
