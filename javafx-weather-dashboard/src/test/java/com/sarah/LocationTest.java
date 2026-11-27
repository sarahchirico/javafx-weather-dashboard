package com.sarah;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LocationTest {

    @Test
    public void constructorStoresLocationValues() {
        // Arrange
        // Act
        Location location = new Location("Boston", "Massachusetts", "United States",
                42.3601, -71.0589, "America/New_York");

        // Assert
        assertEquals("Boston", location.getName());
        assertEquals("Massachusetts", location.getRegion());
        assertEquals("United States", location.getCountry());
        assertEquals(42.3601, location.getLatitude(), 0.0);
        assertEquals(-71.0589, location.getLongitude(), 0.0);
        assertEquals("America/New_York", location.getTimezone());
    }

    @Test
    public void settersUpdateLocationValues() {
        // Arrange
        Location location = new Location("Boston", "Massachusetts", "United States",
                42.3601, -71.0589, "America/New_York");

        // Act
        location.setName("Cambridge");
        location.setRegion("Cambridgeshire");
        location.setCountry("United Kingdom");
        location.setLatitude(52.2053);
        location.setLongitude(0.1218);
        location.setTimezone("Europe/London");

        // Assert
        assertEquals("Cambridge", location.getName());
        assertEquals("Cambridgeshire", location.getRegion());
        assertEquals("United Kingdom", location.getCountry());
        assertEquals(52.2053, location.getLatitude(), 0.0);
        assertEquals(0.1218, location.getLongitude(), 0.0);
        assertEquals("Europe/London", location.getTimezone());
    }

    @Test
    public void toStringIncludesNameRegionAndCountry() {
        // Arrange
        Location location = new Location("Paris", "Ile-de-France", "France",
                48.8566, 2.3522, "Europe/Paris");

        // Act
        String label = location.toString();

        // Assert
        assertEquals("Paris, Ile-de-France, France", label);
    }

    @Test
    public void toStringOmitsEmptyRegionAndCountry() {
        // Arrange
        Location location = new Location("Reykjavik", "", "", 64.1466, -21.9426,
                "Atlantic/Reykjavik");

        // Act
        String label = location.toString();

        // Assert
        assertEquals("Reykjavik", label);
    }
}
