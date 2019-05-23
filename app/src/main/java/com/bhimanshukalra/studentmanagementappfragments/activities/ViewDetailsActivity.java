package com.bhimanshukalra.studentmanagementappfragments.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bhimanshukalra.studentmanagementappfragments.R;
import com.bhimanshukalra.studentmanagementappfragments.fragments.FormFragment;
import com.bhimanshukalra.studentmanagementappfragments.model.Student;

/**
 * The View details activity, we inflate the form fragment and display selected student's data.
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
        Student student = (Student) intent.getSerializableExtra("student");
        //student object contains the details to be displayed on the fragment.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable("student", student);
        bundle.putString("accessMode", "view");
        //accessMode represents the access we are giving the user.
        FormFragment formFragment = FormFragment.newInstance(bundle);
        fragmentTransaction.add(R.id.activity_view_details_ll, formFragment).commit();

        if (getSupportActionBar() != null) {
            //Set back button on app bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle("View student");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //There is only one menu item. Hence, no switch.
        finish();
        return super.onOptionsItemSelected(item);
    }
}
