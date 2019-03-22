package com.hy.controller.workplus;

import com.hy.config.shiro.RedisCacheManager;
import com.hy.config.shiro.ShiroCache;
import com.hy.config.shiro.ShiroUsernamePasswordToken;
import com.hy.config.workplus.AccessInfo;
import com.hy.config.workplus.WorkPlusUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @Auther: 钱敏杰
 * @Date: 2019/3/9 13:58
 * @Description:与恒拓的workplus平台实现对接
 */
@RestController
@RequestMapping("/WPJoint")
public class WorkPlusJointController {

    private static final Logger logger = LoggerFactory.getLogger(WorkPlusJointController.class);

    @Autowired
    private RedisCacheManager redisCacheManager;
    @Value("${spring.redis.cachekey.commonkey}")
    private String cacheKey;
    private String tokenKey = "ht_wp:access_token";

    /**
     * @return void
     * @Author 钱敏杰
     * @Description 对接恒拓app平台到本系统
     * @Date 2018/11/28 17:00
     * @Param [ticket, username, url, response]
     **/
    @RequestMapping(value = "/index/login", method = RequestMethod.GET)
    public void workPlusLogin(@RequestParam("ticket") String ticket, @RequestParam("username") String username, @RequestParam("url") String url, HttpServletResponse response) throws IOException {
        try {
            //查看当前缓存中是否已存在恒拓app的accessToken值
            ShiroCache<Serializable, Object> cache = (ShiroCache) redisCacheManager.getCache(cacheKey);
            String accessToken = null;
            if (cache != null) {
                //取应用身份对象
                AccessInfo info = (AccessInfo) cache.get(tokenKey);
                if (info != null) {
                    accessToken = info.getAccess_token();
                }
            }
            Map<String, Object> data = null;
            Integer status = null;
            if (StringUtils.isEmpty(accessToken)) {
                //不存在accessToken，则从恒拓系统中取回，保存到缓存
                data = WorkPlusUtils.getAccessToken();
                status = (Integer) data.get("status");
                if (status != 0) {
                    throw new RuntimeException("无法获取AccessToken！status:" + status + "；message:" + data.get("message"));
                }
                Map<String, Object> result = (Map<String, Object>) data.get("result");
                accessToken = (String) result.get("access_token");
                //保存token相关数据
                this.saveAceessToken(result);
            }
            //检查单点登录
            data = WorkPlusUtils.checkTicket(ticket, accessToken);
            status = (Integer) data.get("status");
            if (status == 0) {
                Subject subject = SecurityUtils.getSubject();
                //需要判断是否已登录，若已登录则不需要再次登录
                if (!subject.isAuthenticated()) {
                    ShiroUsernamePasswordToken token = new ShiroUsernamePasswordToken(username, true, "ht_password");
                    subject.login(token);
                }
                //重定向指定url页面
                response.sendRedirect(url);
            } else {
                //重定向登录页面
                response.sendRedirect("/index/login.html");
            }
        } catch (Exception e) {
            logger.error("接入恒拓app失败！", e);
            //重定向登录页面
            response.sendRedirect("/index/login.html");
        }
    }

    /**
     * @return void
     * @Author 钱敏杰
     * @Description 保存AceessToken相关数据
     * @Date 2018/11/28 10:16
     * @Param [data]
     **/
    private void saveAceessToken(Map<String, Object> data) {
        AccessInfo info = new AccessInfo();
        //应用的身份凭证
        String accessToken = (String) data.get("access_token");
        info.setAccess_token(accessToken);
        //更新身份凭证
        String refreshToken = (String) data.get("refresh_token");
        info.setRefresh_token(refreshToken);
        //access_token的生效时间
        Long issuedTime = (Long) data.get("issued_time");
        info.setIssued_time(issuedTime);
        //access_token的过期时间
        Long expireTime = (Long) data.get("expire_time");
        info.setExpire_time(expireTime);
        //当前登录用户的标识(目前系统只需要一个)
        String clientId = (String) data.get("client_id");
        info.setClient_id(clientId);
        //设置缓存保存时间
        int expire = (int) (expireTime - issuedTime) / 1000 - 10;
        ShiroCache<Serializable, Object> cache = (ShiroCache) redisCacheManager.getCache(cacheKey);
        cache.put(tokenKey, info, expire);
    }
}
