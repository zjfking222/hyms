package com.hy.common;

import com.hy.model.HrmResource;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class SecurityUtil {

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

    /**
     * @Author 钱敏杰
     * @Description 获取session
     * @Date 2018/11/5 17:41
     * @Param []
     * @return org.apache.shiro.session.Session
     **/
    public static Session getSession(){
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession();
    }

    /**
     * @Author 钱敏杰
     * @Description 获取session中属性
     * @Date 2018/11/5 17:41
     * @Param [key]
     * @return java.lang.Object
     **/
    public static Object getAttribute(String key){
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession().getAttribute(key);
    }

    /**
     * @Author 钱敏杰
     * @Description 保存属性到session
     * @Date 2018/11/5 17:41
     * @Param [key, obj]
     * @return void
     **/
    public static void setAttribute(String key, Object obj){
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute(key, obj);
    }

    /**
     * @Author 钱敏杰
     * @Description 移除session中的属性
     * @Date 2018/11/6 9:20
     * @Param [key]
     * @return void
     **/
    public static void removeAttribute(String key){
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().removeAttribute(key);
    }
}
