package edu.brown.cs.student.main.server;

import static spark.Spark.after;

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

    Spark.init();
    Spark.awaitInitialization();

    System.out.println("Server started at http://localhost:" + port);
  }
}
