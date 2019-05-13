package com.bhimanshukalra.recyclerviewcrud;

public class User {
    private String mName;
    private int mAge;
    private String mPhoneNumber;
    private float mRating;
    private String mProfileUrl;
    private int mLikes;

    public User(String name, int age, String phoneNumber, float rating, String profileUrl, int likes) {
        this.mName = name;
        this.mAge = age;
        this.mPhoneNumber = phoneNumber;
        this.mRating = rating;
        this.mProfileUrl = profileUrl;
        this.mLikes = likes;
    }

    public String getName() {
        return mName;
    }

    public int getAge() {
        return mAge;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public float getRating() {
        return mRating;
    }

    public void setRating(float rating) {
        this.mRating = rating;
    }

    public String getProfileUrl() {
        return mProfileUrl;
    }

    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int likes) {
        this.mLikes = likes;
    }
}
