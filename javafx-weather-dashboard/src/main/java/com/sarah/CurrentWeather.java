package com.sarah;

/**
 * Represents the current weather conditions for a specified location.
 * Stores the weather condition, temperature, and whether it is currently 
 * daytime or nighttime.
 */
public class CurrentWeather {
    private WeatherCondition condition;
    private double temperature;
    private boolean isDay;

    /**
     * Constructs a CurrentWeather object with the specified weather information.
     * 
     * @param condition the current weather condition
     * @param temperature the current temperature
     * @param isDay true if it is daytime, false if it is nighttime
     */

    public CurrentWeather(WeatherCondition condition, double temperature, boolean isDay) {
        this.condition = condition;
        this.temperature = temperature;
        this.isDay = isDay;
    }

    /** 
     * Returns the current weather condition.
     * 
     * @return the current weather condition
     */
    public WeatherCondition getCondition() {
        return condition;
    }

    /** 
     * Updates the current weather condition.
     * 
     * @param condition the new weather condition
     */
    public void setCondition(WeatherCondition condition) {
        this.condition = condition;
    }

    /** 
     * Returns the current temperature.
     * 
     * @return the current temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /** 
     * Updates the current temperature.
     * 
     * @param temperature the new temperature
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /** 
     * Checks whether it is currently daytime.
     * 
     * @return true if it is daytime, false if it is nighttime
     */
    public boolean isDay() {
        return isDay;
    }

    /** 
     * Updates whether it is currently daytime or nighttime.
     * 
     * @param day true if it is daytime, false if it is nighttime
     */
    public void setDay(boolean day) {
        isDay = day;
    }
}
