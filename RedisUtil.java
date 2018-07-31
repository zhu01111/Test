package com.zwc.Boke.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
 
/**
 * Created by lightClouds917
 * Date 2018/1/18
 * Description:操作redis的工具类
 */
@Component
public class RedisUtil {
 
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    
    public static RedisUtil redisUtil;
    
    @PostConstruct
    public void init(){
    	redisUtil = this;
    	redisUtil.redisTemplate = this.redisTemplate;
    }
    
    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisUtil.redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
 
    /**
     * 写入缓存  指定时间  单位：秒
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisUtil.redisTemplate.opsForValue();
            operations.set(key, value);
            redisUtil.redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
 
    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisUtil.redisTemplate.hasKey(key);
    }
 
    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<String, Object> operations = redisUtil.redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
 
    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisUtil.redisTemplate.delete(key);
        }
    }
 
    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<String> keys = redisUtil.redisTemplate.keys(pattern);
        if (keys.size() > 0){
            redisUtil.redisTemplate.delete(keys);
        }
    }
 
    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }
}
