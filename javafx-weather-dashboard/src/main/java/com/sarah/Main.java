package com.sarah;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private final GeocodingService geocodingService;
    private final WeatherService weatherService;
    private final TemperatureConverter temperatureConverter;

    public Main() {
        this.geocodingService = new GeocodingService();
        this.weatherService = new WeatherService();
        this.temperatureConverter = new TemperatureConverter();
    }

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Search for a city (or Q to quit): ");
                String query = scanner.nextLine().trim();
                if ("Q".equalsIgnoreCase(query)) {
                    return;
                }
                if (query.isEmpty()) {
                    System.out.println("Please enter a city name.");
                    continue;
                }

                List<Location> locations = geocodingService.searchLocations(query);
                if (locations.isEmpty()) {
                    System.out.println("No matching locations were found. Try another search.");
                    continue;
                }

                System.out.println("Matching locations:");
                for (int i = 0; i < locations.size(); i++) {
                    System.out.printf(Locale.ROOT, "%d. %s%n", i + 1, locations.get(i));
                }
                Location selectedLocation = selectLocation(scanner, locations);
                WeatherForecast forecast = weatherService.fetchForecast(selectedLocation);
                System.out.println("\nWeather for " + forecast.getLocation());
                if (!displayForecastMenu(scanner, forecast)) {
                    return;
                }
            }
        } catch (IOException exception) {
            System.err.println("Unable to retrieve weather data: " + exception.getMessage());
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            System.err.println("The weather request was interrupted.");
        }
    }

    private boolean displayForecastMenu(Scanner scanner, WeatherForecast forecast) {
        TemperatureUnit unit = TemperatureUnit.CELSIUS;
        displayForecast(forecast, unit);

        while (true) {
            System.out.print("\nChoose [C]elsius, [F]ahrenheit, [S]earch again, or [Q]uit: ");
            String choice = scanner.nextLine().trim();
            if ("S".equalsIgnoreCase(choice)) {
                return true;
            }
            if ("Q".equalsIgnoreCase(choice)) {
                System.out.println("Goodbye.");
                return false;
            }
            if ("C".equalsIgnoreCase(choice)) {
                unit = TemperatureUnit.CELSIUS;
                displayForecast(forecast, unit);
            } else if ("F".equalsIgnoreCase(choice)) {
                unit = TemperatureUnit.FAHRENHEIT;
                displayForecast(forecast, unit);
            } else {
                System.out.println("Enter C, F, S, or Q.");
            }
        }
    }

    private static Location selectLocation(Scanner scanner, List<Location> locations) {
        while (true) {
            System.out.print("Select a location by number: ");
            String input = scanner.nextLine().trim();
            int selection;
            try {
                selection = Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                System.out.println("Enter one of the displayed location numbers.");
                continue;
            }
            if (selection >= 1 && selection <= locations.size()) {
                return locations.get(selection - 1);
            }
            System.out.println("Enter one of the displayed location numbers.");
        }
    }

    private void displayForecast(WeatherForecast forecast, TemperatureUnit unit) {
        CurrentWeather current = forecast.getCurrentWeather();
        System.out.printf(Locale.ROOT, "%nCurrent: %s, %s%n",
                formatTemperature(current.getTemperature(), unit), current.getCondition());

        for (DailyForecast day : forecast.getDailyForecasts()) {
            System.out.printf(Locale.ROOT, "%n%s: %s, high %s, low %s, precipitation %d%%%n",
                    day.getDate(),
                    day.getCondition(),
                    formatTemperature(day.getHighTemperature(), unit),
                    formatTemperature(day.getLowTemperature(), unit),
                    day.getPrecipitationProbability());
            for (HourlyForecast hour : day.getHourlyForecasts()) {
                System.out.printf(Locale.ROOT, "  %s  %s  %s  precipitation %d%%%n",
                        hour.getTime(),
                        formatTemperature(hour.getTemperature(), unit),
                        hour.getCondition(),
                        hour.getPrecipitationProbability());
            }
        }
    }

    private String formatTemperature(double celsius, TemperatureUnit unit) {
        double value = unit == TemperatureUnit.FAHRENHEIT
                ? temperatureConverter.toFahrenheit(celsius)
                : celsius;
        return String.format(Locale.ROOT, "%.1f°%s", value, unit.getSymbol());
    }
}
