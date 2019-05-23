package com.bhimanshukalra.studentmanagementappfragments.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bhimanshukalra.studentmanagementappfragments.Adapter.HomePageAdapter;
import com.bhimanshukalra.studentmanagementappfragments.R;
import com.bhimanshukalra.studentmanagementappfragments.fragments.FormFragment;
import com.bhimanshukalra.studentmanagementappfragments.fragments.ListFragment;
import com.bhimanshukalra.studentmanagementappfragments.model.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.TAB_ONE_TITLE;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.TAB_TWO_TITLE;

/**
 * The Home activity which inflates the list and form fragment.
 */
public class HomeActivity extends AppCompatActivity implements ListFragment.ListInterface, FormFragment.FormInterface {
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    /**
     * The initialization function
     */
    private void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }
        mViewPager = findViewById(R.id.activity_home_view_pager);
        TabLayout tabLayout = findViewById(R.id.activity_home_tab_layout);
        mFragments = getFragments();
        ArrayList<String> fragmentTitles = getFragmentTitles();
        HomePageAdapter homePageAdapter = new HomePageAdapter(getSupportFragmentManager(), mFragments, fragmentTitles);
        mViewPager.setAdapter(homePageAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    FormFragment formFragment = (FormFragment) mFragments.get(1);
                    formFragment.init();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /**
     * The function creates an ArrayList containing the tab heading.
     *
     * @return The ArrayList containing names of tab headings.
     */
    private ArrayList<String> getFragmentTitles() {
        ArrayList<String> fragmentTitles = new ArrayList<>();
        fragmentTitles.add(TAB_ONE_TITLE);
        fragmentTitles.add(TAB_TWO_TITLE);
        return fragmentTitles;
    }

    /**
     * The function creates an ArrayList containing the fragment objects.
     *
     * @return The ArrayList containing reference of the list and form fragment.
     */
    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ListFragment listFragment = new ListFragment();
        listFragment.setInstance(this);
        fragments.add(listFragment);
        FormFragment formFragment = new FormFragment();
        formFragment.setInstance(this);
        fragments.add(formFragment);
        return fragments;
    }

    /**
     * Switch to second page i.e. fragment.
     */
    @Override
    public void primaryButtonClickedDetailsList() {
        mViewPager.setCurrentItem(1);
    }

    /**
     * Switch to second page and show the selected student's data.
     *
     * @param position the position of student list item clicked.
     * @param student  the student whose data has to be updated.
     */
    @Override
    public void openUpdateForm(int position, Student student) {
        mViewPager.setCurrentItem(1);
        FormFragment formFragment = (FormFragment) mFragments.get(1);
        formFragment.setStudentData(student, position);
    }

    /**
     * Switch to first fragment, then depending on the joining date of student, add to the list.
     *
     * @param student  the student with up to date data.
     * @param position the position at which the student has to be inserted. If it is -1, then add to the end.
     */
    @Override
    public void getStudentDataViaForm(Student student, int position) {
        mViewPager.setCurrentItem(0);
        ListFragment listFragment = (ListFragment) mFragments.get(0);
        if (position == -1) {
            listFragment.getStudentData(student, position, "insert");
        } else {
            listFragment.getStudentData(student, position, "update");
        }
    }
}

