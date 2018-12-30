package com.hy.config.workplus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: 钱敏杰
 * @Date: 2018/11/28 9:12
 * @Description:恒拓app对接配置
 */
@Component
public class WorkPlusConfig {

    @Value("${workplus.connecturl}")
    private String connecturl;
    @Value("${workplus.granttype}")
    private String granttype;
    @Value("${workplus.scope}")
    private String scope;
    @Value("${workplus.domainid}")
    private String domainid;
    @Value("${workplus.orgid}")
    private String orgid;
    @Value("${workplus.clientid}")
    private String clientid;
    @Value("${workplus.clientsecret}")
    private String clientsecret;

    public String getConnecturl() {
        return connecturl;
    }

    public void setConnecturl(String connecturl) {
        this.connecturl = connecturl;
    }

    public String getGranttype() {
        return granttype;
    }

    public void setGranttype(String granttype) {
        this.granttype = granttype;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getDomainid() {
        return domainid;
    }

    public void setDomainid(String domainid) {
        this.domainid = domainid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientsecret() {
        return clientsecret;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }
}
