package edu.brown.cs.student.main.Caching;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A class that wraps a FileServer instance and caches responses
 * for efficiency. Notice that the interface hasn't changed at all.
 * This is an example of the proxy pattern; callers will interact
 * with the CachedFileServer, rather than the "real" data source.
 *
 * This version uses a Guava cache class to manage the cache.
 */
public class CachedSearch implements Searching<List<String>, String> {
    private final Searching<List<String>, String> wrappedSearch;
    private final LoadingCache<String, Collection<List<String>>> cache;

    /**
     * Proxy class: wrap an instance of Searcher (of any kind) and cache
     * its results.
     *
     * There are _many_ ways to implement this! We could use a plain
     * HashMap, but then we'd have to handle "eviction" ourselves.
     * Lots of libraries exist. We're using Guava here, to demo the
     * strategy pattern.
     *
     * @param toWrap the Searcher to wrap
     */
    public CachedSearch(Searching<List<String>,String> toWrap) {
        this.wrappedSearch = toWrap;

        // Look at the docs -- there are lots of builder parameters you can use
        //   including ones that affect garbage-collection (not needed for Server).
        this.cache = CacheBuilder.newBuilder()
                // How many entries maximum in the cache?
                .maximumSize(10)
                // How long should entries remain in the cache?
                .expireAfterWrite(1, TimeUnit.MINUTES)
                // Keep statistical info around for profiling purposes
                .recordStats()
                .build(
                        // Strategy pattern: how should the cache behave when
                        // it's asked for something it doesn't have?
                        new CacheLoader<>() {
                            @Override
                            public Collection<List<String>> load(String key)  {
                                System.out.println("called load for: "+key);
                                // If this isn't yet present in the cache, load it:
                                return wrappedSearch.search(key);
                            }
                        });
    }

    @Override
    public Collection<List<String>> search(String target) {
        // "get" is designed for concurrent situations; for today, use getUnchecked:
        Collection<List<String>> result = cache.getUnchecked(target);
        //System.out.println(cache.stats());
        return result;
    }

}
