package com.hy.config.shiro;

import com.github.pagehelper.util.StringUtil;
import com.hy.model.SysPermission;
import com.hy.service.system.PermissionService;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.*;


@Configuration
public class ShiroConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfiguration.class);

    @Value("${spring.shiro.redis.cacheExpire:3600}")
    private int cacheExpire;
    @Value("${spring.shiro.session.expire:1800}")
    private int sessionExpire;

    @Resource
    private ShiroSessionDAO sessionDAO;

    @Autowired
    private PermissionService permissionService;

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroRealm myShiroRealm() {
        return new ShiroRealm();
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        //shiro缓存时间
        redisCacheManager.setExpire(cacheExpire);
        return redisCacheManager;
    }

    @Bean("shiroSessionFactory")
    public ShiroSessionFactory sessionFactory() {
        return new ShiroSessionFactory();
    }

    /**
     * SessionDAO的作用是为Session提供CRUD并进行持久化的一个shiro组件
     * MemorySessionDAO 直接在内存中进行会话维护
     * EnterpriseCacheSessionDAO  提供了缓存功能的会话维护，默认情况下使用MapCache实现，内部使用ConcurrentHashMap保存缓存的会话。
     *
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {
        ShiroSessionDAO redisSessionDAO = new ShiroSessionDAO();
        return redisSessionDAO;
    }

    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(sessionListener());
        sessionManager.setSessionListeners(listeners);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        sessionManager.setSessionDAO(this.sessionDAO);
        sessionManager.setCacheManager(redisCacheManager());
        sessionManager.setSessionFactory(sessionFactory());
        //全局会话超时时间（单位毫秒），默认30分钟
        sessionManager.setGlobalSessionTimeout(this.sessionExpire * 1000);
        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //暂时设置为 5秒 用来测试
        sessionManager.setSessionValidationInterval(3600000);
        return sessionManager;
    }

    @Bean
    public ShiroSessionListener sessionListener() {
        return new ShiroSessionListener();
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        //配置记住我
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 配置保存sessionId的cookie
     * 注意：这里的cookie 不是上面的记住我 cookie 记住我需要一个cookie session管理 也需要自己的cookie
     * 默认为: JSESSIONID 问题: 与SERVLET容器名冲突,重新定义为sid
     *
     * @return
     */

    @Bean
    public SimpleCookie sessionIdCookie() {
        //这个参数是cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //setcookie的httponly属性如果设为true的话，会增加对xss防护的安全系数。它有以下特点：
        //setcookie()的第七个参数
        //设为true后，只能通过http访问，javascript无法访问
        //防止xss读取cookie
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("W2h5bXNdY29va2llQA=="));
        return cookieRememberMeManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/statics/**", "anon");
//        filterChainDefinitionMap.put("/js/**", "anon");
//        filterChainDefinitionMap.put("/css/**", "anon");
//        filterChainDefinitionMap.put("/img/**", "anon");
//        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/api/**", "anon");
        filterChainDefinitionMap.put("/index/app/**", "anon");
        filterChainDefinitionMap.put("/ad/**", "anon");
        filterChainDefinitionMap.put("/qzgz/web/**", "anon");
        filterChainDefinitionMap.put("/qzgz/upload/**", "anon");
        //AJAX登录判断
        filterChainDefinitionMap.put("/index/login", "anon");
        //登录验证码
        filterChainDefinitionMap.put("/index/getLoginCode", "anon");
        filterChainDefinitionMap.put("/index/logout", " logout");
        //恒拓app接入验证
        filterChainDefinitionMap.put("/index/app/workPlusLogin", "anon");
        //忘记密码设置到恒拓登录页，故需要开放访问
        filterChainDefinitionMap.put("/m/identity/passwordForgot.html", "anon");
        filterChainDefinitionMap.put("/app/identity/checkSendMdefVerCode", "anon");
        filterChainDefinitionMap.put("/app/identity/resetForgotPassword", "anon");
        filterChainDefinitionMap.put("/m/identity/passwordSuccess.html", "anon");
        //BO报表登录
        filterChainDefinitionMap.put("/bo/index/*", "anon");
        //恒拓对接开放
        filterChainDefinitionMap.put("/WPJoint/index/**", "anon");
        filterChainDefinitionMap.put("/unauthorized/**", "anon");

        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        //自定义加载权限资源关系
        List<SysPermission> resourcesList = permissionService.getAll();
        for (SysPermission resources : resourcesList) {
            if (StringUtil.isNotEmpty(resources.getPermission()) && StringUtil.isNotEmpty(resources.getUrl())) {
                String permission = "perms[" + resources.getPermission() + "]";
                filterChainDefinitionMap.put(resources.getUrl(), permission);
            }
        }

        filterChainDefinitionMap.put("/**", "authc");
        //测试开发都不拦截
//        filterChainDefinitionMap.put("/**", "anon");
        //使用自定义拦截器：authFilter主要用于拦截未登录请求，主动返回规范JSON;
//        filterChainDefinitionMap.put("/**", "authFilter,user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Filter Chain定义说明
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/index/login.html");
        shiroFilterFactoryBean.setSuccessUrl("/index/index.html");
        shiroFilterFactoryBean.setUnauthorizedUrl("/public/403.html");
        //logout自定义拦截
        shiroFilterFactoryBean.getFilters().put("logout", new ShiroLogoutFilter(this.sessionDAO));
        this.loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }


}
