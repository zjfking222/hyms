package com.hy.config.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RedisCacheManager implements CacheManager {
    /**
     * expire time in seconds
     */
    @Value("${spring.shiro.redis.cacheExpire:1800}")
    private int expire;
    /**
     * fast lookup by name map
     */
    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache cache = caches.get(name);
        if (cache == null) {
            cache = new ShiroCache<K, V>(redisTemplate,name, expire);
            caches.put(name, cache);
        }
        return cache;
    }

}
