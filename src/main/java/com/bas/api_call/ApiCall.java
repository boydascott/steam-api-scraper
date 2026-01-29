package com.bas.api_call;

import com.bas.api_response.Response;
import com.bas.api_response.SteamApp;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiCall {

    private int last_appid;

    public ApiCall(int last_appid) {
        this.last_appid = last_appid;
    }

    public int getLast_appid() {
        return last_appid;
    }

    public void setLast_appid(int last_appid) {
        this.last_appid = last_appid;
    }

    public HttpRequest setGetAppListRequest() {
        Dotenv dotenv = Dotenv.load();
        String url = "http://api.steampowered.com/IStoreService/GetAppList/v1/?key=" + dotenv.get("STEAM_API_KEY") + "&max_results=50000&last_appid=" + last_appid + "&format=json";
        return HttpRequest.newBuilder().uri(URI.create(url)).build();
    }

    public HttpRequest setAppDetailsRequest(int appid) {
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appid;
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

        String formattedResponse = response.body().replace(start, "");
        formattedResponse = formattedResponse.replace(end, "}");

        Gson gson = new Gson();
        Response deserialisedRepsonse = gson.fromJson(formattedResponse, Response.class);

        setLast_appid(deserialisedRepsonse.getLast_appid());

        return deserialisedRepsonse;
    }

    public String jsonFormatter (String response, int appid) {
        String temp = "";
        String newResponse = "";
        int i = 0;
        int brackets = 0;
        while (!temp.contains("{\"" + appid +"\":{\"success\":true,\"data\":")) {
            temp = temp.concat(response.charAt(i) + "");
            if (response.charAt(i) == '{') {
                brackets++;
            }
            i++;
        }

        for (int j = i; j < response.length()-brackets; j++) {
            newResponse = newResponse.concat(response.charAt(j)+"");
        }

        return newResponse;
    }

    public SteamApp deserialiseAppDetailsResponse(HttpResponse<String> httpResponse, int appid) {
        String formattedResponse = jsonFormatter(httpResponse.body(), appid);

        Gson gson = new GsonBuilder().setLenient().create();
        SteamApp app = gson.fromJson(formattedResponse, SteamApp.class);
        app.setAppid(appid);

        return app;
    }
}
