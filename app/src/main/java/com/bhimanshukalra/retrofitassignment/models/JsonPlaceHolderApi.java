package com.bhimanshukalra.retrofitassignment.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @GET("/users")
    Call<List<User>> getUsers();

    @GET("/posts")
    Call<List<Post>> getPosts(@Query("userId") int userId);
}
