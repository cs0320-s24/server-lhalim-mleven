package edu.brown.cs.student.main.SearchHelpers;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchHandler implements Route {

    private List<List<String>> csvInfo;

    /**
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        Set<String> params = request.queryParams();
        String data = request.queryParams("data");

        // Get Query parameters, can be used to make your search more specific
        String target = request.queryParams("target");




        // Initialize a map for our informative response.
        Map<String, Object> responseMap = new HashMap<>();

        responseMap.put("target", target);



//        Search searcher = new Search()



        return null;
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
