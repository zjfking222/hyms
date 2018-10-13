package com.hy.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;

public class ShiroSessionFactory implements SessionFactory {
    @Override
    public Session createSession(SessionContext initData) {
        ShiroSession session = new ShiroSession();
        String host = initData.getHost();
        if (host != null) {
            session.setHost(host);
        }
        return session;
    }
}
