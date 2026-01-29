package com.bas.api_response;

import com.bas.App;

import java.util.List;

public class Results {

    private List<SteamApps> apps;
    private int last_appid;

    public List<SteamApps> getApps() {
        return apps;
    }

    public void setApps(List<SteamApps> apps) {
        this.apps = apps;
    }

    public int getLast_appid() {
        return last_appid;
    }

    public void setLast_appid(int last_appid) {
        this.last_appid = last_appid;
    }
}
