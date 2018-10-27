package com.hy.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.CacheManagerAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Collection;
import java.util.Collections;

/**
 * 缓存功能的会话维护
 */
@Component
public class ShiroSessionDAO extends AbstractSessionDAO implements CacheManagerAware {
    private static Logger logger = LoggerFactory.getLogger(ShiroSessionDAO.class);

    @Value("${spring.shiro.session.cachePrefix:sessionCache}")
    private String keyPrefix;

    private CacheManager cacheManager;
    private Cache<Serializable, Session> sessionCache;

    public ShiroSessionDAO() {
    }

    @Override
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    private Cache<Serializable, Session> getActiveSessionsCacheLazy() {
        if (this.sessionCache == null) {
            this.sessionCache = this.createActiveSessionsCache();
        }
        return this.sessionCache;
    }

    protected Cache<Serializable, Session> createActiveSessionsCache() {
        Cache<Serializable, Session> cache = null;
        CacheManager mgr = this.cacheManager;
        if (this.cacheManager != null) {
            cache = mgr.getCache(this.keyPrefix);
        }
        return cache;
    }

    @Override
    protected Serializable doCreate(Session session) {
        if (session == null) {
            throw new UnknownSessionException("session is null");
        }
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.cache(session, sessionId);
        return sessionId;
    }

    private void cache(Session session, Serializable sessionId) throws UnknownSessionException {
        if (session != null && sessionId != null) {
            Cache<Serializable, Session> cache = this.getActiveSessionsCacheLazy();
            if (cache != null) {
                cache.put(this.getRedisSessionKey(sessionId), session);
            }
        }
    }


    private void expire(Serializable sessionId) throws UnknownSessionException {
        if (sessionId == null)
            return;
        ShiroCache<Serializable, Session> cache = (ShiroCache) this.getActiveSessionsCacheLazy();
        if (cache != null) {
            cache.expire(sessionId);
        }
    }

    protected void uncache(Session session) {
        if (session == null || session.getId() == null)
            return;

        Serializable sessionId = session.getId();
        Cache<Serializable, Session> cache = this.getActiveSessionsCacheLazy();
        if (cache != null) {
            cache.remove(this.getRedisSessionKey(sessionId));
        }
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = null;
        if (sessionId != null) {
            Cache<Serializable, Session> cache = this.getActiveSessionsCacheLazy();
            if (cache != null) {
                session = (Session) cache.get(this.getRedisSessionKey(sessionId));
            }
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            this.uncache(session);
            return;
        }
        if (session instanceof ShiroSession) {
            // 如果没有主要字段(除lastAccessTime以外其他字段)发生改变,刷新缓存
            ShiroSession ss = (ShiroSession) session;
            if (!ss.isChanged()) {
                this.expire(session.getId());
                return;
            }
            //如果没有返回 证明有调用 setAttribute往redis 放的时候永远设置为false
            ss.setChanged(false);
        }
        this.cache(session, session.getId());
    }

    @Override
    public void delete(Session session) {
        try {
            this.uncache(session);
        } catch (Exception e) {
            logger.error("delete session error. session id= {}", session.getId());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Cache<Serializable, Session> cache = this.getActiveSessionsCacheLazy();
        return (Collection) (cache != null ? cache.values() : Collections.emptySet());
    }

    private String getRedisSessionKey(Serializable sessionId) {
        return sessionId.toString();
    }

}
