package com.sarah;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DailyForecastTest {

    @Test
    public void constructorStoresDailyForecastValues() {
        // Arrange
        LocalDate date = LocalDate.of(2026, 11, 27);
        WeatherCondition condition = WeatherCondition.PARTLY_CLOUDY;
        HourlyForecast hour = new HourlyForecast(
                LocalDateTime.of(2026, 11, 27, 12, 0), 68.0, 20, WeatherCondition.PARTLY_CLOUDY);
        List<HourlyForecast> hourlyForecasts = Arrays.asList(hour);

        // Act
        DailyForecast forecast = new DailyForecast(date, condition, 75.0, 58.0, 30,
                hourlyForecasts);

        // Assert
        assertEquals(date, forecast.getDate());
        assertSame(condition, forecast.getCondition());
        assertEquals(75.0, forecast.getHighTemperature(), 0.0);
        assertEquals(58.0, forecast.getLowTemperature(), 0.0);
        assertEquals(30, forecast.getPrecipitationProbability());
        assertEquals(hourlyForecasts, forecast.getHourlyForecasts());
    }

    @Test
    public void settersUpdateDailyForecastValues() {
        // Arrange
        DailyForecast forecast = new DailyForecast(
                LocalDate.of(2026, 11, 27), WeatherCondition.CLEAR, 75.0, 58.0, 30,
                new ArrayList<>());

        // Act
        forecast.setDate(LocalDate.of(2026, 11, 28));
        forecast.setCondition(WeatherCondition.SNOW);
        forecast.setHighTemperature(40.0);
        forecast.setLowTemperature(25.0);
        forecast.setPrecipitationProbability(70);

        List<HourlyForecast> replacement = new ArrayList<>();
        replacement.add(new HourlyForecast(
                LocalDateTime.of(2026, 11, 28, 9, 0), 32.0, 65, WeatherCondition.SNOW));
        forecast.setHourlyForecasts(replacement);

        // Assert
        assertEquals(LocalDate.of(2026, 11, 28), forecast.getDate());
        assertEquals(WeatherCondition.SNOW, forecast.getCondition());
        assertEquals(40.0, forecast.getHighTemperature(), 0.0);
        assertEquals(25.0, forecast.getLowTemperature(), 0.0);
        assertEquals(70, forecast.getPrecipitationProbability());
        assertEquals(replacement, forecast.getHourlyForecasts());
    }

    @Test
    public void hourlyForecastListIsDefensiveAndUnmodifiable() {
        // Arrange
        List<HourlyForecast> source = new ArrayList<>();
        HourlyForecast hour = new HourlyForecast(
                LocalDateTime.of(2026, 11, 27, 12, 0), 68.0, 20, WeatherCondition.CLEAR);
        source.add(hour);
        DailyForecast forecast = new DailyForecast(
                LocalDate.of(2026, 11, 27), WeatherCondition.CLEAR, 75.0, 58.0, 20, source);

        // Act
        source.clear();

        // Assert
        assertEquals(1, forecast.getHourlyForecasts().size());
        assertThrows(UnsupportedOperationException.class,
                () -> forecast.getHourlyForecasts().clear());
    }
}
