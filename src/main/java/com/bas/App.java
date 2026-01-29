package com.bas;

import com.bas.api_call.ApiCall;
import com.bas.api_response.Response;
import com.bas.api_response.SteamApp;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        ApiCall api = new ApiCall(0);

        Response response = new Response();

        int i = -1;


        // Read in app ids
        do {
            System.out.println("Executing Request...");

            HttpRequest request = api.setGetAppListRequest();

            HttpResponse<String> httpResponse = api.getResponse(request);

            if (httpResponse.statusCode() == 200) {
                System.out.println("Request Successful.");
            } else {
                System.out.println("Something went wrong.");
                System.exit(1);
            }

            Response temp = api.deserialiseGetAppListResponse(httpResponse);

            response.setApps(temp.getApps());

            response.setLast_appid(temp.getLast_appid());

            i++;
        } while (response.getLast_appid() != 0);

        for (int j = 0; j < 40; j++) {
            int appid = response.getApps().get(j).getAppid();

            System.out.println("Executing Request...");

            HttpRequest request = api.setAppDetailsRequest(appid);

            HttpResponse<String> httpResponse = api.getResponse(request);

            if (httpResponse.statusCode() == 200) {
                System.out.println("Request Successful.");
            } else {
                System.out.println("Something went wrong.");
                System.exit(1);
            }

            SteamApp app = api.deserialiseAppDetailsResponse(httpResponse, appid);

            System.out.println("Name: " + app.getName());
            System.out.println("App id: " + app.getAppid());

            Thread.sleep(1500);
        }
    }
}
