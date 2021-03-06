package com.youssif.joe.weatherapptask.Retrofit;

import com.youssif.joe.weatherapptask.Model.WeatherCitiesResult;
import com.youssif.joe.weatherapptask.Model.WeatherForecastResult;
import com.youssif.joe.weatherapptask.Model.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {

    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lng,
                                                 @Query("appid") String appid,
                                                 @Query("units") String unit);


    @GET("forecast")
    Observable<WeatherForecastResult> getForecastWeatherByLatLng(@Query("lat") String lat,
                                                                 @Query("lon") String lng,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String unit);


    @GET("group")
    Observable<WeatherCitiesResult> getCitiesWeatherByID(@Query("id") String id,
                                                         @Query("appid") String appid,
                                                         @Query("units") String unit);







}
