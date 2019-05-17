package com.bhimanshukalra.studentmanagementapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static Utilities.Util.openActivityAfterDelay;

/**
 * The Splash activity.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //Open the list activity after showing the splash for three seconds.
        openActivityAfterDelay(this, DetailsListActivity.class, 3);
    }
}
