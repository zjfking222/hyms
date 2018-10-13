package com.hy.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

public class MySessionIdGenerator implements SessionIdGenerator {

    private String name;

    public MySessionIdGenerator(String name) {
        this.name = name;
    }

    @Override
    public Serializable generateId(Session session) {
        Serializable uuid = new JavaUuidSessionIdGenerator().generateId(session);
        return uuid;
    }
}
