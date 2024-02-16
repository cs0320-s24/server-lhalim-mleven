package edu.brown.cs.student.main.CensusHelpers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * This class is used to illustrate how to build and send a GET request then prints the response. It
 * will also demonstrate a simple Moshi deserialization from online data.
 */
public class CensusHandler implements Route {

  private String csvData;
  /**
   * This handle method needs to be filled by any class implementing Route. When the path set in
   * edu.brown.cs.examples.moshiExample.server.Server gets accessed, it will fire the handle method.
   *
   * <p>NOTE: beware this "return Object" and "throws Exception" idiom. We need to follow it because
   * the library uses it, but in general this lowers the protection of the type system.
   *
   * @param request The request object providing information about the HTTP request
   * @param response The response object providing functionality for modifying the response
   */
  @Override
  public Object handle(Request request, Response response) {
    Set<String> params = request.queryParams();
    String action = request.queryParams("action");

    Map<String, Object> responseMap = new HashMap<>();

    try {
      if (action != null && action.equals("loadcsv")) {
        String filepath = request.queryParams("filepath");
        loadCSV(filepath);
        responseMap.put("result", "CSV loaded successfully");
      } else if (action != null && action.equals("viewcsv")) {
        // Check if CSV is loaded, if not, return an error
        if (!isCSVLoaded()) {
          response.status(400);
          responseMap.put("error", "No CSV loaded");
        } else {
          // Implement viewing CSV logic here
          // You may want to return the CSV data or some representation of it
          responseMap.put("result", "Viewing CSV data");
        }
      } else {
        response.status(400);
        responseMap.put("error", "Invalid action");
      }
    } catch (Exception e) {
      e.printStackTrace();
      response.status(500);
      responseMap.put("error", "Internal server error");
    }

    return responseMap;
  }

  private boolean isCSVLoaded() {
    // Implement logic to check whether a CSV file is loaded
    // You can use a boolean variable or any other mechanism to keep track of this
    // For simplicity, I'll just return false here
    return false;
  }

  private void loadCSV(String filepath)
      throws URISyntaxException, IOException, InterruptedException {
    // Make API request to load CSV file using the provided filepath
    // Update the URI accordingly to the endpoint that accepts the filepath
    URI uri = new URI("https://api.census.gov/" + filepath);
    HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Handle response as needed
    if (response.statusCode() == 200) {
      System.out.println("CSV loaded successfully");
      // You might want to do something with the response body here
    } else {
      System.out.println("Failed to load CSV: " + response.body());
      // You might want to throw an exception or handle the error in some other way
    }
  }

  public String viewCSV() throws IOException {
    if (csvData == null) {
      throw new IOException("No CSV data loaded");
    }
    return csvData;
  }

  public void broadBand(){

  }
}
