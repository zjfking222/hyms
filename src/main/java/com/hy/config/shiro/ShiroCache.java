package com.hy.config.shiro;

import io.netty.util.internal.StringUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ShiroCache<K, V> implements Cache<K, V> {

    private static Logger logger = LoggerFactory.getLogger(ShiroCache.class);

    private static final String DEFAULT_CACHE_KEY_PREFIX = "hymsCache:";
    private String keyPrefix = DEFAULT_CACHE_KEY_PREFIX;

    private RedisTemplate<K, V> redisTemplate;

    private int expire;

    public ShiroCache(RedisTemplate redisTemplate, String name, int expire) {
        this.redisTemplate = redisTemplate;
        if (!StringUtil.isNullOrEmpty(name))
            this.keyPrefix += name + ":";
        this.expire = expire;
    }

    @Override
    public V get(K k) throws CacheException {
        logger.debug("获取缓存:{}", k);
        return redisTemplate.opsForValue().get(getkeyPrefix(k));
    }

    /***
     * 设置过期时间
     * @param k
     */
    public void expire(K k) {
        logger.debug("更新缓存时间:{}", k);
        if (redisTemplate.hasKey(getkeyPrefix(k))) {
            redisTemplate.expire(getkeyPrefix(k), this.expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public V put(K k, V v) throws CacheException {
        logger.debug("创建缓存:{}", k);
        V old = get(k);
        redisTemplate.opsForValue().set(getkeyPrefix(k), v, this.expire, TimeUnit.SECONDS);
        return old;
    }

    @Override
    public V remove(K k) throws CacheException {
        logger.debug("删除缓存:{}", k);
        V old = get(k);
        redisTemplate.delete(getkeyPrefix(k));
        return old;
    }

    @Override
    public void clear() throws CacheException {
        logger.debug("清空缓存");
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        return this.keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getkeyPrefix("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> keys = this.keys();
        List<V> list = new ArrayList<>();
        for (K k : keys) {
            list.add(this.get(k));
        }
        return list;
    }

    private K getkeyPrefix(Object k) {
        return (K) (this.keyPrefix + k);
    }

}
