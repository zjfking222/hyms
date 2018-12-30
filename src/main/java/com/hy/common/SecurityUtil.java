package com.hy.common;

import com.hy.config.shiro.ShiroUserInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import java.lang.reflect.Method;

public class SecurityUtil {

    public static ShiroUserInfo getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        //热部署测试配置
        Object key = subject.getPrincipal();
        ShiroUserInfo userInfo = new ShiroUserInfo();
        try {
            BeanUtils.copyProperties(userInfo, key);
        } catch (Exception e) {

        }
        //正式发布配置
//        ShiroUserInfo userInfo = (ShiroUserInfo) subject.getPrincipal();
        return userInfo;
    }

    public static int getDepartmentId() {
        if (StringUtils.isEmpty(getUserInfo().getDepid()))
            return -1;
        return Integer.parseInt(getUserInfo().getDepid());
    }

    public static String getUserName() {
        return getUserInfo().getName();
    }

    public static String getLoginid() {
        return getUserInfo().getLoginid();
    }

    /**
     * @return org.apache.shiro.session.Session
     * @Author 钱敏杰
     * @Description 获取session
     * @Date 2018/11/5 17:41
     * @Param []
     **/
    public static Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession();
    }

    /**
     * @return java.lang.Object
     * @Author 钱敏杰
     * @Description 获取session中属性
     * @Date 2018/11/5 17:41
     * @Param [key]
     **/
    public static Object getAttribute(String key) {
        Subject subject = SecurityUtils.getSubject();
        return subject.getSession().getAttribute(key);
    }

    /**
     * @return void
     * @Author 钱敏杰
     * @Description 保存属性到session
     * @Date 2018/11/5 17:41
     * @Param [key, obj]
     **/
    public static void setAttribute(String key, Object obj) {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute(key, obj);
    }

    /**
     * @return void
     * @Author 钱敏杰
     * @Description 移除session中的属性
     * @Date 2018/11/6 9:20
     * @Param [key]
     **/
    public static void removeAttribute(String key) {
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().removeAttribute(key);
    }

    /**
     * @Author 钱敏杰
     * @Description 更改Principal对象属性
     * @Date 2018/11/22 10:21
     * @Param [methodName, value]
     * @return void
     **/
    public static void setPrincipalAttribute(String methodName, String value) throws Exception{
        Subject subject = SecurityUtils.getSubject();
        //用户信息
        Object key = subject.getPrincipal();
        //热部署下不能强转，使用反射调用方法
        Class clazz = key.getClass();
        Method m = clazz.getMethod(methodName, String.class);
        m.invoke(key, value);
        //调出当前属性存放集合
        PrincipalCollection principalCollection = subject.getPrincipals();
        //当前用户的realm名称
        String realmName = principalCollection.getRealmNames().iterator().next();
        //重新加载Principal
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(key, realmName);
        subject.runAs(newPrincipalCollection);
    }
}
