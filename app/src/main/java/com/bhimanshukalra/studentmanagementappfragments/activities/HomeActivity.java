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

import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ADD_NEW_STUDENT;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.FORM_FRAGMENT_POSITION;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.HOME_ACTIVITY_TITLE;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.INITIALIZE_WITH_ZERO;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.LIST_FRAGMENT_POSITION;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.TAB_ONE_DEFAULT_TITLE;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.TAB_ONE_TITLE_PREFIX;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.TAB_ONE_TITLE_SUFFIX;
import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.getFragmentTitles;
import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.getFragments;
import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.setStudentCountInTab;

/**
 * The Home activity which inflates the list and form fragment.
 */
public class HomeActivity extends AppCompatActivity implements ListFragment.ListInterface, FormFragment.FormInterface {
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments;
    private int studentCount;
    private ArrayList<String> mFragmentTitles;
    private HomePageAdapter mHomePageAdapter;

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
            getSupportActionBar().setTitle(HOME_ACTIVITY_TITLE);
        }
        mViewPager = findViewById(R.id.activity_home_view_pager);
        TabLayout tabLayout = findViewById(R.id.activity_home_tab_layout);
        mFragments = getFragments(this);
        mFragmentTitles = getFragmentTitles();
        mHomePageAdapter = new HomePageAdapter(getSupportFragmentManager(), mFragments, mFragmentTitles);
        mViewPager.setAdapter(mHomePageAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == FORM_FRAGMENT_POSITION) {
                    FormFragment formFragment = (FormFragment) mFragments.get(FORM_FRAGMENT_POSITION);
                    formFragment.init();
                    ListFragment listFragment = (ListFragment) mFragments.get(LIST_FRAGMENT_POSITION);
                    ArrayList<Integer> studentRollNumbers = listFragment.getStudentRollNumberList();
                    formFragment.setStudentRollNumberList(studentRollNumbers);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        studentCount = INITIALIZE_WITH_ZERO;
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
        mViewPager.setCurrentItem(FORM_FRAGMENT_POSITION);
        FormFragment formFragment = (FormFragment) mFragments.get(FORM_FRAGMENT_POSITION);
        formFragment.setStudentData(student, position);
    }

    @Override
    public void itemDeleted() {
        --studentCount;
        mFragmentTitles.remove(LIST_FRAGMENT_POSITION);
        if (studentCount >= 1) {
            mFragmentTitles.add(LIST_FRAGMENT_POSITION, TAB_ONE_TITLE_PREFIX + studentCount + TAB_ONE_TITLE_SUFFIX);
        } else {
            mFragmentTitles.add(LIST_FRAGMENT_POSITION, TAB_ONE_DEFAULT_TITLE);
        }
        mHomePageAdapter.notifyDataSetChanged();
    }

    /**
     * Switch to first fragment, then depending on the joining date of student, add to the list.
     *
     * @param student  the student with up to date data.
     * @param position the position at which the student has to be inserted. If it is -1, then add to the end.
     */
    @Override
    public void getStudentDataViaForm(Student student, int position) {
        mViewPager.setCurrentItem(LIST_FRAGMENT_POSITION);
        ListFragment listFragment = (ListFragment) mFragments.get(LIST_FRAGMENT_POSITION);
        if (position == ADD_NEW_STUDENT) {
            listFragment.getStudentData(student, position, "insert");
            studentCount++;
            setStudentCountInTab(studentCount, mFragmentTitles, mHomePageAdapter);
        } else {
            listFragment.getStudentData(student, position, "update");
        }
    }
}

