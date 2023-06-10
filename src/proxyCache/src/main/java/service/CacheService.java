package service;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class CacheService {

    private static CacheService cacheService;
    private Cache<String, String[]> cache;

    public static CacheService getInstance() {
        if (cacheService == null) {
            cacheService = new CacheService();
        }
        return cacheService;
    }

    private CacheService() {
        this.cache = CacheBuilder.newBuilder().maximumSize(10).expireAfterWrite(30, TimeUnit.SECONDS).build();
    }

    public Cache<String, String[]> getCache() {
        return cache;
    }

    public void setCache(Cache<String, String[]> cache) {
        this.cache = cache;
    }

    public String[] getElementFromCache(String key) {
        return cache.getIfPresent(key);
    }

    public void putElementInCache(String key, String[] value) {
        cache.put(key, value);
    }
}
