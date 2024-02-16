package edu.brown.cs.student.main.Caching;

import java.util.Map;

public interface Cache<K, V> {
    void put(K key, V value);
    V get(K key);
    void remove(K key);
    void clear();
    void setMaxSize(int maxSize);
    void setEvictionPolicy(EvictionPolicy<K, V> evictionPolicy);
}

class LRUCache<K, V> implements Cache<K, V> {
    // Implementation of LRUCache
}

interface EvictionPolicy<K, V> {
    void evict(Map<K, V> cache);
}

class BasicEvictionPolicy<K, V> implements EvictionPolicy<K, V> {
    // Implementation of basic eviction policy
}

class CustomEvictionPolicy<K, V> implements EvictionPolicy<K, V> {
    // Implementation of custom eviction policy
}
