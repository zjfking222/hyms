package com.hy.config.ums;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/20 14:35
 * @Description:存放桐乡联通webservice接口配置数据
 */
@Component
public class UmsConfig {

    @Value("${ums.webservice.address}")
    private String address;
    @Value("${ums.webservice.eNumber}")
    private String eNumber;
    @Value("${ums.webservice.username}")
    private String username;
    @Value("${ums.webservice.password}")
    private String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String geteNumber() {
        return eNumber;
    }

    public void seteNumber(String eNumber) {
        this.eNumber = eNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
