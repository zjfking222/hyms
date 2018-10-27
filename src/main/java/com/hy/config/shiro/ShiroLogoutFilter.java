package com.hy.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Locale;

public class ShiroLogoutFilter extends LogoutFilter {
    private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);

    private ShiroSessionDAO sessionDAO;

    public ShiroLogoutFilter(ShiroSessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = this.getSubject(request, response);
        if (this.isPostOnlyLogout() && !WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH).equals("POST")) {
            return this.onLogoutRequestNotAPost(request, response);
        } else {
            String redirectUrl = this.getRedirectUrl(request, response, subject);
            try {
                Session session = subject.getSession();
                request.removeAttribute(session.getId().toString());
                sessionDAO.delete(session);
                subject.logout();
            } catch (SessionException var6) {
                log.debug("Encountered session exception during logout.  This can generally safely be ignored.", var6);
            }

            this.issueRedirect(request, response, redirectUrl);
            return false;
        }
    }
}
