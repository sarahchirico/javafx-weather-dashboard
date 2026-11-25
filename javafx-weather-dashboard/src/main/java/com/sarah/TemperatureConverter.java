package com.sarah;

/**
 * Handles converting temperatures between Fahrenheit and Celsius.
 */
public class TemperatureConverter {
    /** 
     * Returns the temperature in Celsius.
     * 
     * @param fahrenheit the temperature in Fahrenheit
     * @return the temperature in Celsius
     */
    public double toCelsius(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    /** 
     * Returns the temperature in Fahrenheit
     * 
     * @param celsius the temperature in Celsius
     * @return the temperature in Fahrenheit
     */
    public double toFahrenheit(double celsius) {
        return celsius * 9.0 / 5.0 + 32.0;
    }
}
