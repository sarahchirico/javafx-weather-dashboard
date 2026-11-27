package com.sarah;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertSame;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class WeatherForecastTest {

    @Test
    public void constructorStoresForecastValues() {
        // Arrange
        Location location = new Location("Boston", "Massachusetts", "United States",
                42.3601, -71.0589, "America/New_York");
        CurrentWeather current = new CurrentWeather(WeatherCondition.CLEAR, 72.0, true);
        DailyForecast daily = createDailyForecast();
        List<DailyForecast> dailyForecasts = Arrays.asList(daily);

        // Act
        WeatherForecast forecast = new WeatherForecast(location, current, dailyForecasts);

        // Assert
        assertSame(location, forecast.getLocation());
        assertSame(current, forecast.getCurrentWeather());
        assertEquals(dailyForecasts, forecast.getDailyForecasts());
    }

    @Test
    public void settersUpdateForecastValues() {
        // Arrange
        Location originalLocation = new Location("Boston", "", "United States",
                42.3601, -71.0589, "America/New_York");
        CurrentWeather originalCurrent = new CurrentWeather(WeatherCondition.CLEAR, 72.0, true);
        WeatherForecast forecast = new WeatherForecast(
                originalLocation, originalCurrent, new ArrayList<>());
        Location newLocation = new Location("Paris", "Ile-de-France", "France",
                48.8566, 2.3522, "Europe/Paris");
        CurrentWeather newCurrent = new CurrentWeather(WeatherCondition.RAIN, 55.0, false);

        // Act
        forecast.setLocation(newLocation);
        forecast.setCurrentWeather(newCurrent);
        forecast.setDailyForecasts(Arrays.asList(createDailyForecast()));

        // Assert
        assertSame(newLocation, forecast.getLocation());
        assertSame(newCurrent, forecast.getCurrentWeather());
        assertEquals(1, forecast.getDailyForecasts().size());
    }

    @Test
    public void dailyForecastListIsDefensiveAndUnmodifiable() {
        // Arrange
        List<DailyForecast> source = new ArrayList<>();
        source.add(createDailyForecast());
        WeatherForecast forecast = new WeatherForecast(
                new Location("Boston", "", "United States", 42.3601, -71.0589,
                        "America/New_York"),
                new CurrentWeather(WeatherCondition.CLEAR, 72.0, true),
                source);

        // Act
        source.clear();

        // Assert
        assertEquals(1, forecast.getDailyForecasts().size());
        assertThrows(UnsupportedOperationException.class,
                () -> forecast.getDailyForecasts().clear());
    }

    private DailyForecast createDailyForecast() {
        HourlyForecast hourly = new HourlyForecast(
                LocalDateTime.of(2026, 11, 27, 12, 0),
                68.0,
                20,
                WeatherCondition.PARTLY_CLOUDY);
        return new DailyForecast(
                LocalDate.of(2026, 11, 27),
                WeatherCondition.PARTLY_CLOUDY,
                75.0,
                58.0,
                20,
                Arrays.asList(hourly));
    }
}
