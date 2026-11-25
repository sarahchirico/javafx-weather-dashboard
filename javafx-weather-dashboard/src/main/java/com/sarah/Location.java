package com.sarah;

/**
 * Represents a specific location.
 * Stores the location's name, region, country, latitude, longitude, and timezone.
 */
public class Location {
    private String name;
    private String region;
    private String country;
    private double latitude;
    private double longitude;
    private String timezone;

    /**
     * Constructs a Location object with the specified information.
     * 
     * @param name the name of the location
     * @param region the state, province, or region where the location is located
     * @param country the country where the location is located
     * @param latitude the geographic latitude of the location
     * @param longitude the geographic longitude of the location
     * @param timezone the timezone of the location
     */
    public Location(String name, String region, String country, double latitude,
                    double longitude, String timezone) {
        this.name = name;
        this.region = region;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
    }

    /** 
     * Returns the location's name.
     * 
     * @return the location's name
     */
    public String getName() {
        return name;
    }

    /** 
     * Updates the location's name.
     * 
     * @param name the location's new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** 
     * Returns the location's region.
     * 
     * @return the location's region
     */
    public String getRegion() {
        return region;
    }

    /** 
     * Updates the location's region.
     * 
     * @param region the location's new region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /** 
     * Returns the location's country.
     * 
     * @return the location's country
     */
    public String getCountry() {
        return country;
    }

    /** 
     * Updates the location's country.
     * 
     * @param country the location's new country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /** 
     * Returns the location's latitude.
     * 
     * @return the location's latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /** 
     * Updates the location's latitude.
     * 
     * @param latitude the location's new latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /** 
     * Returns the location's longitude.
     * 
     * @return the location's longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /** 
     * Updates the location's longitude.
     * 
     * @param longitude the location's new longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /** 
     * Returns the location's timezone.
     * 
     * @return the location's timezone
     */
    public String getTimezone() {
        return timezone;
    }

    /** 
     * Updates the location's timezone.
     * 
     * @param timezone the location's new timezone
     */
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    /** 
     * Returns a formatted label containing the location's name, region, and country.
     * 
     * @return the formatted location label
     */
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
