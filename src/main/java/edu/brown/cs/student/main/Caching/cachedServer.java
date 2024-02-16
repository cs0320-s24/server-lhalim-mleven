package edu.brown.cs.student.main.Caching;

import edu.brown.cs.student.main.Creators.CreateStringList;
import edu.brown.cs.student.main.FactoryFailureException;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class cachedServer {
    private static cachedSearch<List<String>> cachedSearch;

    public static void main(String[] args) {
        int port = 8989;
        Spark.port(port);

        // Enable CORS
        Spark.after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "*");
        });

        try {
            // Initialize CachedSearch instance
            FileReader reader = new FileReader("path/to/your/file.csv");
            cachedSearch = new cachedSearch<>(reader, new CreateStringList(), true);
        } catch (FactoryFailureException | FileNotFoundException e) {
            System.err.println("Error initializing CachedSearch: " + e.getMessage());
        }

        // Define route for search endpoint
        Spark.get("/search", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Get query parameters
                String target = request.queryParams("target");
                String indexStr = request.queryParams("index");
                String column = request.queryParams("column");

                if (target == null || target.isEmpty()) {
                    response.status(400);
                    return "Error: Missing target parameter";
                }

                // Perform search based on query parameters
                try {
                    if (indexStr != null && !indexStr.isEmpty()) {
                        int index = Integer.parseInt(indexStr);
                        return cachedSearch.search(target, index);
                    } else if (column != null && !column.isEmpty()) {
                        return cachedSearch.search(target, column);
                    } else {
                        return cachedSearch.search(target);
                    }
                } catch (NumberFormatException e) {
                    response.status(400);
                    return "Error: Invalid index parameter";
                }
            }
        });

        // Start Spark server
        Spark.awaitInitialization();

        //Printing a message to let the user know the server has been started
        System.out.println("Server started at http://localhost:" + port);
    }
}
