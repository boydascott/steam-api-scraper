package com.bas;

import com.bas.services.ApiCallService;
import com.bas.model.SteamApp;

import java.io.IOException;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws IOException, InterruptedException {
        ApiCallService api = new ApiCallService(0);

        List<SteamApp> apps = api.callApi(0);

        apps.forEach(steamApp -> {
            System.out.println(steamApp.getName());
            System.out.println(steamApp.getAppid());
        });
    }
}
