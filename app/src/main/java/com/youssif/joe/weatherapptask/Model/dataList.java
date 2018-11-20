package com.youssif.joe.weatherapptask.Model;

import java.util.List;

public class dataList {

    public Coord coord ;
    public Sys sys ;
    public List<Weather> weather ;
    public Main main ;
    public int visibility ;
    public Wind wind ;
    public Clouds clouds ;
    public int dt ;
    public int id ;
    public String name ;

    public dataList() {
    }

    public Coord getCoord() {
        return coord;
    }

    public Sys getSys() {
        return sys;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public int getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public int getDt() {
        return dt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
