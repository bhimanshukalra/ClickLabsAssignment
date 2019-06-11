package com.bhimanshukalra.retrofitassignment.utilities;

import android.content.Context;
import android.net.ConnectivityManager;

import com.bhimanshukalra.retrofitassignment.models.JsonPlaceHolderApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bhimanshukalra.retrofitassignment.constants.constants.BASE_URL;

/**
 * The Util class for utility functions.
 */
public class Util {
    /**
     * This function initializes retrofit.
     *
     * @return instance json place holder api.
     */
    public static JsonPlaceHolderApi getRetrofitInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(JsonPlaceHolderApi.class);
    }

    /**
     * This function check network state, if the device is connected with internet on not.
     *
     * @param context The function calling Activity's context for getSystemService().
     * @return If device is connected to internet the true else false.
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo() != null;
    }
}
