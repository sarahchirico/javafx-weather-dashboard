package com.sarah;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DailyForecast {
    private LocalDate date;
    private WeatherCondition condition;
    private double highTemperature;
    private double lowTemperature;
    private int precipitationProbability;
    private List<HourlyForecast> hourlyForecasts;

    public DailyForecast(LocalDate date, WeatherCondition condition, double highTemperature,
                         double lowTemperature, int precipitationProbability,
                         List<HourlyForecast> hourlyForecasts) {
        this.date = date;
        this.condition = condition;
        this.highTemperature = highTemperature;
        this.lowTemperature = lowTemperature;
        this.precipitationProbability = precipitationProbability;
        setHourlyForecasts(hourlyForecasts);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    public double getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(double highTemperature) {
        this.highTemperature = highTemperature;
    }

    public double getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(double lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public List<HourlyForecast> getHourlyForecasts() {
        return Collections.unmodifiableList(hourlyForecasts);
    }

    public void setHourlyForecasts(List<HourlyForecast> hourlyForecasts) {
        this.hourlyForecasts = new ArrayList<>(hourlyForecasts);
    }
}
