package com.goeuro.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by datta on 07/08/2016.
 */
public class CityLocation {
    @SerializedName("_id")
    private long id;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("fullName")
    private String fullName;
    @SerializedName("iata_airport_code")
    private String iataAirportCode;
    @SerializedName("type")
    private String type;
    @SerializedName("country")
    private String country;
    @SerializedName("locationId")
    private long
            locationId;
    @SerializedName("inEurope")
    private boolean isInEurope;
    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("coreCountry")
    private boolean isCoreCountry;
    @SerializedName("distance")
    private String distance;
    @SerializedName("geo_position")
    private GeoPosition geoPosition;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIataAirportCode() {
        return iataAirportCode;
    }

    public void setIataAirportCode(String iataAirportCode) {
        this.iataAirportCode = iataAirportCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public boolean isInEurope() {
        return isInEurope;
    }

    public void setInEurope(boolean inEurope) {
        isInEurope = inEurope;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isCoreCountry() {
        return isCoreCountry;
    }

    public void setCoreCountry(boolean coreCountry) {
        isCoreCountry = coreCountry;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public GeoPosition getGeoPosition() {
        return geoPosition;
    }

    public void setGeoPosition(GeoPosition geoPosition) {
        this.geoPosition = geoPosition;
    }
}
