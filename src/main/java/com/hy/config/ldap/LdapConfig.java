package com.hy.config.ldap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: bunnyhsu
 * @Date: 2018/11/6 10:03
 * @Description: Ldap Configuration file
 */
@Component
public class LdapConfig {

    @Value("${ldap.host}")
    private String host;
    @Value("${ldap.port}")
    private String port;
    @Value("${ldap.account}")
    private String account;
    @Value("${ldap.password}")
    private String password;
    @Value("${ldap.baseDn}")
    private String baseDn;
    @Value("${ldap.uName}")
    private String uName;
    @Value("${ldap.maxResult}")
    private String maxResult;
    @Value("${ldap.cer}")
    private String cer;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public String getBaseDn() {
        return baseDn;
    }

    public String getuName() {
        return uName;
    }

    public String getMaxResult() {
        return maxResult;
    }

    public String getCer() {
        return cer;
    }

    public void setCer(String cer) {
        this.cer = cer;
    }
}
