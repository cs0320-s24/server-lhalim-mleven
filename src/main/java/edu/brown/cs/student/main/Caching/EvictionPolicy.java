package edu.brown.cs.student.main.Caching;

import java.util.Map;

public interface EvictionPolicy<K, V> {
    void evict(Map<K, V> cache, Map<K, Long> ttlMap);
}
