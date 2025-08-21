package com.example.testcontainer1.configuration;

public interface AppConstants {
    public static String CONTROLLER_URL_PREFIX = "/api/v1";
    public static String ORDER_URI_PREFIX = "order";
    public static String ITEM_URI_PREFIX = "item";
    public static String ORDER_URL_PREFIX = CONTROLLER_URL_PREFIX + "/" + ORDER_URI_PREFIX;
    public static String ITEM_URL_PREFIX = CONTROLLER_URL_PREFIX + "/" + ITEM_URI_PREFIX;
    public static String NY_WEATHER_URL = "/gridpoints/OKX/34,38/forecast";

}
