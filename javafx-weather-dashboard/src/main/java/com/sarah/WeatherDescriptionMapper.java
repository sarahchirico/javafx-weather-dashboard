package com.sarah;

public class WeatherDescriptionMapper {

    /**
     * Converts a weather condition into a user-friendly description.
     *
     * @param condition the weather condition to describe
     * @return the corresponding human-readable description
     * @throws IllegalArgumentException if the weather condition is unsupported
     */
    public String getDescription(WeatherCondition condition) {
        if (condition == null) {
            throw new IllegalArgumentException("Weather condition cannot be null");
        }

        switch (condition) {
            case CLEAR:
                return "Clear skies";
            case PARTLY_CLOUDY:
                return "Partly cloudy";
            case CLOUDY:
                return "Cloudy";
            case FOG:
                return "Foggy";
            case DRIZZLE:
                return "Light drizzle";
            case RAIN:
                return "Rainy";
            case SNOW:
                return "Snowy";
            case THUNDERSTORM:
                return "Thunderstorms";
            default:
                throw new IllegalArgumentException("Unsupported weather condition: " + condition);
        }
    }
}
