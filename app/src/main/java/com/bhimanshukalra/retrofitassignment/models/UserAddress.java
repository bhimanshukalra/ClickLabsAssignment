package com.bhimanshukalra.retrofitassignment.models;

import com.google.gson.annotations.SerializedName;

class UserAddress {
    @SerializedName("street")
    private String street;
    @SerializedName("suite")
    private String suite;
    @SerializedName("city")
    private String city;
    @SerializedName("zipcode")
    private String zipcode;
    @SerializedName("geo")
    private UserGeo geo;

    public String getStreet() {
        return street;
    }

    public String getSuite() {
        return suite;
    }

    public String getCity() {
        return city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public UserGeo getGeo() {
        return geo;
    }
}
