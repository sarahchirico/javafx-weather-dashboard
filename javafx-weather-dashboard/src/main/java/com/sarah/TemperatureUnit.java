package com.sarah;

/**
 * Represents a temperature unit and its display symbol.
 */
public enum TemperatureUnit {
    CELSIUS("C"),
    FAHRENHEIT("F");

    private final String symbol;

    /**
     * Constructs a TemperatureUnit with the specified symbol.
     * 
     * @param symbol the abbreviation for the temperature unit
     */
    TemperatureUnit(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the symbol for the temperature unit.
     * 
     * @return the temperature unit symbol
     */
    public String getSymbol() {
        return symbol;
    }
}
