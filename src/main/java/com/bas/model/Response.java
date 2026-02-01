package com.bas.model;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Response {

    private ArrayList<SteamApp> apps;
    private int last_appid;

    public void setApps(ArrayList<SteamApp> apps) {
        for (SteamApp app : apps) {
            this.apps.add(app);
        }
    }
}
