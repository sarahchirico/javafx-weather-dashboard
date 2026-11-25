package com.sarah;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the weather forecast for an individual day for a specified location.
 * Stores the forecasted date, weather condition, high/low temperature, precipitation probability,
 * and a list of HourlyForecast objects.
 */
public class DailyForecast {
    private LocalDate date;
    private WeatherCondition condition;
    private double highTemperature;
    private double lowTemperature;
    private int precipitationProbability;
    private List<HourlyForecast> hourlyForecasts;

    /**
     * Constructs a DailyForecast object with the specified weather information.
     * 
     * @param date the date of the forecast
     * @param condition the forecasted weather condition
     * @param highTemperature the forecasted high temperature
     * @param lowTemperature the forecasted low temperature
     * @param precipitationProbability the forecasted precipitation probability
     * @param hourlyForecasts the hourly forecasts for the day
     */
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

    /** 
     * Returns the forecasted date.
     * 
     * @return the forecasted date
     */
    public LocalDate getDate() {
        return date;
    }

    /** 
     * Updates the date.
     * 
     * @param date the new forecasted date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /** 
     * Returns the forecasted weather condition.
     * 
     * @return the forecasted weather condition
     */
    public WeatherCondition getCondition() {
        return condition;
    }

    /** 
     * Updates the forecasted weather condition.
     * 
     * @param condition the new forecasted weather condition
     */
    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    /** 
     * Returns the forecasted high temperature.
     * 
     * @return the forecasted high temperature
     */
    public double getHighTemperature() {
        return highTemperature;
    }

    /** 
     * Updates the forecasted high temperature.
     * 
     * @param highTemperature the new forecasted high temperature
     */
    public void setHighTemperature(double highTemperature) {
        this.highTemperature = highTemperature;
    }

    /** 
     * Returns the forecasted low temperature.
     * 
     * @return the forecasted low temperature
     */
    public double getLowTemperature() {
        return lowTemperature;
    }

    /** 
     * Updates the forecasted low temperature.
     * 
     * @param lowTemperature the new forecasted low temperature
     */
    public void setLowTemperature(double lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    /** 
     * Returns the forecasted precipitation probability.
     * 
     * @return the forecasted precipitation probability
     */
    public int getPrecipitationProbability() {
        return precipitationProbability;
    }

    /** 
     * Updates the forecasted precipitation probability.
     * 
     * @param precipitationProbability the new forecasted precipitation probability
     */
    public void setPrecipitationProbability(int precipitationProbability) {
        this.precipitationProbability = precipitationProbability;
    }

    /** 
     * Returns an unmodifiable list of hourly forecasts.
     * 
     * @return an unmodifiable list of HourlyForecast objects
     */
    public List<HourlyForecast> getHourlyForecasts() {
        return Collections.unmodifiableList(hourlyForecasts);
    }

    /** 
     * Updates the hourly forecast by replacing the existing list with a copy of the provided list.
     * 
     * @param hourlyForecasts the new list of hourly forecasts
     */
    public void setHourlyForecasts(List<HourlyForecast> hourlyForecasts) {
        this.hourlyForecasts = new ArrayList<>(hourlyForecasts);
    }
}
