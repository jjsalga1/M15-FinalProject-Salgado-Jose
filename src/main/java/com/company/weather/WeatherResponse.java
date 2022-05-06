package com.company.weather;

import java.util.List;

/**
 * The {@code WeatherResponse} class is an object that gets created inside the
 * WeatherResponseAPI class. Variables represent the JSON response's key-value
 * pairs.
 */
public class WeatherResponse {
    public Coord coord;
    public List<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public long dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}
