package edu.brown.cs.student.main.SearchHelpers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.brown.cs.student.main.Creators.CreateStringList;
import spark.Request;
import spark.Response;
import spark.Route;

public class SearchHandler implements Route {

  // Initialize a map for our informative response.
  Map<String, Object> parameterDict = new HashMap<>();

  /**
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @Override
  public Object handle(Request request, Response response) throws Exception {

    // Get Query parameters, can be used to make your search more specific
    String target = request.queryParams("target");
    String column = request.queryParams("columnName");
    boolean headerBool = Boolean.parseBoolean(request.queryParams("hasHeader"));
    int index = Integer.parseInt(request.queryParams("index"));

    //Populating dictionary with query parameters
    parameterDict.put("target", target);
    parameterDict.put("columnName", column);
    parameterDict.put("headerBool", headerBool);
    parameterDict.put("index", index);

    if (target == "") {
      return new SearchNoDataFailureResponse("No search term inputted.").serialize();
    }

    try (FileReader reader = new FileReader("test.csv")) {
      Search searcher = new Search(reader, new CreateStringList(), headerBool);

      Collection<List<String>> foundRow = null;

      //Perform searches
      if(column != null) {
        foundRow = searcher.search(target, column);
      }
      else if (index == 0) {
        foundRow = searcher.search(target, index);
      }
      else {
        foundRow = searcher.search(target);
      }
      return new SearchSuccessResponse("error", parameterDict).serialize();


    }


    return new SearchNoDataFailureResponse().serialize();

  }





  public record SearchSuccessResponse(String response_type, Map<String, Object> responseMap) {
    public SearchSuccessResponse(Map<String, Object> responseMap) {
      this("success", responseMap);
    }
    /**
     * @return this response, serialized as Json
     */
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

  /** Response object to send if someone requested a search to empty data */
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
