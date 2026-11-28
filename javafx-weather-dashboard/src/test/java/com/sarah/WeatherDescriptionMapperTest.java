package com.sarah;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WeatherDescriptionMapperTest {
    @Test
    public void returnsClearSkiesDescription() {
        WeatherDescriptionMapper mapper = new WeatherDescriptionMapper();

        String description = mapper.getDescription(WeatherCondition.CLEAR);

        assertEquals("Clear skies", description);
    }

    @Test 
    public void returnsPartlyCloudyDescription() {
        WeatherDescriptionMapper mapper = new WeatherDescriptionMapper();

        String description = mapper.getDescription(WeatherCondition.PARTLY_CLOUDY);

        assertEquals("Partly cloudy", description);
    }

    @Test 
    public void returnsCloudyDescription() {
        WeatherDescriptionMapper mapper = new WeatherDescriptionMapper();

        String description = mapper.getDescription(WeatherCondition.CLOUDY);

        assertEquals("Cloudy", description);
    }

    @Test 
    public void returnsFoggyDescription() {
        WeatherDescriptionMapper mapper = new WeatherDescriptionMapper();

        String description = mapper.getDescription(WeatherCondition.FOG);

        assertEquals("Foggy", description);
    }

    @Test 
    public void returnsLightDrizzleDescription() {
        WeatherDescriptionMapper mapper = new WeatherDescriptionMapper();

        String description = mapper.getDescription(WeatherCondition.DRIZZLE);

        assertEquals("Light drizzle", description);
    }

    @Test 
    public void returnsRainyDescription() {
        WeatherDescriptionMapper mapper = new WeatherDescriptionMapper();

        String description = mapper.getDescription(WeatherCondition.RAIN);

        assertEquals("Rainy", description);
    }

    @Test 
    public void returnsSnowyDescription() {
        WeatherDescriptionMapper mapper = new WeatherDescriptionMapper();

        String description = mapper.getDescription(WeatherCondition.SNOW);

        assertEquals("Snowy", description);
    }

    @Test 
    public void returnsThunderstormsDescription() {
        WeatherDescriptionMapper mapper = new WeatherDescriptionMapper();

        String description = mapper.getDescription(WeatherCondition.THUNDERSTORM);

        assertEquals("Thunderstorms", description);
    }
}
