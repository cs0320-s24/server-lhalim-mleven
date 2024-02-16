package edu.brown.cs.student.main.Caching;

import edu.brown.cs.student.main.Creators.CreatorFromRow;
import edu.brown.cs.student.main.FactoryFailureException;
import edu.brown.cs.student.main.Parser;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class cachedSearch<T> {

    private List<List<String>> parsedData;
    private boolean hasHeaders;
    private Parser parser;
    private Cache<String, Collection<List<T>>> cache; // Cache for search results

    public cachedSearch(Reader inputReader, CreatorFromRow inputCreator, boolean headersBool) throws FactoryFailureException {
        if (inputReader == null || inputCreator == null) {
            throw new IllegalArgumentException("Input reader or creator cannot be null");
        }
        this.parser = new Parser<>(inputReader, inputCreator, headersBool);
        this.parsedData = parser.read();
        this.hasHeaders = headersBool;
        this.cache = new VolatileTTLCache<>(); // Initialize cache
    }

    // Search using a target and index
    public Collection<List<T>> search(String target, int index) {
        if (index < 0 || index >= parsedData.get(0).size()) {
            throw new IllegalArgumentException("Index is out of bounds");
        }

        String cacheKey = generateCacheKey(target, index);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey); // Return cached result if available
        }

        Collection<List<T>> found = new ArrayList<>();
        for (List<String> row : this.parsedData) {
            if (row.get(index).equals(target)) {
                System.out.println(row);
                found.add((List<T>) row);
            }
        }

        if (found.isEmpty()) {
            System.err.println("Target not found");
        }

        cache.put(cacheKey, found); // Cache the result
        return found;
    }

    // Search using a target and column name
    public Collection<List<T>> search(String target, String column) {
        int index = this.parser.getHeader().indexOf(column);
        if (index == -1) {
            System.err.println("Column not found");
            return new ArrayList<>(); // Return empty collection
        }

        return search(target, index); // Delegate to index-based search
    }

    // Search using just a target
    public Collection<List<T>> search(String target) {
        Collection<List<T>> found = new ArrayList<>();
        for (int row = 0; row < this.parsedData.size(); row++) {
            for (int col = 0; col < this.parsedData.get(row).size(); col++) {
                String current = this.parsedData.get(row).get(col);
                if (target.equals(current)) {
                    List<String> foundRow = this.parsedData.get(row);
                    System.out.println(foundRow);
                    found.add((List<T>) foundRow);
                }
            }
        }

        if (found.isEmpty()) {
            System.err.println("Target not found");
        }

        return found;
    }

    // Generate a unique cache key for the given search query
    private String generateCacheKey(String target, int index) {
        return target + "_" + index;
    }
}
