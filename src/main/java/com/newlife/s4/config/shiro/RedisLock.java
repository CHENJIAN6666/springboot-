package com.newlife.s4.config.shiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 描述: redis 线程锁
 *
 * @author withqianqian@163.com
 * @create 2018-11-06 16:45
 */
public class RedisLock {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //单位毫秒
    private final long lockTimeOut = 3000;

    private StringRedisTemplate redisTemplate;
    private String PRE_LOCK = "NX:";

    public RedisLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @return the lockTimeOut
     */
    public long getLockTimeOut() {
        return lockTimeOut;
    }

    private long perSleep;

    /**
     * 得不到锁立即返回，得到锁返回设置的超时时间
     *
     * @param key
     * @return
     */
    public Boolean tryLock(final String key) throws Exception{
        //得到锁后设置的过期时间，未得到锁返回False
        final Long expireTime = System.currentTimeMillis() + lockTimeOut + 1;
        String NX = PRE_LOCK + key;
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            if (connection.setNX(NX.getBytes(), String.valueOf(expireTime).getBytes())) {
                connection.expire(NX.getBytes(),6);
                return Boolean.TRUE;
            } else {
                byte[] curLockTimeStr = connection.get(NX.getBytes());
                if (curLockTimeStr != null && curLockTimeStr.length == 0) {
                    String curTimeStr = new String(curLockTimeStr);
                    //锁没过期
                    if (Long.valueOf(curTimeStr) < System.currentTimeMillis()) {
                        return Boolean.FALSE;
                    } else {
                        connection.del(NX.getBytes());
                        return Boolean.TRUE;
                    }

                }
                return Boolean.FALSE;
            }
        });

    }


}
