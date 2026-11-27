package com.sarah;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.time.LocalDateTime;

import org.junit.Test;

public class HourlyForecastTest {

    @Test
    public void constructorStoresHourlyForecastValues() {
        // Arrange
        LocalDateTime time = LocalDateTime.of(2026, 11, 27, 15, 30);
        WeatherCondition condition = WeatherCondition.RAIN;

        // Act
        HourlyForecast forecast = new HourlyForecast(time, 48.5, 80, condition);

        // Assert
        assertEquals(time, forecast.getTime());
        assertEquals(48.5, forecast.getTemperature(), 0.0);
        assertEquals(80, forecast.getPrecipitationProbability());
        assertSame(condition, forecast.getCondition());
    }

    @Test
    public void settersUpdateHourlyForecastValues() {
        // Arrange
        HourlyForecast forecast = new HourlyForecast(
                LocalDateTime.of(2026, 11, 27, 15, 30), 48.5, 80, WeatherCondition.RAIN);

        // Act
        forecast.setTime(LocalDateTime.of(2026, 11, 27, 16, 30));
        forecast.setTemperature(50.0);
        forecast.setPrecipitationProbability(25);
        forecast.setCondition(WeatherCondition.CLOUDY);

        // Assert
        assertEquals(LocalDateTime.of(2026, 11, 27, 16, 30), forecast.getTime());
        assertEquals(50.0, forecast.getTemperature(), 0.0);
        assertEquals(25, forecast.getPrecipitationProbability());
        assertEquals(WeatherCondition.CLOUDY, forecast.getCondition());
    }
}
