package com.sarah;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TemperatureUnitTest {

    @Test
    public void celsiusHasCelsiusSymbol() {
        // Arrange
        TemperatureUnit unit = TemperatureUnit.CELSIUS;

        // Act
        String symbol = unit.getSymbol();

        // Assert
        assertEquals("C", symbol);
    }

    @Test
    public void fahrenheitHasFahrenheitSymbol() {
        // Arrange
        TemperatureUnit unit = TemperatureUnit.FAHRENHEIT;

        // Act
        String symbol = unit.getSymbol();

        // Assert
        assertEquals("F", symbol);
    }

    @Test
    public void temperatureUnitContainsBothSupportedUnits() {
        // Arrange
        // Act
        TemperatureUnit[] units = TemperatureUnit.values();

        // Assert
        assertEquals(2, units.length);
        assertEquals(TemperatureUnit.CELSIUS, units[0]);
        assertEquals(TemperatureUnit.FAHRENHEIT, units[1]);
    }
}
