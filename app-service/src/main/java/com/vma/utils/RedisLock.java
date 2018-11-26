package com.vma.utils;


import com.vma.assist.wraps.LogWrap;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * redis锁工具类
 *
 * @author huang
 */
public class RedisLock {


    private static final Logger LOGGER = LogWrap.getLogger(RedisUtil.class);

    private static final int DEFAULT_SINGLE_EXPIRE_TIME = 0;

    private static final int DEFAULT_BATCH_EXPIRE_TIME = 6;

    /**
     * 获取锁  如果锁可用   立即返回true，  否则返回false
     *
     * @param key key
     * @return 是否成功获取锁
     */
    public static boolean tryLock(String key) {
        return tryLock(key, 0L, null);
    }

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     *
     * @param key key
     * @param expireTime 有效期  秒
     * @return 是否成功获取锁
     */
    public static boolean tryLock(String key, int expireTime) {
        return tryLock(key, 0L, null, expireTime);
    }

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     *
     * @param key key
     * @param timeout 时间
     * @param unit 时间单位
     * @return 是否成功获取锁
     */
    public static boolean tryLock(String key, long timeout, TimeUnit unit) {
        return tryLock(key, timeout, unit, DEFAULT_SINGLE_EXPIRE_TIME);
    }

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     * @param key key
     * @param timeout 时间
     * @param unit 时间单位
     * @param expireTime expireTime
     * @return 是否成功获取锁
     */
    public static boolean tryLock(String key, long timeout, TimeUnit unit, int expireTime) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            long nano = System.nanoTime();
            do {
                System.out.println("try lock key: " + key);
                Long i = jedis.setnx(key, key);
                if (i == 1) {
                    if (expireTime > 0) {
                        jedis.expire(key, expireTime);
                    }
                    System.out.println("获取锁, key: " + key + " , expire in " + expireTime + " seconds.");
                    return Boolean.TRUE;
                } else { // 存在锁
                    String desc = jedis.get(key);
                    System.out.println("已被其他用户锁住key: " + key + " 已被其他用户锁住：" + desc);
                }
                if (timeout == 0) {
                    break;
                }
                Thread.sleep(10);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return Boolean.FALSE;
        } catch (JedisConnectionException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            RedisUtil.returnResource(jedis);
        }
        return Boolean.FALSE;
    }

    /**
     * 如果锁空闲立即返回   获取失败 一直等待
     *
     * @param key key
     */
    public static void lock(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            do {
                LOGGER.debug("lock key: " + key);
                Long i = jedis.setnx(key, key);
                if (i == 1) {
                    jedis.expire(key, DEFAULT_SINGLE_EXPIRE_TIME);
                    LOGGER.debug("get lock, key: " + key + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
                    return;
                } else {
                    if (LOGGER.isDebugEnabled()) {
                        String desc = jedis.get(key);
                        LOGGER.debug("key: " + key + " locked by another business：" + desc);
                    }
                }
                Thread.sleep(300);
            } while (true);
        } catch (JedisConnectionException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 释放锁
     *
     * @param key key
     */
    public static void unLock(String key) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.del(key);
            LOGGER.debug("释放锁release lock, keys :" + key);
        } catch (JedisConnectionException je) {
            LOGGER.error(je.getMessage(), je);
        } finally {
            RedisUtil.returnResource(jedis);
        }

    }

    /**
     * 批量获取锁  如果全部获取   立即返回true, 部分获取失败 返回false
     *
     * @param keyList keyList
     * @return 如果全部获取   立即返回true, 部分获取失败 返回false
     */
    public static boolean tryLock(List<String> keyList) {
        return tryLock(keyList, 0L, null);
    }

    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     *
     * @param keyList keyList
     * @param timeout timeout
     * @param unit unit
     * @return 获取锁成功 返回true， 否则返回false
     */
    public static boolean tryLock(List<String> keyList, long timeout, TimeUnit unit) {
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            List<String> needLocking = new CopyOnWriteArrayList<String>();
            List<String> locked = new CopyOnWriteArrayList<String>();
            long nano = System.nanoTime();
            do {
                // 构建pipeline，批量提交
                Pipeline pipeline = jedis.pipelined();
                for (String key : keyList) {
                    needLocking.add(key);
                    pipeline.setnx(key, key);
                }
                LOGGER.debug("try lock keys: " + needLocking);
                // 提交redis执行计数
                List<Object> results = pipeline.syncAndReturnAll();
                for (int i = 0; i < results.size(); ++i) {
                    Long result = (Long) results.get(i);
                    String key = needLocking.get(i);
                    if (result == 1) {    // setnx成功，获得锁
                        jedis.expire(key, DEFAULT_BATCH_EXPIRE_TIME);
                        locked.add(key);
                    }
                }
                needLocking.removeAll(locked);    // 已锁定资源去除
                if (CollectionUtils.isEmpty(needLocking)) {
                    return true;
                } else {
                    // 部分资源未能锁住
                    LOGGER.debug("keys: " + needLocking + " locked by another business：");
                }
                if (timeout == 0) {
                    break;
                }
                Thread.sleep(500);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));

            // 得不到锁，释放锁定的部分对象，并返回失败
            if (!CollectionUtils.isEmpty(locked)) {
                jedis.del(locked.toArray(new String[0]));
            }
            return false;
        } catch (JedisConnectionException | InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }

    /**
     * 批量释放锁
     *
     * @param keyList keyList
     */
    public static void unLock(List<String> keyList) {
        List<String> keys = new CopyOnWriteArrayList<>();
        for (String key : keyList) {
            keys.add(key);
        }
        Jedis jedis = null;
        try {
            jedis = RedisUtil.getJedis();
            jedis.del(keys.toArray(new String[0]));
            LOGGER.debug("释放锁release lock, keys :" + keys);
        } catch (JedisConnectionException je) {
            LOGGER.error(je.getMessage(), je);
        } finally {
            RedisUtil.returnResource(jedis);
        }
    }
}
