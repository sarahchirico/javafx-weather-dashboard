package com.sarah;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CurrentWeatherTest {

    @Test
    public void constructorStoresCurrentWeatherValues() {
        // Arrange
        WeatherCondition condition = WeatherCondition.CLEAR;

        // Act
        CurrentWeather weather = new CurrentWeather(condition, 72.5, true);

        // Assert
        assertSame(condition, weather.getCondition());
        assertEquals(72.5, weather.getTemperature(), 0.0);
        assertTrue(weather.isDay());
    }

    @Test
    public void settersUpdateCurrentWeatherValues() {
        // Arrange
        CurrentWeather weather = new CurrentWeather(WeatherCondition.CLEAR, 72.5, true);

        // Act
        weather.setCondition(WeatherCondition.RAIN);
        weather.setTemperature(55.25);
        weather.setDay(false);

        // Assert
        assertEquals(WeatherCondition.RAIN, weather.getCondition());
        assertEquals(55.25, weather.getTemperature(), 0.0);
        assertFalse(weather.isDay());
    }
}
