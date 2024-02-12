package edu.brown.cs.student.main;

import edu.brown.cs.student.main.Census;
import edu.brown.cs.student.main.CensusAPIUtilities;
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
// See Documentation here: https://www.boredapi.com/documentation
public class CensusHandler implements Route {
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
        String data = request.queryParams("data"); // example of query parameter, still to be amended

        // Creates a hashmap to store the results of the request
        Map<String, Object> responseMap = new HashMap<>();
        try {
            // Sends a request to the API and receives JSON back
            String censusJson = this.sendRequest(data);//add requests
            // Deserializes JSON into an Activity
            Census census = CensusAPIUtilities.deserializeCensus(censusJson);

            return responseMap;
        } catch (Exception e) { //needs better error handling
            e.printStackTrace();
            responseMap.put("result", "Exception");
        }
        return responseMap;
    }

    private String sendRequest(String data) // data needs to be altered to meet param request
            throws URISyntaxException, IOException, InterruptedException {

        HttpRequest buildCensusApiRequest =
                HttpRequest.newBuilder()
                        .uri(new URI("https://api.census.gov/"))
                        .GET()
                        .build();

        // Send that API request then store the response in this variable. Note the generic type.
        HttpResponse<String> sentCensusApiResponse =
                HttpClient.newBuilder()
                        .build()
                        .send(buildCensusApiRequest, HttpResponse.BodyHandlers.ofString());

        // What's the difference between these two lines? Why do we return the body? What is useful from
        // the raw response (hint: how can we use the status of response)?
        System.out.println(sentCensusApiResponse);
        System.out.println(sentCensusApiResponse.body());

        return sentCensusApiResponse.body();
    }
}
