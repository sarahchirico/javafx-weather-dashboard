package com.sarah;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

public class GeocodingServiceTest {

    @Test
    public void nullSearchQueryThrowsIllegalArgumentException() {
        // Arrange
        GeocodingService service = new GeocodingService();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.searchLocations(null));
    }

    @Test
    public void blankSearchQueryThrowsIllegalArgumentException() {
        // Arrange
        GeocodingService service = new GeocodingService();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.searchLocations("   "));
    }

    @Test
    public void parseLocationsCreatesLocationFromValidJson() throws Exception {
        // Arrange
        GeocodingService service = new GeocodingService();
        String json = "{"
                + "\"results\":[{\"name\":\"Boston\",\"admin1\":\"Massachusetts\","
                + "\"country\":\"United States\",\"latitude\":42.3601,"
                + "\"longitude\":-71.0589,\"timezone\":\"America/New_York\"}]"
                + "}";

        // Act
        List<Location> locations = invokeParseLocations(service, json);

        // Assert
        assertEquals(1, locations.size());
        assertEquals("Boston", locations.get(0).getName());
        assertEquals("Massachusetts", locations.get(0).getRegion());
        assertEquals("United States", locations.get(0).getCountry());
        assertEquals(42.3601, locations.get(0).getLatitude(), 0.0);
        assertEquals(-71.0589, locations.get(0).getLongitude(), 0.0);
        assertEquals("America/New_York", locations.get(0).getTimezone());
    }

    @Test
    public void parseLocationsReturnsEmptyListWhenResultsAreMissing() throws Exception {
        // Arrange
        GeocodingService service = new GeocodingService();

        // Act
        List<Location> locations = invokeParseLocations(service, "{}");

        // Assert
        assertEquals(0, locations.size());
    }

    @Test
    public void parseLocationsThrowsIOExceptionForApiError() throws Exception {
        // Arrange
        GeocodingService service = new GeocodingService();
        String json = "{\"error\":true,\"reason\":\"Invalid location query\"}";

        // Act & Assert
        IOException exception = assertThrows(IOException.class,
                () -> invokeParseLocations(service, json));
        assertEquals("Geocoding API error: Invalid location query", exception.getMessage());
    }

    @SuppressWarnings("unchecked")
    private List<Location> invokeParseLocations(GeocodingService service, String json)
            throws Exception {
        try {
            Method method = GeocodingService.class.getDeclaredMethod("parseLocations", String.class);
            method.setAccessible(true);
            return (List<Location>) method.invoke(service, json);
        } catch (InvocationTargetException exception) {
            Throwable cause = exception.getCause();
            if (cause instanceof IOException) {
                throw (IOException) cause;
            }
            throw exception;
        }
    }
}
