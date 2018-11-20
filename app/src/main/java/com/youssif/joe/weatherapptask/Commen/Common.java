package com.youssif.joe.weatherapptask.Commen;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static final String APP_ID = "d51f03a139172ee658b9dbe8d201f9f1";
    public static String lat = "";
    public static String lon = "";

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a, dd EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToHour(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        String formatted = sdf.format(date);
        return formatted;
    }
}
