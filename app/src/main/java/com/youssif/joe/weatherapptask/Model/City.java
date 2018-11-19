package com.youssif.joe.weatherapptask.Model;

public class City {

    public int id ;
    public String name;
    public Coord coord;
    public String country ;

    public City() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }
}
