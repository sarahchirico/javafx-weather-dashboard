package com.sarah;

/**
 * Constructs a TemperatureUnit with the specified symbol.
 * 
 * @param symbol the abbreviation for the temperature unit
 */
public enum TemperatureUnit {
    CELSIUS("C"),
    FAHRENHEIT("F");

    private final String symbol;

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
