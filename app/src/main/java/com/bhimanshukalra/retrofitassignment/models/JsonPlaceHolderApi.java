package com.bhimanshukalra.retrofitassignment.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * The interface Json place holder api, this is used for API calls.
 */
public interface JsonPlaceHolderApi {
    /**
     * Gets users.
     *
     * @return the users list.
     */
    @GET("/users")
    Call<List<User>> getUsers();

    /**
     * Gets posts.
     *
     * @param userId the user id selected.
     * @return the posts list.
     */
    @GET("/posts")
    Call<List<Post>> getPosts(@Query("userId") int userId);
}
