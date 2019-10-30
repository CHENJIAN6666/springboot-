package com.newlife.s4.config.shiro;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDb {


    private static StringRedisTemplate redisTemplate;

    public RedisDb() { }

    @Autowired
    public RedisDb(StringRedisTemplate redisTemplate2) {
        redisTemplate = redisTemplate2;
    }

    // session 在redis过期时间是30分钟30*60
    private static int expireTime = 60 * 60 * 24 * 7;

    private static int countExpireTime = 2 * 24 * 3600;

    private static final String SMS_ID = "sms";

    private static final String WECHAT_MSG = "wechat_msg";

    private static final String FOR_CONSULTANT = "for_consultant:";

    private static final String WECHAT_AVATAR_DOWNLOAD_QUEUE = "wechat_avatar_download_queue";

    public static final String PENDING_ACTIVATION_CODE = "pending_activation_code";

    private static long BLOCK_TIME = 3;

    private static Logger logger = LoggerFactory.getLogger(RedisDb.class);

    // 保存字符串数据
    public static void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 获取字符串类型的数据
    public static String getString(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除字符串数据
    public static void delString(String key) {
        redisTemplate.delete(key);
    }

    // 保存byte类型数据
    public static void setObject(final byte[] key, final byte[] value) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.set(key, value);
                redisConnection.expire(key, expireTime);
                return Boolean.TRUE;
            }
        });
    }

    // 获取byte类型数据
    public static byte[] getObject(final byte[] key) {
        return redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.get(key);

            }
        });
    }

    // 更新byte类型的数据，主要更新过期时间
    public static void updateObject(final byte[] key) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.expire(key, expireTime);
                return Boolean.TRUE;
            }
        });
    }

    // key对应的整数value加1
    public static void inc(final String key) {
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.incrBy(key.getBytes(), 1);
                redisConnection.expire(key.getBytes(), countExpireTime);
                return Boolean.TRUE;
            }
        });

    }

    // 获取所有keys
    public static Set<String> getAllKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 设置k/v的过期时间
     *
     * @param key
     * @param value
     * @param seconds
     */
    public static void setString(String key, String value, int seconds) {
        redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
    }

    /**
     * 更新k/v的过期时间
     *
     * @param key
     * @param milliseconds
     */
    public static void pexpire(final String key, final long milliseconds) {
        redisTemplate.expire(key, milliseconds, TimeUnit.SECONDS);
    }

    //====================采用基于java 内存的阻塞队列

    /**
     * 基于redis的队列 存储带发送的消息
     *
     * @param value
     */
    @Deprecated
    public static void pushSendSms(final String ...value) {
        redisTemplate.opsForList().rightPushAll(SMS_ID,value);
    }


    public static void pushWechatAvatarDownloadQueue(final String ...value) {
        pushList(WECHAT_AVATAR_DOWNLOAD_QUEUE,value);
    }

    public static void pushList(final String key ,final String ...value) {
        redisTemplate.opsForList().rightPushAll(key,value);
    }

    public static void pushList(final String key ,final Collection collection) {
        redisTemplate.opsForList().rightPushAll(key,collection);
    }

    /**
     * 基于redis的队列 存储带发送的消息
     *
     */
    @Deprecated
    public static String popSendSms() {
        return redisTemplate.opsForList().leftPop(SMS_ID,BLOCK_TIME,TimeUnit.HOURS);
    }

    public static String popWechatAvatarDownloadQueue() {
        return popList(WECHAT_AVATAR_DOWNLOAD_QUEUE);
    }

    public static String popList(final String key) {
        return redisTemplate.opsForList().leftPop(key,BLOCK_TIME,TimeUnit.HOURS);
    }

    public static String popListNoBlock(final String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public static Long listSize(final String key){
        return redisTemplate.opsForList().size(key);
    }

    public static Long WechatAvatarDownloadQueueSize(){
        return listSize(WECHAT_AVATAR_DOWNLOAD_QUEUE);
    }

    /**
     * 基于redis的队列 存储带发送的消息
     *
     * @param value
     */
    public static void pushWehcatMsg(String ...value) {
        redisTemplate.opsForList().rightPushAll(WECHAT_MSG,value);
    }

    /**
     * 基于redis的队列 存储带发送的消息
     *
     */
    public static String popWechatMsg() {
        return redisTemplate.opsForList().leftPop(WECHAT_MSG,BLOCK_TIME,TimeUnit.HOURS);
    }

    /**
     * 扫码关注销售顾问
     *
     * @param userID
     * @param value
     */
    public static void pushMember(Long userID, String value,long ts) {
        redisTemplate.opsForValue().set(FOR_CONSULTANT + userID + ":" + ts, value, 30, TimeUnit.SECONDS);
    }

    /**
     * 获取扫码成功的会员
     * @param userID
     */
    public static JSONObject popMembers(String userID,long...ts) {
        JSONObject jsonObject = null;
        String member =  redisTemplate.opsForValue().get(FOR_CONSULTANT + userID + ":" + ts[0]);
        if(StringUtils.isNotEmpty(member)){
            jsonObject = JSONObject.parseObject(member);
            redisTemplate.delete(FOR_CONSULTANT + userID + ":" + ts[0]);
        }
        return jsonObject;
    }

    /**
     * 添加到redis set数据结构中
     * @param key
     * @param val
     */
    public static void addRedisSet(String key,String... val){
        redisTemplate.opsForSet().add(key,val);
    }

    /**
     * 随机从redis中pop出一个值
     * @param key
     * @return
     */
    public static String popRedisSet(String key){
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 获取set的长度
     * @param key
     * @return
     */
    public static Long getRedisSetSize(String key){
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 根据Key获取整个set
     * @param key
     * @return
     */
    public static Set<String> getRedisSet(String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 删除set中的值
     * @param key
     * @return
     */
    public static void delRedisSet(String key,String... val){
         redisTemplate.opsForSet().remove(key,val);
    }

    /**
     * 判断key是否操作redis中
     *
     * @param key
     * @return
     */
    public static Boolean existKey(String key) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.exists(key.getBytes());
            }
        });
    }

    public static Object getHashKey(String key, String field) {
        Object value = redisTemplate.opsForHash().get(key, field);
        if (value == null)
            value = "";
        return value;
    }
    public static void addHashKey(String key,String field) {
        redisTemplate.opsForHash().put(key, field, "0");
    }
    public static void addHashKey(String key,String field,String v) {
        redisTemplate.opsForHash().put(key, field, v);
    }
    public static Long getHashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    public static Boolean lock(String key){
        RedisLock redisLock = new RedisLock(redisTemplate);
        try {
            return redisLock.tryLock(key);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return Boolean.FALSE;
    }
}