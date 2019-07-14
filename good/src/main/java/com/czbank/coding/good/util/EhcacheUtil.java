package com.czbank.coding.good.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class EhcacheUtil {

    @Resource
    private CacheManager cacheManager;

    public Object get(Object key, String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache == null) {
            return null;
        }
        Element element = cache.get(key);
        if (element == null) {
            return null;
        }
        return element.getObjectValue();
    }



    public void set(Object key, Object v, String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            log.info("ehcache set");
            Element element = new Element(key, v);
            cache.put(element);
        }
    }

    public void del(Object key, String cacheName) {
        Cache cache = getCache(cacheName);
        if (cache != null) {
            cache.remove(key);
        }
    }

    private Cache getCache(String cacheName) {
        EhCacheCacheManager cacheCacheManager = (EhCacheCacheManager) cacheManager;
        net.sf.ehcache.CacheManager ehCacheManager = cacheCacheManager.getCacheManager();
        return ehCacheManager.getCache(cacheName);
    }
}
