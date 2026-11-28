package com.sarah;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Periodically refreshes the weather forecast for a selected location.
 */
public class WeatherRefreshScheduler {
    public static final int DEFAULT_REFRESH_INTERVAL = 15;
    public static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.MINUTES;

    private final WeatherService weatherService;
    private final Location location;
    private final long refreshInterval;
    private final TimeUnit timeUnit;

    private ScheduledExecutorService executor;
    private ScheduledFuture<?> scheduledRefresh;

    /**
     * Creates a scheduler using the default 15-minute refresh interval.
     */
    public WeatherRefreshScheduler(WeatherService weatherService, Location location) {
        this(weatherService, location, DEFAULT_REFRESH_INTERVAL, DEFAULT_TIME_UNIT);
    }

    /**
     * Creates a scheduler using the specified refresh interval.
     */
    public WeatherRefreshScheduler(WeatherService weatherService, Location location,
                                   long refreshInterval, TimeUnit timeUnit) {
        if (weatherService == null) {
            throw new IllegalArgumentException("A weather service is required.");
        }
        if (location == null) {
            throw new IllegalArgumentException("A selected location is required.");
        }
        if (refreshInterval <= 0) {
            throw new IllegalArgumentException("The refresh interval must be greater than zero.");
        }
        if (timeUnit == null) {
            throw new IllegalArgumentException("A time unit is required.");
        }

        this.weatherService = weatherService;
        this.location = location;
        this.refreshInterval = refreshInterval;
        this.timeUnit = timeUnit;
    }

    /**
     * Starts periodic weather refreshes.
     */
    public synchronized void start() {
        if (executor != null && !executor.isShutdown()) {
            return;
        }

        ThreadFactory threadFactory = runnable -> {
            Thread thread = new Thread(runnable, "weather-refresh-scheduler");
            thread.setDaemon(true);
            return thread;
        };
        executor = Executors.newSingleThreadScheduledExecutor(threadFactory);
        scheduledRefresh = executor.scheduleWithFixedDelay(
                this::refreshWeather,
                refreshInterval,
                refreshInterval,
                timeUnit);
    }

    /**
     * Cancels scheduled refreshes and stops the scheduler.
     */
    public synchronized void stop() {
        if (scheduledRefresh != null) {
            scheduledRefresh.cancel(true);
            scheduledRefresh = null;
        }
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }
    }

    private void refreshWeather() {
        try {
            weatherService.fetchForecast(location);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        } catch (Exception exception) {
            System.err.println("Unable to refresh weather data: " + exception.getMessage());
        }
    }
}
