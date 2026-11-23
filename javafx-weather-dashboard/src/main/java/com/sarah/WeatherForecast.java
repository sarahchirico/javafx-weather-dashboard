package com.sarah;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeatherForecast {
    private Location location;
    private CurrentWeather currentWeather;
    private List<DailyForecast> dailyForecasts;

    public WeatherForecast(Location location, CurrentWeather currentWeather,
                           List<DailyForecast> dailyForecasts) {
        this.location = location;
        this.currentWeather = currentWeather;
        setDailyForecasts(dailyForecasts);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public List<DailyForecast> getDailyForecasts() {
        return Collections.unmodifiableList(dailyForecasts);
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = new ArrayList<>(dailyForecasts);
    }
}
