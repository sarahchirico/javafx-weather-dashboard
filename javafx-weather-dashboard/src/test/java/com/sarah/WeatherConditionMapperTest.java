package com.sarah;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class WeatherConditionMapperTest {

    @Test
    public void mapsClearAndCloudRanges() {
        // Arrange
        WeatherConditionMapper mapper = new WeatherConditionMapper();

        // Act
        WeatherCondition clear = mapper.mapCode(0);
        WeatherCondition partlyCloudy = mapper.mapCode(2);
        WeatherCondition cloudy = mapper.mapCode(3);

        // Assert
        assertEquals(WeatherCondition.CLEAR, clear);
        assertEquals(WeatherCondition.PARTLY_CLOUDY, partlyCloudy);
        assertEquals(WeatherCondition.CLOUDY, cloudy);
    }

    @Test
    public void mapsFogDrizzleAndRainRanges() {
        // Arrange
        WeatherConditionMapper mapper = new WeatherConditionMapper();

        // Act
        WeatherCondition fog = mapper.mapCode(48);
        WeatherCondition drizzle = mapper.mapCode(57);
        WeatherCondition rain = mapper.mapCode(82);

        // Assert
        assertEquals(WeatherCondition.FOG, fog);
        assertEquals(WeatherCondition.DRIZZLE, drizzle);
        assertEquals(WeatherCondition.RAIN, rain);
    }

    @Test
    public void mapsSnowAndThunderstormRanges() {
        // Arrange
        WeatherConditionMapper mapper = new WeatherConditionMapper();

        // Act
        WeatherCondition snow = mapper.mapCode(86);
        WeatherCondition thunderstorm = mapper.mapCode(99);

        // Assert
        assertEquals(WeatherCondition.SNOW, snow);
        assertEquals(WeatherCondition.THUNDERSTORM, thunderstorm);
    }

    @Test
    public void unsupportedCodeThrowsIllegalArgumentException() {
        // Arrange
        WeatherConditionMapper mapper = new WeatherConditionMapper();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> mapper.mapCode(999));
        assertEquals("Unsupported WMO weather code: 999", exception.getMessage());
    }
}
