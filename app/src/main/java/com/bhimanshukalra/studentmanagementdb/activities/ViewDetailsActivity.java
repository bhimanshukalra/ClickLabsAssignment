package com.bhimanshukalra.studentmanagementdb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bhimanshukalra.studentmanagementdb.R;
import com.bhimanshukalra.studentmanagementdb.fragments.FormFragment;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ACCESS_MODE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ACCESS_MODE_VIEW;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BUNDLE_STUDENT_KEY;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_STUDENT_KEY;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.VIEW_ACTIVITY_APP_BAR_TITLE;

/**
 * The View details activity. The FormFragment is displayed in this activity in view only mode.
 */
public class ViewDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        init();
    }

    /**
     * The initialization function
     */
    private void init() {
        Intent intent = getIntent();
        Student student = intent.getParcelableExtra(INTENT_STUDENT_KEY);
        //student object contains the details to be displayed on the fragment.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_STUDENT_KEY, student);
        bundle.putString(ACCESS_MODE, ACCESS_MODE_VIEW);
        //accessMode represents the access we are giving the user, which is view only in this case.
        FormFragment formFragment = FormFragment.newInstance(bundle);
        fragmentTransaction.add(R.id.activity_view_details_ll, formFragment).commit();

        if (getSupportActionBar() != null) {
            //Set back button on app bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle(VIEW_ACTIVITY_APP_BAR_TITLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //There is only one menu item. Hence, no switch.
        finish();
        return super.onOptionsItemSelected(item);
    }
}
