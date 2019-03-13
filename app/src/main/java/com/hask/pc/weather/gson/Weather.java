package com.hask.pc.weather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    public Update update;

    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
