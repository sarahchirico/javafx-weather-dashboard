package com.sarah;

import java.time.LocalDateTime;

public class HourlyForecast {
    private LocalDateTime time;
    private double temperature;
    private int precipitationProbability;
    private WeatherCondition condition;

    public HourlyForecast(LocalDateTime time, double temperature,
                          int precipitationProbability, WeatherCondition condition) {
        this.time = time;
        this.temperature = temperature;
        this.precipitationProbability = precipitationProbability;
        this.condition = condition;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }
}
