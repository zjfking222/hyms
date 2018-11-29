package com.hy.controller.index;


import com.hy.common.ResultObj;

import com.hy.common.SecurityUtil;
import com.hy.config.shiro.RedisCacheManager;
import com.hy.config.shiro.ShiroCache;
import com.hy.config.shiro.ShiroUsernamePasswordToken;
import com.hy.config.workplus.AccessInfo;
import com.hy.config.workplus.WorkPlusUtils;
import com.hy.dto.PermissionDto;
import com.hy.enums.ResultCode;
import com.hy.service.system.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RedisCacheManager redisCacheManager;
    @Value("${spring.redis.cachekey.commoncache}")
    private String cacheKey;
    private String tokenKey = "access_token";

    @RequestMapping(value = "/index/login", method = RequestMethod.POST)
    public ResultObj login(@RequestBody Map<String, String> logininfo) {
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

    @RequestMapping(value = "/index/app/logout")
    public void logout(HttpServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        response.sendRedirect("/index/app/login");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletResponse response) throws Exception {
        response.sendRedirect("/index/login.html");
    }


    @RequestMapping(value = "/index/config", method = RequestMethod.POST)
    public ResultObj getMenus(@RequestBody Map<String, String> info) {
        Subject subject = SecurityUtils.getSubject();
        List<PermissionDto> list = null;
        //根据传入的参数判断当前请求是否为手机端传入，若是则获取手机端或兼容的菜单，若否，则获取web端与兼容的菜单
        String fieldType = info.get("fieldType");
        if("0".equals(fieldType)){
            list = permissionService.getUserMenus(SecurityUtil.getUserId(), 0);
        }else if("1".equals(fieldType)){
            list = permissionService.getUserMenus(SecurityUtil.getUserId(), 1);
        }else{
            //未知参数，待添加
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", SecurityUtil.getUserName());
        map.put("menus", list);
        return ResultObj.success(map);
    }

    /**
     * @Author 钱敏杰
     * @Description 对接恒拓app平台到本系统
     * @Date 2018/11/28 17:00
     * @Param [ticket, username, url, response]
     * @return void
     **/
    @RequestMapping(value = "/index/app/workPlusLogin", method = RequestMethod.GET)
    public void workPlusLogin(@RequestParam("ticket") String ticket, @RequestParam("username") String username, @RequestParam("url") String url, HttpServletResponse response) throws IOException {
        try {
            //查看当前缓存中是否已存在恒拓app的accessToken值
            ShiroCache<Serializable, Object> cache = (ShiroCache)redisCacheManager.getCache(cacheKey);
            String accessToken = null;
            if(cache != null ){
                //取应用身份对象
                AccessInfo info = (AccessInfo)cache.get("HT_WorkPlus:" + tokenKey);
                if(info != null){
                    accessToken = info.getAccess_token();
                }
            }
            Map<String, Object> data = null;
            Integer status = null;
            if(StringUtils.isEmpty(accessToken)){
                //不存在accessToken，则从恒拓系统中取回，保存到缓存
                data = WorkPlusUtils.getAccessToken();
                status = (Integer)data.get("status");
                if(status != 0){
                    throw new RuntimeException("无法获取AccessToken！");
                }
                Map<String, Object> result = (Map<String, Object>) data.get("result");
                accessToken = (String)result.get("access_token");
                //保存token相关数据
                this.saveAceessToken(result);
            }
            //检查单点登录
            data = WorkPlusUtils.checkTicket(ticket, accessToken);
            status = (Integer)data.get("status");
            if(status == 0) {
                Subject subject = SecurityUtils.getSubject();
                //需要判断是否已登录，若已登录则不需要再次登录-----未做
                if(!subject.isAuthenticated()){
                    ShiroUsernamePasswordToken token = new ShiroUsernamePasswordToken(username, true,"ht_password");
                    subject.login(token);
                }
                //重定向指定url页面
                response.sendRedirect(url);
            }else {
                //重定向登录页面
                response.sendRedirect("/index/login.html");
            }
            //throw new RuntimeException("11111111");
        } catch (Exception e) {
            logger.error("接入恒拓app失败！", e);
            //重定向登录页面
            response.sendRedirect("/index/login.html");
        }
    }

    /**
     * @Author 钱敏杰
     * @Description 保存AceessToken相关数据
     * @Date 2018/11/28 10:16
     * @Param [data]
     * @return void
     **/
    private void saveAceessToken(Map<String, Object> data){
        AccessInfo info = new AccessInfo();
        //应用的身份凭证
        String accessToken = (String)data.get("access_token");
        info.setAccess_token(accessToken);
        //更新身份凭证
        String refreshToken = (String)data.get("refresh_token");
        info.setRefresh_token(refreshToken);
        //access_token的生效时间
        Long issuedTime = (Long)data.get("issued_time");
        info.setIssued_time(issuedTime);
        //access_token的过期时间
        Long expireTime = (Long)data.get("expire_time");
        info.setExpire_time(expireTime);
        //当前登录用户的标识(目前系统只需要一个)
        String clientId = (String)data.get("client_id");
        info.setClient_id(clientId);
        //设置缓存保存时间
        int expire = (int) (expireTime-issuedTime)/1000-10;
        ShiroCache<Serializable, Object> cache = (ShiroCache)redisCacheManager.getCache(cacheKey);
        cache.put("HT_WorkPlus:" + tokenKey, info, expire);
    }
}

