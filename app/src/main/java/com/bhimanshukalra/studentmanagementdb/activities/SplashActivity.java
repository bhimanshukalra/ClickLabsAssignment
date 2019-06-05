package com.bhimanshukalra.studentmanagementdb.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.bhimanshukalra.studentmanagementdb.R;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ASYNC_TASK;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BACKGROUND_TASK_HANDLER;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BG_HANDLER_PREF_KEY;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.PREF_KEY;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SPLASH_TIME_DELAY;

/**
 * The Splash activity.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        init();
        openHomeActivityPostDelay();
    }

    /**
     * Get the saved background task handler via SharedPreferences if it exists, else use AsyncTask as default.
     */
    private void init() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        BACKGROUND_TASK_HANDLER = sharedPreferences.getInt(BG_HANDLER_PREF_KEY, ASYNC_TASK);
    }

    /**
     * Open the HomeActivity after delay.
     */
    private void openHomeActivityPostDelay() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_DELAY);
    }

}
