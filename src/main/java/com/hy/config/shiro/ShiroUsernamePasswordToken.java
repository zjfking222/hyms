package com.hy.config.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class ShiroUsernamePasswordToken extends UsernamePasswordToken {
    private boolean authenticated;

    public ShiroUsernamePasswordToken() {
        this.setRememberMe(false);
        this.authenticated = false;
    }

    public ShiroUsernamePasswordToken(String username, char[] password) {
        this(username, (char[]) password, false, (String) null);
    }

    public ShiroUsernamePasswordToken(String username, String password) {
        this(username, (char[]) (password != null ? password.toCharArray() : null), false, (String) null);
    }

    public ShiroUsernamePasswordToken(String username, char[] password, String host) {
        this(username, password, false, host);
    }

    public ShiroUsernamePasswordToken(String username, String password, String host) {
        this(username, password != null ? password.toCharArray() : null, false, host);
    }

    public ShiroUsernamePasswordToken(String username, char[] password, boolean rememberMe) {
        this(username, (char[]) password, rememberMe, (String) null);
    }

    public ShiroUsernamePasswordToken(String username, String password, boolean rememberMe) {
        this(username, (char[]) (password != null ? password.toCharArray() : null), rememberMe, (String) null);
    }

    public ShiroUsernamePasswordToken(String username, String password, boolean rememberMe, String host) {
        this(username, password != null ? password.toCharArray() : null, rememberMe, host);
    }

    public ShiroUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host) {
        this.setRememberMe(rememberMe);
        this.setUsername(username);
        this.setPassword(password);
        this.setHost(host);
        this.authenticated = false;
    }


    /**
     * 初始化Token
     *
     * @param username      用户名
     * @param authenticated 是否已认证
     */
    public ShiroUsernamePasswordToken(String username, boolean authenticated) {
        this.setUsername(username);
        this.authenticated = authenticated;
    }

    public boolean getAuthenticated() {
        return this.authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

}
