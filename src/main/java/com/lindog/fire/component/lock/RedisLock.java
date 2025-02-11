package com.lindog.fire.component.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 自定义redis分布式锁（redission配置）
 */
@Slf4j
@Component
public class RedisLock {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static final String LOCK_PREFIX = "lock:";
    
    /**
     * 尝试获取分布式锁
     * @param lockKey 锁的key
     * @param value 锁的值（通常使用requestId或线程id，用于识别锁的持有者）
     * @param timeout 超时时间（秒）
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String value, long timeout) {
        try {
            String key = LOCK_PREFIX + lockKey;
            Boolean success = redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
            log.debug("Try lock key: {}, value: {}, result: {}", key, value, success);
            return success != null && success;
        } catch (Exception e) {
            log.error("Try lock error", e);
            return false;
        }
    }

    /**
     * 释放分布式锁
     * @param lockKey 锁的key
     * @param value 锁的值，必须与加锁时的值相同
     * @return 是否释放成功
     */
    public boolean releaseLock(String lockKey, String value) {
        try {
            String key = LOCK_PREFIX + lockKey;
            String currentValue = redisTemplate.opsForValue().get(key);
            if (value.equals(currentValue)) {
                Boolean result = redisTemplate.delete(key);
                log.debug("Release lock key: {}, value: {}, result: {}", key, value, result);
                return result != null && result;
            }
            return false;
        } catch (Exception e) {
            log.error("Release lock error", e);
            return false;
        }
    }
} 