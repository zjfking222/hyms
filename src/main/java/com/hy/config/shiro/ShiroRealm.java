package com.hy.config.shiro;

import com.github.pagehelper.util.StringUtil;
import com.hy.common.SecurityUtil;
import com.hy.config.ldap.LdapUtil;
import com.hy.dto.SysUsersDto;
import com.hy.model.HrmResource;
import com.hy.model.LdapStaff;
import com.hy.model.SysPermission;
import com.hy.service.oa.HrmResourceService;
import com.hy.service.system.PermissionService;
import com.hy.service.system.SysUsersService;
import com.hy.utils.RSAUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ShiroRealm extends AuthorizingRealm {

    //@Autowired
    //private HrmResourceService hrmResourceService;

    @Autowired
    private PermissionService permissionService;

    //系统用户
    @Autowired
    private SysUsersService sysUsersService;
    //RSA加密私钥
    @Value("${rsa.private.key}")
    private String privateKey;

    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        List<SysPermission> list = permissionService.getByUserId(SecurityUtil.getUserId());
        for (SysPermission permission : list) {
            if (StringUtil.isNotEmpty(permission.getPermission())) {
                authorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (authenticationToken instanceof ShiroUsernamePasswordToken) {
            ShiroUsernamePasswordToken token = (ShiroUsernamePasswordToken) authenticationToken;
            System.out.println(token.getAuthenticated());
            if (token.getAuthenticated()) {
                LdapStaff staff = LdapUtil.getStaffByUid(token.getUsername());
                if (staff == null)
                    throw new UnknownAccountException();
                ShiroUserInfo userInfo = new ShiroUserInfo(Integer.parseInt(staff.getId()), staff.getId(), staff.getName(), String.valueOf(token.getPassword()), staff.getDepid(), staff.getDepname(), staff.getDuty());
                return new SimpleAuthenticationInfo(userInfo, String.valueOf(token.getPassword()), getName());
            }
        }
        //获取用户的输入的账号
        String loginId = authenticationToken.getPrincipal().toString();
        //根据员工号获取当前用户
        SysUsersDto user = sysUsersService.getUsersByEmpnum(loginId);
        //判断当前用户是否存在于本系统中
        if(user == null){
            //未在本系统中存在账号时不予登录
            throw new IncorrectCredentialsException();
        }
        //根据用户名从ad域中获取用户信息
        LdapStaff aduser = LdapUtil.getStaffByUid(loginId);
        if(aduser == null){
            throw new UnknownAccountException();
        }
        //根据用户名密码取ad域中验证
        String password = String.valueOf(((UsernamePasswordToken) authenticationToken).getPassword());
        String depassword = null;
        try {
            byte[] decodedData = RSAUtil.decryptByPrivateKey(password, privateKey);
            depassword = new String(decodedData);
        } catch (Exception e) {
            logger.error("RSA解密失败！", e);
            throw new IncorrectCredentialsException();
        }
        if(!LdapUtil.checkAuthentication(loginId, depassword)){//ad域中身份验证通过，则继续，否则抛出异常
            throw new IncorrectCredentialsException();
        }
        //原为使用oa系统的账号，现已废弃
        //获取当前系统中的账号
        /*List<SysUsersDto> users = sysUsersService.getUsersByLoginid(loginId);
        if (users == null || users.size() == 0) {
            //未在本系统中存在账号时不予登录
            throw new IncorrectCredentialsException();
        }
        //获取OA中的账号
        HrmResource hrmResource = hrmResourceService.findByLoginId(loginId);
        if (hrmResource == null) {
            throw new UnknownAccountException();
        }*/
        ShiroUserInfo userInfo = new ShiroUserInfo(Integer.parseInt(aduser.getId()), aduser.getId(), aduser.getName(), password, aduser.getDepid(), aduser.getDepname(), aduser.getDuty());
        return new SimpleAuthenticationInfo(userInfo, password, getName());
    }


    /**
     * 重写方法,清除当前用户的的 授权缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 重写方法，清除当前用户的 认证缓存
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    /**
     * 自定义方法：清除所有 授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    /**
     * 自定义方法：清除所有 认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    /**
     * 自定义方法：清除所有的  认证缓存  和 授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
