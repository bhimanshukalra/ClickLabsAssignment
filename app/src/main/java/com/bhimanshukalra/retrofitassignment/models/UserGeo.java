package com.bhimanshukalra.retrofitassignment.models;

import com.google.gson.annotations.SerializedName;

class UserGeo {
    @SerializedName("lat")
    private float lat;
    @SerializedName("lng")
    private float lng;

    public float getLat() {
        return lat;
    }

    public float getLng() {
        return lng;
    }
}
