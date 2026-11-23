package com.sarah;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GeocodingService {
    private static final String API_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private final HttpClient httpClient;

    public GeocodingService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public List<Location> searchLocations(String query) throws IOException, InterruptedException {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("Search query must not be blank.");
        }

        String encodedQuery = URLEncoder.encode(query.trim(), StandardCharsets.UTF_8);
        URI uri = URI.create(API_URL + "?name=" + encodedQuery + "&count=10&language=en&format=json");
        HttpRequest request = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Geocoding request failed with HTTP status " + response.statusCode() + ".");
        }
        return parseLocations(response.body());
    }

    private List<Location> parseLocations(String jsonData) throws IOException {
        try {
            JsonObject root = JsonParser.parseString(jsonData).getAsJsonObject();
            if (root.has("error") && root.get("error").getAsBoolean()) {
                throw new IOException("Geocoding API error: " + optionalString(root, "reason", "Unknown API error"));
            }

            List<Location> locations = new ArrayList<>();
            JsonElement results = root.get("results");
            if (results == null || results.isJsonNull()) {
                return locations;
            }
            if (!results.isJsonArray()) {
                throw new IOException("Geocoding API returned an invalid results field.");
            }

            JsonArray resultArray = results.getAsJsonArray();
            for (JsonElement result : resultArray) {
                if (!result.isJsonObject()) {
                    throw new IOException("Geocoding API returned an invalid location.");
                }
                JsonObject item = result.getAsJsonObject();
                locations.add(new Location(
                        requiredString(item, "name"),
                        optionalString(item, "admin1", ""),
                        optionalString(item, "country", ""),
                        requiredDouble(item, "latitude"),
                        requiredDouble(item, "longitude"),
                        optionalString(item, "timezone", "")));
            }
            return locations;
        } catch (JsonParseException | IllegalStateException | NumberFormatException exception) {
            throw new IOException("Could not parse the geocoding API response.", exception);
        }
    }

    private static String requiredString(JsonObject object, String key) throws IOException {
        String value = optionalString(object, key, null);
        if (value == null || value.isEmpty()) {
            throw new IOException("Geocoding API response is missing " + key + ".");
        }
        return value;
    }

    private static String optionalString(JsonObject object, String key, String defaultValue) {
        JsonElement element = object.get(key);
        return element == null || element.isJsonNull() ? defaultValue : element.getAsString();
    }

    private static double requiredDouble(JsonObject object, String key) throws IOException {
        JsonElement value = object.get(key);
        if (value == null || value.isJsonNull()) {
            throw new IOException("Geocoding API response is missing " + key + ".");
        }
        return value.getAsDouble();
    }
}
