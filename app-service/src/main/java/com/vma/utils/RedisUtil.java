package com.vma.utils;


import com.vma.assist.wraps.ConfigWrap;
import com.vma.assist.wraps.LogWrap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Redis操作工具类
 *
 * @author huang
 */
public class RedisUtil {

    private static final Logger LOG = LogWrap.getLogger(RedisUtil.class);

    private static String host;
    private static Integer port;
    private static String password;
    private static Integer database;
    private static Duration timeout;
    private static Integer maxActive;
    private static Integer maxIdle;
    private static Duration maxWaitMillis;
    private static Integer minIdle;

    private static RedisUtil instance;
    private static JedisPool jedisPool = null;
    private static ReentrantLock lock = new ReentrantLock();

    //初始化redis连接池
    static {

        host = ConfigWrap.getStringValue("spring.redis.host");
        System.out.println(host);
        port = ConfigWrap.getIntegerValue("spring.redis.port");
        password = ConfigWrap.getStringValue("spring.redis.password");
        database = ConfigWrap.getIntegerValue("spring.redis.database");
        if (database == null) {
            database = 0;
        }
        if (StringUtils.isBlank(ConfigWrap.getStringValue("spring.redis.timeout"))) {
            timeout = Duration.parse("PT10s");
        } else {
            timeout = Duration.parse(ConfigWrap.getStringValue("spring.redis.timeout"));
        }
        maxActive = ConfigWrap.getIntegerValue("spring.redis.jedis.pool.max-active");
        if (maxActive == null) {
            maxActive = 100;
        }
        maxIdle = ConfigWrap.getIntegerValue("spring.redis.jedis.pool.max-idle");
        if (maxIdle == null) {
            maxIdle = 10;
        }
        if (StringUtils.isBlank(ConfigWrap.getStringValue("spring.redis.jedis.pool.max-wait"))) {
            maxWaitMillis = Duration.parse(ConfigWrap.getStringValue("spring.redis.jedis.pool.max-wait"));
        } else {
            maxWaitMillis = Duration.parse("PT10s");
        }
        minIdle = ConfigWrap.getIntegerValue("spring.redis.jedis.pool.min-idle");
        if (minIdle == null) {
            minIdle = 1;
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis.toMillis());
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPool = new JedisPool(jedisPoolConfig, host, port, (int) timeout.toMillis(), password);
    }

    /**
     * @return 返回jedis
     */
    public static synchronized Jedis getJedis() {
        if (jedisPool != null) {
            Jedis resource = jedisPool.getResource();
            return resource;
        } else {
            return null;
        }
    }

    /**
     * RedisUtil获取实例
     *
     * @return redisutil
     */
    public static RedisUtil getInstance() {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new RedisUtil();
            }
            lock.unlock();
        }
        return instance;
    }


    /**
     * 释放Jedis资源
     *
     * @param jedis jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 设置缓存值  无过期时间
     *
     * @param key key
     * @param value value
     * @return 是否成功
     */
    public static boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String ok = jedis.set(key, value);
            return "OK".equals(ok) ? true : false;
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }


    /**
     * 设置缓存值
     *
     * @param key key
     * @param value value
     * @param expireTime 单位秒
     * @return 是否成功
     */
    public static boolean set(String key, String value, Integer expireTime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String ok = jedis.set(key, value);
            if (null != expireTime) {
                jedis.expire(key, expireTime);
            }
            return "OK".equals(ok) ? true : false;
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }


    /**
     * 根据key获取缓存值
     *
     * @param key key
     * @return string
     */
    public static String get(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", e);
        } finally {
            returnResource(jedis);
        }
        return "";
    }

    /**
     * 根据key删除
     *
     * @param key key
     * @return 是否删除成功
     */
    public static boolean delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Long result = jedis.del(key);
            return result != 0 ? true : false;
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }


    /**
     * 根据pattern删除所有key
     *
     * @param pattern pattern
     */
    public static void delKeys(String pattern) {
        try {
            deletekeys(pattern + "_*");
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", e);
        }

    }

    /**
     * @param pattern pattern
     */
    private static synchronized void deletekeys(String pattern) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> keySet = jedis.keys(pattern);
            if (keySet == null || keySet.size() == 0) {
                return;
            }
            String[] keys = new String[keySet.size()];
            int i = 0;
            for (String key : keySet) {
                keys[i] = key;
                i++;
            }
            jedis.del(keys);
        } catch (JedisConnectionException e) {
            LOG.error("连接redis失败", e);
        } finally {
            returnResource(jedis);
        }


    }

}
