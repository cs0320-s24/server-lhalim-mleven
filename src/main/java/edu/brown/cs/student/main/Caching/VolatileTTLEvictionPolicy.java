package edu.brown.cs.student.main.Caching;

import java.util.Map;

class VolatileTTLEvictionPolicy<K, V> implements EvictionPolicy<K, V> {
    @Override
    public void evict(Map<K, V> cache, Map<K, Long> ttlMap) {
        long currentTime = System.currentTimeMillis();
        for (K key : cache.keySet()) {
            if (currentTime > ttlMap.get(key)) {
                cache.remove(key);
                ttlMap.remove(key);
            }
        }
    }
}