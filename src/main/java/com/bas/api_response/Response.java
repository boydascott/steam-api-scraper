package com.bas.api_response;

import java.util.ArrayList;

public class Response {

    private ArrayList<SteamApp> apps;
    private int last_appid;

    public Response () {
        this.apps = new ArrayList<>();
    }

    public ArrayList<SteamApp> getApps() {
        return apps;
    }

    public void setApps(ArrayList<SteamApp> apps) {
        this.apps.addAll(apps);
    }

    public int getLast_appid() {
        return last_appid;
    }

    public void setLast_appid(int last_appid) {
        this.last_appid = last_appid;
    }
}
