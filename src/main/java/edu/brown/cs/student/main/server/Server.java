package edu.brown.cs.student.main.server;

import static spark.Spark.after;

import java.util.List;

import edu.brown.cs.student.main.SearchHelpers.SearchHandler;
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


    SearchHandler search = new SearchHandler();

//    Spark.post("loadcsv", );
//    Spark.get("viewcsv", );
//    Spark.post("broadband", );
    Spark.post("searchcsv", search);


    Spark.init();
    Spark.awaitInitialization();

    System.out.println("Server started at http://localhost:" + port);
  }
}
