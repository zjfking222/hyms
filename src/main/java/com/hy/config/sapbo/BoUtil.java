package com.hy.config.sapbo;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.security.ILogonTokenMgr;
import com.hy.config.shiro.RedisCacheManager;
import com.hy.config.shiro.ShiroCache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @Auther: 詹继锋
 * @Date: 2018/12/4 17:33
 * @Description:BO报表单点登录
 */
@Component
public class BoUtil {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BoUtil.class);

    private static String casekey;
    private static BoConfig boConfig;
    @Autowired
    private static RedisCacheManager redisCacheManager;

    @Autowired
    public void setBoConfig(BoConfig boConfig) {
        BoUtil.boConfig = boConfig;
    }

    @Autowired
    public void setRedisCacheManager(RedisCacheManager redisCacheManager) {
        BoUtil.redisCacheManager = redisCacheManager;
    }

    @Value("${spring.redis.cachekey.commonkey}")
    public void setCasekey(String casekey) {
        BoUtil.casekey = casekey;
    }

    /**
     * @return java.lang.String
     * @Author 詹继锋
     * @Date 2018/12/4 19:20
     * @Description
     * @Param: boLogonId BO登录ID，password BO登录密码
     */
    public static String getToekn(String boLogonId, String password) throws SDKException {
        String token = null;
        /*ShiroCache<Serializable, Object> cache = (ShiroCache) redisCacheManager.getCache(casekey);
        String key = boConfig.getCacheKey() + boLogonId + ":";
        if (cache.hasKey(key)) {
            BoToken boToken = (BoToken) cache.get(key);
            Date date = new Date();
            if (date.getTime() < boToken.getExpire().getTime())
                token = boToken.getToken();
        }
        if (StringUtils.isEmpty(token)) {*/
            token = BoUtil.getBOToekn(boLogonId, password);
       // }
        return token;
    }

    /**
     * @return
     * @Author 詹继锋
     * @Date 2018/12/5 14:19
     * @Description
     * @Param: boLogonId:BO登录账号，password：登录密码，cacheKey
     */
    private static String getBOToekn(String boLogonId, String password) throws SDKException {
        ISessionMgr sessionMgr = CrystalEnterprise.getSessionMgr();
        IEnterpriseSession session = sessionMgr.logon(boLogonId, password, boConfig.getCmc(), boConfig.getAuthentication());
        ILogonTokenMgr tokenMgr = session.getLogonTokenMgr();
        /* 其中createLogonToken(java.lang.String clientComputerName, int validMinutes, int validNumOfLogons)
        clientComputerName为使用这个token的客户端计算机名，空字符串表示该token可被任何客户端使用；
        validMinutes为token的有效时间（分钟）；
        validNumOfLogons 表示该token允许被使用的最大次数。*/
        Date issued = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issued);
        calendar.add(Calendar.MINUTE, boConfig.getExpire());
        Date expire = calendar.getTime();
        String token = tokenMgr.createLogonToken("", boConfig.getExpire(), boConfig.getMaxNum());
        BoToken boToken = new BoToken(token, issued, expire);
        ShiroCache<Serializable, Object> cache = (ShiroCache) redisCacheManager.getCache(casekey);
        String cacheKey = boConfig.getCacheKey() + boLogonId + ":";
        cache.put(cacheKey, boToken, (boConfig.getExpire() - 10) * 60);
        return token;
    }

    /**
     * @return String
     * @Author 詹继锋
     * @Date 2018/12/5 15:50
     * @Description 获取报表路径
     * @Param: iDocID:BO报表的CUID,boToken:访问授权token
     */
    public static String getBOUrl(String iDocID, String boToken) {
        if (StringUtils.isEmpty(iDocID) || StringUtils.isEmpty(iDocID))
            return "";

        return boConfig.getOpendocUrl() + "?sIDType=CUID&iDocID=" + iDocID + "&token=" + boToken;
    }

}
