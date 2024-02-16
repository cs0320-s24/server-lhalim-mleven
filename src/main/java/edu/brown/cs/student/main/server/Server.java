package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import edu.brown.cs.student.main.CensusHelpers.CensusAPIUtilities;
import edu.brown.cs.student.main.CensusHelpers.CensusHandler;
import edu.brown.cs.student.main.SearchHelpers.SearchHandler;
import java.util.List;
import spark.Spark;

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


    Spark.get("loadcsv", new CensusHandler());
    Spark.get("viewcsv", new CensusHandler());
    Spark.get("broadband", new CensusHandler());
    Spark.get("searchcsv", new SearchHandler());

    Spark.init();
    Spark.awaitInitialization();

    System.out.println("Server started at http://localhost:" + port);
  }
}
