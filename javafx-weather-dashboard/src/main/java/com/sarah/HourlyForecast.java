package com.sarah;

import java.time.LocalDateTime;

/**
 * Represents the weather forecast for a specific hour.
 * Stores the forecasted time, temperature, precipitation probability, and weather condition.
 */
public class HourlyForecast {
    private LocalDateTime time;
    private double temperature;
    private int precipitationProbability;
    private WeatherCondition condition;

    /**
     * Constructs an HourlyForecast object with the specified weather information.
     * 
     * @param time the forecasted time
     * @param temperature the forecasted temperature
     * @param precipitationProbability the forecasted precipitation probability
     * @param condition the forecasted weather condition
     */
    public HourlyForecast(LocalDateTime time, double temperature,
                          int precipitationProbability, WeatherCondition condition) {
        this.time = time;
        this.temperature = temperature;
        this.precipitationProbability = precipitationProbability;
        this.condition = condition;
    }

    /** 
     * Returns the forecasted time.
     * 
     * @return the forecasted time
     */
    public LocalDateTime getTime() {
        return time;
    }

    /** 
     * Updates the forecasted time.
     * 
     * @param time the new forecasted time
     */
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    /** 
     * Returns the forecasted temperature.
     * 
     * @return the forecasted temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /** 
     * Updates the forecasted temperature.
     * 
     * @param temperature the new forecasted temperature
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
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
}
