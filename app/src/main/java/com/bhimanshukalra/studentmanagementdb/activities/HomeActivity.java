package com.bhimanshukalra.studentmanagementdb.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.bhimanshukalra.studentmanagementdb.R;
import com.bhimanshukalra.studentmanagementdb.adapter.HomePageAdapter;
import com.bhimanshukalra.studentmanagementdb.fragments.FormFragment;
import com.bhimanshukalra.studentmanagementdb.fragments.ListFragment;
import com.bhimanshukalra.studentmanagementdb.models.FragmentList;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ASYNCTASK;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BACKGROUND_TASK_HANDLER;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.FORM_TAB;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.LIST_TAB;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATION_IN_LIST;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.log;

public class HomeActivity extends AppCompatActivity implements FormFragment.FormInterface, ListFragment.ListFragmentInterface {

    private ViewPager mViewPager;
    private static ArrayList<FragmentList> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        if (getActionBar() != null) {
            getActionBar().setTitle("Home");
        }
        initViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /**
         * TODO: Implement sharedPreferences
         */
        switch (item.getItemId()){
            case R.id.home_menu_item_asynctask:
                BACKGROUND_TASK_HANDLER = ASYNCTASK;
                break;
            case R.id.home_menu_item_service:
                BACKGROUND_TASK_HANDLER = SERVICE;
                break;
            case R.id.home_menu_item_intent_service:
                BACKGROUND_TASK_HANDLER = INTENT_SERVICE;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        ListFragment listFragment = new ListFragment();
        listFragment.setInstance(this);
        FormFragment formFragment = new FormFragment();
        formFragment.setInstance(this);

        fragmentList.add(new FragmentList(getString(R.string.list_tab_title), listFragment));
        fragmentList.add(new FragmentList(getString(R.string.form_tab_tab_add_student), formFragment));
    }

    private void initViewPager() {
        initFragmentList();
        mViewPager = findViewById(R.id.activity_home_view_pager);
        TabLayout tabLayout = findViewById(R.id.activity_home_tab_layout);
        setAdapterForViewPager();
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == LIST_TAB) {
                    if (UPDATION_IN_LIST) {
                        log("UPDATION_IN_LIST");
                        updateList();
                        UPDATION_IN_LIST = false;
                    } else {
                        log("no-------UPDATION_IN_LIST");
                        //Add button is not clicked in the second i.e. 'Add student' fragment, then hide the keyboard.
//                        hideKeyboard(HomeActivity.this);
                    }

//                    if(tab.getPosition() == LIST_TAB){
//                        log("setFormTabDefaultTitle();");
//                        setFormTabDefaultTitle();
//                    }
//                    TODO: Change student details title
                } else if (tab.getPosition() == FORM_TAB) {
                    FormFragment formFragment = (FormFragment) fragmentList.get(FORM_TAB).getFragment();
                    formFragment.initBtn();
                    formFragment.clearAllEditTexts();
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

    public static void updateList() {
        ListFragment listFragment = (ListFragment) fragmentList.get(LIST_TAB).getFragment();
        listFragment.fetchDataFromDb();
    }

    private void setFormTabDefaultTitle() {
        fragmentList.get(FORM_TAB).setTitle(getString(R.string.form_tab_tab_add_student));
        setAdapterForViewPager();
    }

    private void setAdapterForViewPager() {
        HomePageAdapter homePageAdapter = new HomePageAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(homePageAdapter);
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //FormFragment interface function.
    @Override
    public void showKeyboard(EditText etName) {
//        log("showKeyboard");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void switchToTab(int tabNum) {
//        log("switchToTab: "+tabNum);
        mViewPager.setCurrentItem(tabNum);
    }

    @Override
    public void editStudentDetails(int position, Student student) {
//        fragmentList.add(new FragmentList("Add Student", formFragment));
        fragmentList.get(FORM_TAB).setTitle(getString(R.string.form_tab_title_edit));
        setAdapterForViewPager();
        mViewPager.setCurrentItem(FORM_TAB);
        FormFragment formFragment = (FormFragment) fragmentList.get(FORM_TAB).getFragment();
        formFragment.setStudentData(student);
    }

}
