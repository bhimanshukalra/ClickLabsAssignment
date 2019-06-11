package com.bhimanshukalra.retrofitassignment.models;

import android.os.Parcel;
import android.os.Parcelable;

import static com.bhimanshukalra.retrofitassignment.constants.constants.NEXT_LINE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_CITY;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_COMPANY_BS;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_COMPANY_CATCH_PHRASE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_COMPANY_NAME;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_EMAIL;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_ID;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_LAT;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_LNG;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_NAME;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_PHONE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_STREET;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_SUITE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_USERNAME;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_WEBSITE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_ZIP_CODE;

public class User implements Parcelable {
    private int id;
    private String name;
    private String username;
    private String email;
    private UserAddress address;
    private String phone;
    private String website;
    private UserCompany company;
//TODO: Seriselizabe

    protected User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        username = in.readString();
        email = in.readString();
        phone = in.readString();
        website = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getPrimaryUserDetails(){
        String userDetails = "";
        userDetails += USER_ID + id + NEXT_LINE;
        userDetails += USER_NAME + name + NEXT_LINE;
        userDetails += USER_EMAIL + email + NEXT_LINE;
        return userDetails;
    }


    public String getAllUserDetails(){
        String userDetails = "";
        userDetails += USER_ID + id + NEXT_LINE;
        userDetails += USER_NAME + name + NEXT_LINE;
        userDetails += USER_USERNAME + username + NEXT_LINE;
        userDetails += USER_EMAIL + email + NEXT_LINE;
        userDetails += USER_STREET + address.getStreet() + NEXT_LINE;
        userDetails += USER_SUITE + address.getSuite() + NEXT_LINE;
        userDetails += USER_CITY + address.getCity() + NEXT_LINE;
        userDetails += USER_ZIP_CODE + address.getZipcode() + NEXT_LINE;
        userDetails += USER_LAT + address.getGeo().getLat() + NEXT_LINE;
        userDetails += USER_LNG + address.getGeo().getLng() + NEXT_LINE;
        userDetails += USER_PHONE + phone + NEXT_LINE;
        userDetails += USER_WEBSITE + website + NEXT_LINE;
        userDetails += USER_COMPANY_NAME + company.getName() + NEXT_LINE;
        userDetails += USER_COMPANY_CATCH_PHRASE + company.getCatchPhrase() + NEXT_LINE;
        userDetails += USER_COMPANY_BS + company.getBs() + NEXT_LINE;
        return userDetails;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public UserAddress getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public UserCompany getCompany() {
        return company;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(website);
    }
}
