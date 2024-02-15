package edu.brown.cs.student.main;

import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
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


        return null;
    }
}
