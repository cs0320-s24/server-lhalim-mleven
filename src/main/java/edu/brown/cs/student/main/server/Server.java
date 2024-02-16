package edu.brown.cs.student.main.server;

import edu.brown.cs.student.main.CensusHelpers.CensusHandler;
import edu.brown.cs.student.main.SearchHelpers.SearchHandler;
import spark.Spark;

import java.util.List;

import static spark.Spark.after;

/**
 * The server class contains the main() method which starts Spark and runs the handlers
 * Endpoint that allows users to load a CSV
 * Endpoint that allows users to view the loaded data
 * Endpoint that allows users to search for a target string within the loaded data
 * Endpoint that allows users to get information about broadband access for a specific state and county in the US
 */
public class Server {
  public static List<List<String>> loadedCSV;

  public static void main(String[] args) {
    int port = 8989;
    Spark.port(port);

    after(
            (request, response) -> {
              response.header("Access-Control-Allow-Origin", "*");
              response.header("Access-Control-Allow-Methods", "*");
            });

//    String dataAsJson = CensusAPIUtilities.deserializeCensus("");

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