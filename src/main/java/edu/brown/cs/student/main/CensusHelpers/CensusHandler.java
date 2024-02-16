package edu.brown.cs.student.main.CensusHelpers;

import spark.Request;
import spark.Response;
import spark.Route;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CensusHandler implements Route {

  private String csvData;

  @Override
  public Object handle(Request request, Response response) {
    Set<String> params = request.queryParams();
    String action = request.queryParams("action");

    Map<String, Object> responseMap = new HashMap<>();

    try {
      if (action != null && action.equals("loadcsv")) {
        String filepath = request.queryParams("filepath");
        if (filepath == null || filepath.isEmpty()) {
          throw new IllegalArgumentException("Filepath is null or empty");
        }
        loadCSV(filepath);
        responseMap.put("result", "CSV loaded successfully");
      } else if (action != null && action.equals("viewcsv")) {
        if (!isCSVLoaded()) {
          response.status(400);
          responseMap.put("error", "No CSV loaded");
        } else {
          responseMap.put("result", "Viewing CSV data");
        }
      } else {
        response.status(400);
        responseMap.put("error", "Invalid action");
      }
    } catch (IllegalArgumentException e) {
      response.status(400);
      responseMap.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      response.status(500);
      responseMap.put("error", "Internal server error");
    }

    return responseMap;
  }

  private boolean isCSVLoaded() {
    return csvData != null; // Check if CSV data is loaded
  }

  public void loadCSV(String filepath)
          throws URISyntaxException, IOException, InterruptedException {
    if (filepath == null || filepath.isEmpty()) {
      throw new IllegalArgumentException("Filepath is null or empty");
    }
    // Make API request to load CSV file using the provided filepath
    URI uri = new URI("https://api.census.gov/" + filepath);
    HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();

    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Handle response as needed
    if (response.statusCode() == 200) {
      System.out.println("CSV loaded successfully");
      csvData = response.body(); // Store CSV data
    } else {
      System.out.println("Failed to load CSV: " + response.body());
      throw new IOException("Failed to load CSV: " + response.body());
    }
  }

  public String viewCSV() throws IOException {
    if (csvData == null) {
      throw new IOException("No CSV data loaded");
    }
    return csvData;
  }

  public void broadband(){

  }
}
