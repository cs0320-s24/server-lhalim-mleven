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

