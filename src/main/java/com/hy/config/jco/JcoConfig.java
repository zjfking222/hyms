package com.hy.config.jco;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/1 16:53
 * @Description:存放jco配置属性
 */
@Component
public class JcoConfig {

    @Value("${jco.ashost}")
    private String ashost;
    @Value("${jco.sysnr}")
    private String sysnr;
    @Value("${jco.client}")
    private String client;
    @Value("${jco.user}")
    private String user;
    @Value("${jco.passwd}")
    private String passwd;
    @Value("${jco.lang}")
    private String lang;
    @Value("${jco.destination.name}")
    private String destinationName;

    public String getAshost() {
        return ashost;
    }

    public String getSysnr() {
        return sysnr;
    }

    public String getClient() {
        return client;
    }

    public String getUser() {
        return user;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getLang() {
        return lang;
    }

    public String getDestinationName() {
        return destinationName;
    }
}
