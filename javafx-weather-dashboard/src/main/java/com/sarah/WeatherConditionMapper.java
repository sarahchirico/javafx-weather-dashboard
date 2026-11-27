package com.sarah;

/**
 * Maps WMO weather codes to the corresponding WeatherCondition values.
 */
public class WeatherConditionMapper {
    /** 
     * Converts a WMO weather code into a WeatherCondition value.
     * 
     * @param code the WMO weather code returned by the weather API
     * @return the corresponding WeatherCondition
     * @throws IllegalArgumentException if the weather code is unsupported
     */
    public WeatherCondition mapCode(int code) {
        if (code == 0) {
            return WeatherCondition.CLEAR;
        }
        if (code == 1 || code == 2) {
            return WeatherCondition.PARTLY_CLOUDY;
        }
        if (code == 3) {
            return WeatherCondition.CLOUDY;
        }
        if (code == 45 || code == 48) {
            return WeatherCondition.FOG;
        }
        if (code >= 51 && code <= 57) {
            return WeatherCondition.DRIZZLE;
        }
        if ((code >= 61 && code <= 67) || (code >= 80 && code <= 82)) {
            return WeatherCondition.RAIN;
        }
        if ((code >= 71 && code <= 77) || code == 85 || code == 86) {
            return WeatherCondition.SNOW;
        }
        if (code == 95 || code == 96 || code == 99) {
            return WeatherCondition.THUNDERSTORM;
        }
        throw new IllegalArgumentException("Unsupported WMO weather code: " + code);
    }
}
