package com.bas;

import com.bas.api_call.ApiCall;
import com.bas.api_response.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        ApiCall api = new ApiCall(0);

        Response response = new Response();

        int i = -1;

        do {
            System.out.println("running");

            Response temp = api.deserialiseGetAppListResponse(api.getResponse(api.setGetAppListRequest()));

            System.out.println(temp.getApps());

            response.setApps(temp.getApps());

            response.setLast_appid(temp.getLast_appid());

            System.out.println(response.getApps().get(0).getAppid());
            System.out.println(response.getLast_appid());
            i++;
        } while (i < 4);


//        api.deserialiseGetAppListResponse(api.getResponse(api.setGetAppListRequest()));
//
//        String url = "https://store.steampowered.com/api/appdetails?appids=10";
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println("Status code: " + response.statusCode());
//        System.out.println(response.body());

//        System.out.println(responses.get(0).getApps().get(0));

//        CharSequence start =  "{\"" + apps.get(0).getAppid() +"\":";
//        CharSequence end = "\"}}}}}";
//
//        String test = response.body().replace(start, "");
//        test = test.replace(end, "\"}}}}");
//
//        System.out.println(test);
    }
}
