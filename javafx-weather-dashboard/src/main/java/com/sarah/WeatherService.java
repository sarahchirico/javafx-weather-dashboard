package com.sarah;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherService {
    private static final String API_URL = "https://api.open-meteo.com/v1/forecast";
    private final HttpClient httpClient;
    private final WeatherConditionMapper conditionMapper;

    public WeatherService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.conditionMapper = new WeatherConditionMapper();
    }

    public WeatherForecast fetchForecast(Location location) throws IOException, InterruptedException {
        if (location == null) {
            throw new IllegalArgumentException("A selected location is required.");
        }

        String url = API_URL
                + "?latitude=" + location.getLatitude()
                + "&longitude=" + location.getLongitude()
                + "&current=temperature_2m,weather_code,is_day"
                + "&daily=weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max"
                + "&hourly=temperature_2m,precipitation_probability,weather_code"
                + "&forecast_days=7&timezone=auto&temperature_unit=celsius";
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Weather request failed with HTTP status " + response.statusCode() + ".");
        }
        return parseForecast(response.body(), location);
    }

    private WeatherForecast parseForecast(String json, Location location) throws IOException {
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("error") && root.get("error").getAsBoolean()) {
                throw new IOException("Weather API error: " + optionalString(root, "reason", "Unknown API error"));
            }

            JsonObject current = requiredObject(root, "current");
            CurrentWeather currentWeather = new CurrentWeather(
                    conditionMapper.mapCode(requiredInt(current, "weather_code")),
                    requiredDouble(current, "temperature_2m"),
                    requiredInt(current, "is_day") == 1);

            JsonObject daily = requiredObject(root, "daily");
            JsonObject hourly = requiredObject(root, "hourly");
            JsonArray dates = requiredArray(daily, "time");
            JsonArray dailyCodes = requiredArray(daily, "weather_code");
            JsonArray highs = requiredArray(daily, "temperature_2m_max");
            JsonArray lows = requiredArray(daily, "temperature_2m_min");
            JsonArray dailyPrecipitation = requiredArray(daily, "precipitation_probability_max");
            if (dates.size() != 7 || dailyCodes.size() != dates.size() || highs.size() != dates.size()
                    || lows.size() != dates.size() || dailyPrecipitation.size() != dates.size()) {
                throw new IOException("Weather API did not return a complete 7-day forecast.");
            }

            JsonArray times = requiredArray(hourly, "time");
            JsonArray hourlyTemperatures = requiredArray(hourly, "temperature_2m");
            JsonArray hourlyPrecipitation = requiredArray(hourly, "precipitation_probability");
            JsonArray hourlyCodes = requiredArray(hourly, "weather_code");
            if (times.size() != hourlyTemperatures.size() || times.size() != hourlyPrecipitation.size()
                    || times.size() != hourlyCodes.size()) {
                throw new IOException("Weather API returned incomplete hourly forecast data.");
            }

            Map<LocalDate, List<HourlyForecast>> hourlyByDate = new HashMap<>();
            for (int i = 0; i < times.size(); i++) {
                LocalDateTime time = LocalDateTime.parse(times.get(i).getAsString());
                HourlyForecast forecast = new HourlyForecast(
                        time,
                        hourlyTemperatures.get(i).getAsDouble(),
                        hourlyPrecipitation.get(i).getAsInt(),
                        conditionMapper.mapCode(hourlyCodes.get(i).getAsInt()));
                hourlyByDate.computeIfAbsent(time.toLocalDate(), ignored -> new ArrayList<>()).add(forecast);
            }

            List<DailyForecast> dailyForecasts = new ArrayList<>();
            for (int i = 0; i < dates.size(); i++) {
                LocalDate date = LocalDate.parse(dates.get(i).getAsString());
                List<HourlyForecast> dayHours = hourlyByDate.get(date);
                if (dayHours == null || dayHours.isEmpty()) {
                    throw new IOException("Weather API returned no hourly forecast for " + date + ".");
                }
                dailyForecasts.add(new DailyForecast(
                        date,
                        conditionMapper.mapCode(dailyCodes.get(i).getAsInt()),
                        highs.get(i).getAsDouble(),
                        lows.get(i).getAsDouble(),
                        dailyPrecipitation.get(i).getAsInt(),
                        dayHours));
            }
            return new WeatherForecast(location, currentWeather, dailyForecasts);
        } catch (RuntimeException exception) {
            throw new IOException("Could not parse the weather API response.", exception);
        }
    }

    private static JsonObject requiredObject(JsonObject object, String key) throws IOException {
        JsonElement element = object.get(key);
        if (element == null || !element.isJsonObject()) {
            throw new IOException("Weather API response is missing " + key + ".");
        }
        return element.getAsJsonObject();
    }

    private static JsonArray requiredArray(JsonObject object, String key) throws IOException {
        JsonElement element = object.get(key);
        if (element == null || !element.isJsonArray()) {
            throw new IOException("Weather API response is missing " + key + ".");
        }
        return element.getAsJsonArray();
    }

    private static int requiredInt(JsonObject object, String key) throws IOException {
        JsonElement value = object.get(key);
        if (value == null || value.isJsonNull()) {
            throw new IOException("Weather API response is missing " + key + ".");
        }
        return value.getAsInt();
    }

    private static double requiredDouble(JsonObject object, String key) throws IOException {
        JsonElement value = object.get(key);
        if (value == null || value.isJsonNull()) {
            throw new IOException("Weather API response is missing " + key + ".");
        }
        return value.getAsDouble();
    }

    private static String optionalString(JsonObject object, String key, String defaultValue) {
        JsonElement element = object.get(key);
        return element == null || element.isJsonNull() ? defaultValue : element.getAsString();
    }
}
