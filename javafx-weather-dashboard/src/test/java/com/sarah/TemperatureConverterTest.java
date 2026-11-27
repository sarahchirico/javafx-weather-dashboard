package com.sarah;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TemperatureConverterTest {

    @Test
    public void toCelsiusConvertsFreezingPoint() {
        // Arrange
        TemperatureConverter converter = new TemperatureConverter();

        // Act
        double celsius = converter.toCelsius(32.0);

        // Assert
        assertEquals(0.0, celsius, 0.000001);
    }

    @Test
    public void toCelsiusConvertsNegativeTemperature() {
        // Arrange
        TemperatureConverter converter = new TemperatureConverter();

        // Act
        double celsius = converter.toCelsius(-40.0);

        // Assert
        assertEquals(-40.0, celsius, 0.000001);
    }

    @Test
    public void toFahrenheitConvertsBoilingPoint() {
        // Arrange
        TemperatureConverter converter = new TemperatureConverter();

        // Act
        double fahrenheit = converter.toFahrenheit(100.0);

        // Assert
        assertEquals(212.0, fahrenheit, 0.000001);
    }

    @Test
    public void toFahrenheitConvertsNegativeTemperature() {
        // Arrange
        TemperatureConverter converter = new TemperatureConverter();

        // Act
        double fahrenheit = converter.toFahrenheit(-40.0);

        // Assert
        assertEquals(-40.0, fahrenheit, 0.000001);
    }
}
