package com.sarah;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the complete weather forecast for a specified location.
 * Stores the location, current weather conditions, and a list of daily forecasts.
 */
public class WeatherForecast {
    private Location location;
    private CurrentWeather currentWeather;
    private List<DailyForecast> dailyForecasts;

    /**
     * Constructs a WeatherForecast object with the specified weather information.
     * 
     * @param location the location associated with the forecast
     * @param currentWeather the current weater conditions
     * @param dailyForecasts the list of daily forecasts
     */
    public WeatherForecast(Location location, CurrentWeather currentWeather,
                           List<DailyForecast> dailyForecasts) {
        this.location = location;
        this.currentWeather = currentWeather;
        setDailyForecasts(dailyForecasts);
    }

    /** 
     * Returns the location associated with the forecast.
     * 
     * @return the forecast location
     */
    public Location getLocation() {
        return location;
    }

    /** 
     * Updates the location associated with the forecast.
     * 
     * @param location the new forecast location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /** 
     * Returns the current weather conditions.
     * 
     * @return the current weather conditions
     */
    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    /** 
     * Updates the current weather conditions.
     * 
     * @param currentWeather the new current weather information
     */
    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    /** 
     * Returns an unmodifiable list of daily forecasts.
     * 
     * @return an unmodifiable list of DailyForecast objects
     */
    public List<DailyForecast> getDailyForecasts() {
        return Collections.unmodifiableList(dailyForecasts);
    }

    /** 
     * Updates the daily forecast by replacing the existing list with a copy of the provided list.
     * 
     * @param dailyForecasts the new list of daily forecasts
     */
    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = new ArrayList<>(dailyForecasts);
    }
}
