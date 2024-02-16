package edu.brown.cs.student;

import edu.brown.cs.student.main.Caching.Cache;
import edu.brown.cs.student.main.Caching.VolatileTTLCache;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class testCaching {

    private Cache<String, List<String>> cache;

    @Before
    public void setUp() {
        // Initialize cache before each test
        cache = new VolatileTTLCache<>();
        cache.setMaxSize(3); // Set maximum cache size
    }

    @Test
    public void testCachePutAndGet() {
        // Put some entries into the cache
        List<String> value1 = List.of("Value1");
        List<String> value2 = List.of("Value2");
        cache.put("Key1", value1);
        cache.put("Key2", value2);

        // Retrieve entries from the cache
        assertEquals(value1, cache.get("Key1"));
        assertEquals(value2, cache.get("Key2"));
    }

    @Test
    public void testCacheEviction() {
        // Put more entries than the cache size limit
        cache.put("Key1", List.of("Value1"));
        cache.put("Key2", List.of("Value2"));
        cache.put("Key3", List.of("Value3"));
        cache.put("Key4", List.of("Value4")); // This should trigger eviction

        // Check that the oldest entry has been evicted
        assertNull(cache.get("Key1"));
    }

    @Test
    public void testCacheTTL() throws InterruptedException {
        // Put an entry with TTL of 1 second
        List<String> value = List.of("Value");
        cache.put("Key", value);

        // Wait for 2 seconds (TTL should expire)
        Thread.sleep(2000);

        // The entry should have been evicted due to TTL expiration
        assertNull(cache.get("Key"));
    }

    @Test
    public void testCacheClear() {
        // Put some entries into the cache
        cache.put("Key1", List.of("Value1"));
        cache.put("Key2", List.of("Value2"));

        // Clear the cache
        cache.clear();

        // Check that the cache is empty
        assertNull(cache.get("Key1"));
        assertNull(cache.get("Key2"));
    }
}

