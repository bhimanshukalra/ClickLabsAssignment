package com.bhimanshukalra.retrofitassignment.models;

class UserAddress {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
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
