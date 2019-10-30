package com.newlife.s4.config.shiro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

import com.newlife.s4.util.KryoUtils;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;

public class SessionRedisDao extends EnterpriseCacheSessionDAO {

    private static long TIME_OUT = 60 * 60 * 24 * 7 * 1800;

    private static final String SESSION_PREFIX = "session_id:";

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
//        RedisDb.setObject((SESSION_PREFIX + sessionId.toString()).getBytes(), sessionToByte(session));
        //当session创建时，会调用doUpdate(Session session)方法，所以这里可以不做RedisDb.setObject()
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = null;
        if (session == null) {
            byte[] bytes = RedisDb.getObject((SESSION_PREFIX + sessionId.toString()).getBytes());
            if (bytes != null && bytes.length > 0) {
                session = byteToSession(bytes);
            }
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        //避免微信端的请求产生无效的session
        if(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) != null){
        	RedisDb.setObject((SESSION_PREFIX + session.getId().toString()).getBytes(), sessionToByte(session));
        }
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        RedisDb.delString(SESSION_PREFIX + session.getId());
    }

    // 把session对象转化为byte保存到redis中
    public byte[] sessionToByte(Session session) {
        session.setTimeout(TIME_OUT);
        return KryoUtils.encode(session);
    }

    // 把byte还原为session
    public Session byteToSession(byte[] bytes) {

        return (Session) KryoUtils.decode(bytes);
    }

}