package com.goeuro.client.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by datta on 07/08/2016.
 */
public class GeoPosition {

    public GeoPosition() {
    }

    public GeoPosition(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    @SerializedName("latitude")
    private float latitude;

    @SerializedName("longitude")
    private float longitude;
}
