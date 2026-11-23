package com.sarah;

public class CurrentWeather {
    private WeatherCondition condition;
    private double temperature;
    private boolean isDay;

    public CurrentWeather(WeatherCondition condition, double temperature, boolean isDay) {
        this.condition = condition;
        this.temperature = temperature;
        this.isDay = isDay;
    }

    public WeatherCondition getCondition() {
        return condition;
    }

    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public boolean isDay() {
        return isDay;
    }

    public void setDay(boolean day) {
        isDay = day;
    }
}
