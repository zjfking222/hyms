package com.hy.config.shiro;

import com.hy.model.HrmResource;
import com.hy.service.oa.HrmResourceService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private HrmResourceService hrmResourceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addRole("系统管理员");
        authorizationInfo.addStringPermission("admin:edit");
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) authenticationToken;
        //获取用户的输入的账号
        String loginId = utoken.getUsername();
        LOGGER.debug(loginId);
        HrmResource hrmResource = hrmResourceService.findByLoginId(loginId);
        if (hrmResource == null)
            throw new UnknownAccountException();
        LOGGER.debug(hrmResource.getPassword().toLowerCase());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                hrmResource.getLoginid(), hrmResource.getPassword().toLowerCase(), getName());
        return authenticationInfo;
    }
}
