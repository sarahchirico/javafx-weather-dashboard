package com.sarah;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class WeatherServiceTest {

    @Test
    public void nullLocationThrowsIllegalArgumentException() {
        // Arrange
        WeatherService service = new WeatherService();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.fetchForecast(null));
    }

    @Test
    public void parseForecastCreatesCompleteForecastFromValidJson() throws Exception {
        // Arrange
        WeatherService service = new WeatherService();
        Location location = new Location("Boston", "Massachusetts", "United States",
                42.3601, -71.0589, "America/New_York");
        String json = completeForecastJson();

        // Act
        WeatherForecast forecast = invokeParseForecast(service, json, location);

        // Assert
        assertEquals(location, forecast.getLocation());
        assertEquals(WeatherCondition.CLEAR, forecast.getCurrentWeather().getCondition());
        assertEquals(70.5, forecast.getCurrentWeather().getTemperature(), 0.0);
        assertTrue(forecast.getCurrentWeather().isDay());
        assertEquals(7, forecast.getDailyForecasts().size());
        assertEquals("2026-11-27", forecast.getDailyForecasts().get(0).getDate().toString());
    }

    @Test
    public void parseForecastThrowsIOExceptionForApiError() throws Exception {
        // Arrange
        WeatherService service = new WeatherService();
        Location location = new Location("Boston", "Massachusetts", "United States",
                42.3601, -71.0589, "America/New_York");
        String json = "{\"error\":true,\"reason\":\"Invalid coordinates\"}";

        // Act & Assert
        IOException exception = assertThrows(IOException.class,
                () -> invokeParseForecast(service, json, location));
        assertEquals("Weather API error: Invalid coordinates", exception.getMessage());
    }

    @Test
    public void parseForecastThrowsIOExceptionForIncompleteDailyData() throws Exception {
        // Arrange
        WeatherService service = new WeatherService();
        Location location = new Location("Boston", "Massachusetts", "United States",
                42.3601, -71.0589, "America/New_York");
        String json = completeForecastJson().replace(
                "\"time\":[\"2026-11-27\",\"2026-11-28\",\"2026-11-29\",\"2026-11-30\",\"2026-12-01\",\"2026-12-02\",\"2026-12-03\"]",
                "\"time\":[\"2026-11-27\"]");

        // Act & Assert
        IOException exception = assertThrows(IOException.class,
                () -> invokeParseForecast(service, json, location));
        assertTrue(exception.getMessage().contains("complete 7-day forecast"));
    }

    private WeatherForecast invokeParseForecast(WeatherService service, String json,
                                                Location location) throws Exception {
        try {
            Method method = WeatherService.class.getDeclaredMethod(
                    "parseForecast", String.class, Location.class);
            method.setAccessible(true);
            return (WeatherForecast) method.invoke(service, json, location);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof IOException) {
                throw (IOException) cause;
            }
            throw exception;
        }
    }

    private String completeForecastJson() {
        return "{"
                + "\"current\":{\"temperature_2m\":70.5,\"weather_code\":0,\"is_day\":1},"
                + "\"daily\":{\"time\":["
                + "\"2026-11-27\",\"2026-11-28\",\"2026-11-29\",\"2026-11-30\","
                + "\"2026-12-01\",\"2026-12-02\",\"2026-12-03\"],"
                + "\"weather_code\":[0,1,2,3,61,71,95],"
                + "\"temperature_2m_max\":[75,74,73,72,55,40,45],"
                + "\"temperature_2m_min\":[55,54,53,52,40,25,30],"
                + "\"precipitation_probability_max\":[10,20,30,40,60,70,80]},"
                + "\"hourly\":{\"time\":["
                + "\"2026-11-27T12:00\",\"2026-11-28T12:00\",\"2026-11-29T12:00\","
                + "\"2026-11-30T12:00\",\"2026-12-01T12:00\",\"2026-12-02T12:00\","
                + "\"2026-12-03T12:00\"],"
                + "\"temperature_2m\":[70.5,69,68,67,50,35,42],"
                + "\"precipitation_probability\":[10,20,30,40,60,70,80],"
                + "\"weather_code\":[0,1,2,3,61,71,95]}"
                + "}";
    }
}
