package com.hy.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.concurrent.atomic.AtomicInteger;

public class ShiroSessionListener implements SessionListener {
    /**
     * 统计在线人数
     * juc包下线程安全自增
     */

    private final AtomicInteger sessionCount = new AtomicInteger(0);

    @Override

    /**
     * 会话创建时触发
     * @param session
     */

    public void onStart(Session session) {
        sessionCount.incrementAndGet();
    }

    /**
     * 退出会话时触发
     *
     * @param session
     */

    @Override
    public void onStop(Session session) {
        sessionCount.decrementAndGet();
    }

    /**
     * 会话过期时触发
     *
     * @param session
     */
    @Override
    public void onExpiration(Session session) {
        sessionCount.decrementAndGet();

    }

    /**
     * 获取在线人数使用
     *
     * @return
     */
    public AtomicInteger getSessionCount() {
        return sessionCount;
    }
}
