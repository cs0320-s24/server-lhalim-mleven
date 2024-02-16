package edu.brown.cs.student.main.SearchHelpers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import edu.brown.cs.student.main.CensusHelpers.CensusHandler;
import edu.brown.cs.student.main.Creators.CreateStringList;
import java.io.StringReader;
import java.util.*;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * The SearchHandler class implements Route and
 */
public class SearchHandler implements Route {

  // Initialize a map for our informative response.
  Map<String, Object> parameterDict = new HashMap<>();

  /**
   * @param request The request object providing information about the HTTP request
   * @param response The response object providing functionality for modifying the response
   * @return return either a SearchSuccessResponse or a SearchNoDataFailureResponse
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {

    // Get Query parameters, can be used to make your search more specific
    String target = request.queryParams("target");
    String column = request.queryParams("columnName");
    boolean headerBool = Boolean.parseBoolean(request.queryParams("hasHeader"));
    String index = request.queryParams("index");

    // Populating dictionary with query parameters
    parameterDict.put("target", target);
    parameterDict.put("columnName", column);
    parameterDict.put("headerBool", headerBool);
    parameterDict.put("index", index);

    // If the target is not input, return an error
    if (target == "") {
      return new SearchNoDataFailureResponse("No search term was inputted.").serialize();
    }

    // Loading in the csvData as a string using the Census Handler
    CensusHandler handler = new CensusHandler();
    handler.handle(request, response);

    // Getting the data as a string
    String csvData = handler.viewCSV();

    try (StringReader reader = new StringReader(csvData)) {
      Search searcher = new Search(reader, new CreateStringList(), headerBool);

      // Initialise where the search result will be stored
      Collection<List<String>> foundRow;

      // Perform searches based off what information the user inputs
      if (column != null) {
        foundRow = searcher.search(target, column);
      } else if (index != null) {
        foundRow = searcher.search(target, index);
      } else {
        foundRow = searcher.search(target);
      }

      //If the target is not found, returns a message
      if(foundRow.isEmpty()){
        return new SearchSuccessResponse(
                foundRow, parameterDict.toString(), "The target: " + target + "was not found.")
                .serialize();
      }

      //Returning the rows that the target was found in
      return new SearchSuccessResponse(
              foundRow, parameterDict.toString(), "The target: " + target + " was found in " + foundRow.size() + " rows.")
          .serialize();
    } catch (RuntimeException e) { //throwing error if search process fails
      return new SearchNoDataFailureResponse("Error during search process. " + e.getMessage()).serialize();
    }
  }

  /** Response object to send if the search is conducted successfully **/
  public record SearchSuccessResponse(
      String result, Collection<List<String>> rows, String params, String message) {
    /**
     * Constructor
     *
     * @param rows the rows where the target was found
     * @param params parameters passed into the query
     * @param message a message to tell users how many places the target was found in
     */
    public SearchSuccessResponse(Collection<List<String>> rows, String params, String message) {
      this("success", rows, params, message);
    }

    String serialize() {
      try {
        // Initialize Moshi which takes in this class and returns it as JSON!
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<SearchSuccessResponse> adapter = moshi.adapter(SearchSuccessResponse.class);
        return adapter.toJson(this);
      } catch (Exception e) {
        // For debugging purposes, show in the console _why_ this fails
        // Otherwise we'll just get an error 500 from the API in integration
        // testing.
        e.printStackTrace();
        throw e;
      }
    }
  }

  /** Response object to send if there is an error in the search */
  public record SearchNoDataFailureResponse(String response_type) {
    public SearchNoDataFailureResponse() {
      this("error");
    }

    /**
     * @return this response, serialized as Json
     */
    String serialize() {
      Moshi moshi = new Moshi.Builder().build();
      return moshi.adapter(SearchNoDataFailureResponse.class).toJson(this);
    }
  }
}
