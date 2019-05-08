package com.bhimanshukalra.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

/**
 * This activity is for the splash which displays "Assignment 2"
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Timer to wait for 3 seconds and then start login activity
        new CountDownTimer(3000, 3000) {
            //After 3 seconds this activity will be destroyed and login activity is started.
            @Override
            public void onFinish() {
                finish();
                startActivityForResult(new Intent(SplashActivity.this, LoginActivity.class), 1);
            }

            @Override
            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
}
