package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.CensusHelpers.CensusHandler;
import edu.brown.cs.student.main.SearchHelpers.SearchHandler;
import spark.Spark;

import java.util.List;

import static spark.Spark.after;

public class Server {
  public static List<List<String>> loadedCSV;

  public static void main(String[] args) {
    // Defensive programming: Check for null arguments
    if (args == null) {
      System.err.println("Error: Null arguments provided.");
      return;
    }

    int port = 8989;

    // Defensive programming: Input validation for port number
    try {
      port = Integer.parseInt(args[0]);
    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
      System.err.println("Invalid port number provided. Using default port: " + port);
    }

    Spark.port(port);

    after(
            (request, response) -> {
              response.header("Access-Control-Allow-Origin", "*");
              response.header("Access-Control-Allow-Methods", "*");
            });

    //Starting up the different endpoints
    Spark.get("loadcsv", new CensusHandler());
    Spark.get("viewcsv", new CensusHandler());
    Spark.get("broadband", new CensusHandler());
    Spark.get("searchcsv", new SearchHandler());

    Spark.init();
    Spark.awaitInitialization();

    //Printing a message to let the user know the server has been started
    System.out.println("Server started at http://localhost:" + port);
  }
}
