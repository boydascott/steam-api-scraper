package com.bas.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SteamApp {
    private int appid;
    private String name;
    private String type;
    private String detailed_description;
    private String header_image;
    private ArrayList<String> developers;
    private ArrayList<String> publishers;
    private ArrayList<Genre> genres;
}