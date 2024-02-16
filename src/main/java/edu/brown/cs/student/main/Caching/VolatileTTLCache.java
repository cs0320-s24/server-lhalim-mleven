package edu.brown.cs.student.main.Caching;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class VolatileTTLCache<K, V> implements Cache<K, V> {
    private final Map<K, V> cache;
    private final Map<K, Long> ttlMap; // Map to store TTL for each key
    private final Timer timer;
    private EvictionPolicy<K, V> evictionPolicy;
    private int maxSize;
    private long defaultTTL; // Default TTL in milliseconds

    public VolatileTTLCache() {
        this.cache = new HashMap<>();
        this.ttlMap = new HashMap<>();
        this.timer = new Timer();
        this.defaultTTL = 60000; // Default TTL: 60 seconds
        this.evictionPolicy = new VolatileTTLEvictionPolicy<>();
    }

    @Override
    public synchronized void put(K key, V value) {
        if (cache.size() >= maxSize) {
            evictionPolicy.evict(cache, ttlMap);
        }
        cache.put(key, value);
        ttlMap.put(key, System.currentTimeMillis() + defaultTTL);
        scheduleEviction(key, defaultTTL);
    }

    @Override
    public synchronized V get(K key) {
        V value = cache.get(key);
        if (value != null && System.currentTimeMillis() > ttlMap.get(key)) {
            remove(key); // Remove entry if TTL has expired
            return null;
        }
        return value;
    }

    @Override
    public synchronized void remove(K key) {
        cache.remove(key);
        ttlMap.remove(key);
    }

    @Override
    public synchronized void clear() {
        cache.clear();
        ttlMap.clear();
    }

    @Override
    public synchronized void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public synchronized void setEvictionPolicy(EvictionPolicy<K, V> evictionPolicy) {
        this.evictionPolicy = evictionPolicy;
    }

    @Override
    public boolean containsKey(K cacheKey) {
        return cache.containsKey(cacheKey);
    }

    // Schedule eviction of a key after TTL milliseconds
    private void scheduleEviction(K key, long ttl) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                remove(key);
            }
        }, ttl);
    }
}


