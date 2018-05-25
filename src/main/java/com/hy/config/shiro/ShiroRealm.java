package com.hy.config.shiro;

import com.github.pagehelper.util.StringUtil;
import com.hy.common.SecurityHelp;
import com.hy.model.HrmResource;
import com.hy.model.SysPermission;
import com.hy.service.oa.HrmResourceService;
import com.hy.service.system.PermissionService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Security;
import java.util.List;

@Component
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private HrmResourceService hrmResourceService;

    @Autowired
    private PermissionService permissionService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<SysPermission> list = permissionService.getByUserId(SecurityHelp.getUserId());
        for (SysPermission permission : list) {
            if (StringUtil.isNotEmpty(permission.getPermission())) {
                authorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) authenticationToken;
        //获取用户的输入的账号
        String loginId = utoken.getUsername();
        HrmResource hrmResource = hrmResourceService.findByLoginId(loginId);
        if (hrmResource == null)
            throw new UnknownAccountException();
        LOGGER.debug(hrmResource.getPassword().toLowerCase());
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                hrmResource, hrmResource.getPassword().toLowerCase(), getName());
        return authenticationInfo;
    }
}
