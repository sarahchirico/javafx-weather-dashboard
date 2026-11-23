package com.sarah;

public class Location {
    private String name;
    private String region;
    private String country;
    private double latitude;
    private double longitude;
    private String timezone;

    public Location(String name, String region, String country, double latitude,
                    double longitude, String timezone) {
        this.name = name;
        this.region = region;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        StringBuilder label = new StringBuilder(name);
        if (region != null && !region.isEmpty()) {
            label.append(", ").append(region);
        }
        if (country != null && !country.isEmpty()) {
            label.append(", ").append(country);
        }
        return label.toString();
    }
}
