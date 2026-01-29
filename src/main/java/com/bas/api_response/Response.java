package com.bas.api_response;

import java.util.List;

public class Response {

    private Results response;

    public Results getResponse () {
        return response;
    }

    public List<SteamApps> getApps() {
        return response.getApps();
    }

    public void setResponse(Results response) {
        this.response = response;
    }
}
