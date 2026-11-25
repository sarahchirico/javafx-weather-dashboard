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

/**
 * Handles retrieving and processing location data from the Open-Meteo Geocoding API.
 */
public class GeocodingService {
    private static final String API_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private final HttpClient httpClient;

    /**
     * Constructs a GeocodingService and initializes and HTTP client with a connection timeout.
     */
    public GeocodingService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    /** 
     * Searches for locations matching the specified query using the Open-Meteo Geocoding API.
     * 
     * @param query the name of the location to search for
     * @return a list of matching Location objects
     * @throws IllegalArgumentException if the search query is null or blank
     * @throws IOException if the API request fails or the response cannot be processed
     * @throws InterruptedException if the HTTP request is interrupted
     */
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

    /** 
     * Parses the JSON response from the Geocoding API and converts the results into Location objects.
     * 
     * @param jsonData the JSON response received from the API
     * @return a list of Location objects extracted from the response
     * @throws IOException if the reponse contains invalid or missing location data
     */
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

    /** 
     * Retrieves a required String value from a JSON object.
     * 
     * @param object the JSON object containing the requested value
     * @param key the name of the JSON property to retrieve
     * @return the requested String value
     * @throws IOException if the property is missing or empty
     */
    private static String requiredString(JsonObject object, String key) throws IOException {
        String value = optionalString(object, key, null);
        if (value == null || value.isEmpty()) {
            throw new IOException("Geocoding API response is missing " + key + ".");
        }
        return value;
    }

    /** 
     * Retrieves an optional String value from a JSON object.
     * 
     * @param object the JSON object containing the requested value
     * @param key the name of the JSON property to retrieve
     * @param defaultValue the value to return if the property is missing or null
     * @return the requested String value or the default value
     */
    private static String optionalString(JsonObject object, String key, String defaultValue) {
        JsonElement element = object.get(key);
        return element == null || element.isJsonNull() ? defaultValue : element.getAsString();
    }

    /** 
     * Retrieves a required numeric value from a JSON object.
     * 
     * @param object the JSON object containing the requested value
     * @param key the name of the JSON property to retrieve
     * @return the requested numeric value as a double
     * @throws IOException if the property is missing or null
     */
    private static double requiredDouble(JsonObject object, String key) throws IOException {
        JsonElement value = object.get(key);
        if (value == null || value.isJsonNull()) {
            throw new IOException("Geocoding API response is missing " + key + ".");
        }
        return value.getAsDouble();
    }
}
