package com.sarah;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class WeatherRefreshSchedulerTest {

    @Test
    public void startSchedulesWeatherRefresh() throws InterruptedException {
        TestWeatherService service = new TestWeatherService();

        Location location = new Location(
            "Boston",
            "Massachusetts",
            "United States",
            42.3601,
            -71.0589,
            "America/New_York"
        );

        WeatherRefreshScheduler scheduler = new WeatherRefreshScheduler(service, location, 1, TimeUnit.SECONDS);

        try {
            scheduler.start();

            boolean refreshed = service.latch.await(3, TimeUnit.SECONDS);
            assertTrue(refreshed);

        } finally {
            scheduler.stop();
        }
    }

    private static class TestWeatherService extends WeatherService {
        public final CountDownLatch latch = new CountDownLatch(1);

        @Override
        public WeatherForecast fetchForecast(Location location) throws IOException, InterruptedException {
            latch.countDown();
            return null;
        }
    }

    @Test
    public void usesFifteenMinuteRefreshInterval() {
        assertEquals(15, WeatherRefreshScheduler.DEFAULT_REFRESH_INTERVAL);
        assertEquals(TimeUnit.MINUTES, WeatherRefreshScheduler.DEFAULT_TIME_UNIT);
    }

    @Test
    public void startSchedulesWeatherRefreshRepeatedly() throws InterruptedException {
        CountingWeatherService service = new CountingWeatherService(3);
        WeatherRefreshScheduler scheduler =
                new WeatherRefreshScheduler(service, boston(), 100, TimeUnit.MILLISECONDS);

        try {
            scheduler.start();

            assertTrue("The scheduler should run more than once",
                    service.latch.await(3, TimeUnit.SECONDS));
            assertTrue(service.calls.get() >= 3);

        } finally {
            scheduler.stop();
        }
    }

    @Test
    public void stopCancelsScheduledRefresh() throws InterruptedException {
        CountingWeatherService service = new CountingWeatherService(1);
        WeatherRefreshScheduler scheduler =
                new WeatherRefreshScheduler(service, boston(), 100, TimeUnit.MILLISECONDS);

        try {
            scheduler.start();

            boolean refreshed = service.latch.await(2, TimeUnit.SECONDS);
            assertTrue("The scheduler should run at least once", refreshed);
            
            scheduler.stop();

            int callsAtStop = service.calls.get();
            Thread.sleep(350);
            assertEquals("Stopping the scheduler should prevent later refreshes",
                    callsAtStop, service.calls.get());

        } finally {
            scheduler.stop();
        }
    }

    @Test
    public void continuesRefreshingAfterApiFailure() throws InterruptedException {
        FailingOnceWeatherService service = new FailingOnceWeatherService();
        WeatherRefreshScheduler scheduler =
                new WeatherRefreshScheduler(service, boston(), 100, TimeUnit.MILLISECONDS);

        try {
            scheduler.start();

            assertTrue("A later refresh should run after an API failure",
                    service.successLatch.await(3, TimeUnit.SECONDS));
            assertTrue(service.calls.get() >= 2);

        } finally {
            scheduler.stop();
        }
    }

    private static Location boston() {
        return new Location(
                "Boston",
                "Massachusetts",
                "United States",
                42.3601,
                -71.0589,
                "America/New_York");
    }

    private static class CountingWeatherService extends WeatherService {
        public final AtomicInteger calls = new AtomicInteger();
        public final CountDownLatch latch;

        private CountingWeatherService(int expectedCalls) {
            latch = new CountDownLatch(expectedCalls);
        }

        @Override
        public WeatherForecast fetchForecast(Location location) throws IOException, InterruptedException {
            calls.incrementAndGet();
            latch.countDown();
            return null;
        }
    }

    private static class FailingOnceWeatherService extends WeatherService {
        public final AtomicInteger calls = new AtomicInteger();
        public final CountDownLatch successLatch = new CountDownLatch(1);

        @Override
        public WeatherForecast fetchForecast(Location location) throws IOException, InterruptedException {
            int callNumber = calls.incrementAndGet();
            if (callNumber == 1) {
                throw new IOException("Temporary API failure");
            }
            successLatch.countDown();
            return null;
        }
    }
}
