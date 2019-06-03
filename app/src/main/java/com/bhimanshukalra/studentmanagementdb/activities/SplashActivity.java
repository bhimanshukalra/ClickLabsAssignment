package com.bhimanshukalra.studentmanagementdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.bhimanshukalra.studentmanagementdb.R;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SPLASH_TIME_DELAY;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        openHomeActivityPostDelay();
    }

    private void openHomeActivityPostDelay() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            }
        };
        handler.postDelayed(runnable, SPLASH_TIME_DELAY);
    }

}
