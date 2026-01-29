package com.bas.api_call;

import com.bas.api_response.Response;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiCall {

    private int last_appid;
    private final String apikey;

    public ApiCall(int last_appid) {
        Dotenv dotenv = Dotenv.load();
        this.last_appid = last_appid;
        this.apikey = dotenv.get("STEAM_API_KEY");
    }

    public int getLast_appid() {
        return last_appid;
    }

    public void setLast_appid(int last_appid) {
        this.last_appid = last_appid;
    }

    public HttpRequest setGetAppListRequest() {
        String url = "http://api.steampowered.com/IStoreService/GetAppList/v1/?key=" + apikey + "&max_results=50000&last_appid=" + last_appid + "&format=json";
        return HttpRequest.newBuilder().uri(URI.create(url)).build();
    }

    public HttpResponse<String> getResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String>  response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: " + response.statusCode());

        return response;
    }

    public Response deserialiseGetAppListResponse(HttpResponse<String> response) {
        CharSequence start =  "{\"response\":";
        CharSequence end = "}}";

        String test = response.body().replace(start, "");
        test = test.replace(end, "}");

        Gson gson = new Gson();

        Response deserialisedRepsonse = gson.fromJson(test, Response.class);

        setLast_appid(deserialisedRepsonse.getLast_appid());

        return deserialisedRepsonse;
    }
}
