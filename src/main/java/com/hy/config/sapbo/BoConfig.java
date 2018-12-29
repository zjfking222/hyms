package com.hy.config.sapbo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: 詹继锋
 * @Date: 2018/12/4 18:05
 * @Description:
 */
@Component
public class BoConfig {

    @Value("${sapbo.cmc}")
    private String cmc;

    @Value("${sapbo.authentication}")
    private String authentication;

    @Value("${sapbo.maxnum}")
    private int maxNum;

    @Value("${sapbo.cache.expire}")
    private int expire;

    @Value("${sapbo.cache.key}")
    private String cacheKey;

    @Value("${sapbo.opendocUrl}")
    private   String opendocUrl;

    public String getCmc() {
        return cmc;
    }
    public void setCmc(String cmc) {
        this.cmc = cmc;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public String getCacheKey() {
        return cacheKey;
    }

    public void setCacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
    }


    public String getOpendocUrl() {
        return opendocUrl;
    }

    public void setOpendocUrl(String opendocUrl) {
        this.opendocUrl = opendocUrl;
    }


}
