package com.bas.services;

import com.bas.model.Response;
import com.bas.model.SteamApp;
import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.*;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiCallService {

    private int last_appid;

    public HttpRequest setGetAppListRequest() {
        Dotenv dotenv = Dotenv.load();
        String url = "https://api.steampowered.com/IStoreService/GetAppList/v1/?key=" + dotenv.get("STEAM_API_KEY") + "&max_results=50000&last_appid=" + last_appid + "&format=json";
        return HttpRequest.newBuilder().uri(URI.create(url)).build();
    }

    public HttpRequest setAppDetailsRequest(int appid) {
        String url = "https://store.steampowered.com/api/appdetails?appids=" + appid;
        return HttpRequest.newBuilder().uri(URI.create(url)).build();
    }

    public HttpResponse<String> getResponse(HttpRequest request) {
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e);
        }

        assert response != null;
        System.out.println("Status code: " + response.statusCode());

        return response;
    }

    public Response deserialiseGetAppListResponse(HttpResponse<String> response) {
        CharSequence start =  "{\"response\":";
        CharSequence end = "}}";

        String formattedResponse = response.body().replace(start, "");
        formattedResponse = formattedResponse.replace(end, "}");

        Gson gson = new Gson();
        Response deserialisedResponse = gson.fromJson(formattedResponse, Response.class);

        setLast_appid(deserialisedResponse.getLast_appid());

        return deserialisedResponse;
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

        Gson gson = new Gson();
        SteamApp app = gson.fromJson(formattedResponse, SteamApp.class);
        app.setAppid(appid);

        return app;
    }

    public List<SteamApp> callApi (int last_appid) {
        Response response = new Response(new ArrayList<>(), 0);
        List<SteamApp> apps = new ArrayList<>();

        do {
            System.out.println("Executing Request...");

            HttpRequest request = setGetAppListRequest();

            HttpResponse<String> httpResponse = getResponse(request);

            if (httpResponse.statusCode() == 200) {
                System.out.println("Request Successful.");
            } else {
                System.out.println("Something went wrong.");
                System.exit(1);
            }

            Response temp = deserialiseGetAppListResponse(httpResponse);

            response.setApps(new ArrayList<>());

            response.setApps(temp.getApps());

            response.setLast_appid(temp.getLast_appid());
        } while (response.getLast_appid() != 0);

        for (int i = last_appid; i < (last_appid+200); i++) {
            int appid = response.getApps().get(i).getAppid();

            System.out.println("Executing Request...");

            HttpRequest request = setAppDetailsRequest(appid);

            HttpResponse<String> httpResponse = getResponse(request);

            if (httpResponse.statusCode() == 200) {
                System.out.println("Request Successful.");
            } else {
                System.out.println("Something went wrong.");
                System.exit(1);
            }

            SteamApp app = deserialiseAppDetailsResponse(httpResponse, appid);
            apps.add(app);

            System.out.println("Name: " + apps.get(i).getName());
            System.out.println("App id: " + apps.get(i).getAppid());

            try {
                Thread.sleep(1500);
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e);
            }
        }

        return apps;
    }
}
