package com.bhimanshukalra.retrofitassignment.utilities;

import com.bhimanshukalra.retrofitassignment.models.JsonPlaceHolderApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bhimanshukalra.retrofitassignment.constants.constants.BASE_URL;

public class Util {
    public static JsonPlaceHolderApi getRetrofitInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JsonPlaceHolderApi.class);
    }
}
