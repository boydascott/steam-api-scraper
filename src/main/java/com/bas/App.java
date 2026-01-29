package com.bas;

import com.bas.api_call.ApiCall;
import com.bas.api_response.Response;
import com.bas.api_response.SteamApps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        ApiCall api = new ApiCall(0);

        List<Response> responses = new ArrayList<>();

        int i = -1;


        do {
            i++;
            Response response = api.deserialiseResponse(api.getResponse(api.setRequest()));
            responses.add(response);
        } while (responses.get(i).getResponse().getLast_appid() != 0);


    }
}
