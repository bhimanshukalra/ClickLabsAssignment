package com.bhimanshukalra.studentmanagementdb.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.bhimanshukalra.studentmanagementdb.R;
import com.bhimanshukalra.studentmanagementdb.adapter.HomePageAdapter;
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyAsyncTask;
import com.bhimanshukalra.studentmanagementdb.fragments.FormFragment;
import com.bhimanshukalra.studentmanagementdb.fragments.ListFragment;
import com.bhimanshukalra.studentmanagementdb.models.FragmentList;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ASYNC_TASK;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BACKGROUND_TASK_HANDLER;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BG_HANDLER_PREF_KEY;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BROADCAST_UDATE_UI;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.FORM_TAB;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.HOME_APP_BAR_TITLE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.LIST_TAB;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.PREF_KEY;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.READ_ALL_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.STUDENT_LIST;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.TAB_ONE_TITLE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.TAB_TWO_ADD_TITLE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.TAB_TWO_EDIT_TITLE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATION_IN_LIST;

/**
 * The HomePage activity.
 */
public class HomeActivity extends AppCompatActivity implements ListFragment.ListFragmentInterface,
        MyAsyncTask.AsyncTaskInterface {
    private ViewPager mViewPager;
    private ArrayList<FragmentList> fragmentList;
    private MyBroadcastReceiver mMyBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    /**
     * The initialization function.
     */
    private void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(HOME_APP_BAR_TITLE);
        }
        new MyAsyncTask(this);
        initViewPager();
        initBroadcastReceiver();
    }

    /**
     * Initialize broadcast receiver for service and intent service.
     */
    private void initBroadcastReceiver() {
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(BROADCAST_UDATE_UI);
        registerReceiver(mMyBroadcastReceiver, intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_item_async_task:
                BACKGROUND_TASK_HANDLER = ASYNC_TASK;
                break;
            case R.id.home_menu_item_service:
                BACKGROUND_TASK_HANDLER = SERVICE;
                break;
            case R.id.home_menu_item_intent_service:
                BACKGROUND_TASK_HANDLER = INTENT_SERVICE;
                break;
        }
        //Set the chosen background task holder in shared preferences to enable usage of the same after app restart.
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(BG_HANDLER_PREF_KEY, BACKGROUND_TASK_HANDLER).apply();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize fragmentList with fragment references and titles.
     */
    private void initFragmentList() {
        fragmentList = new ArrayList<>();
        ListFragment listFragment = new ListFragment();
        listFragment.setInstance(this);

        fragmentList.add(new FragmentList(TAB_ONE_TITLE, listFragment));
        fragmentList.add(new FragmentList(TAB_TWO_ADD_TITLE, new FormFragment()));
    }

    /**
     * Initialize ViewPager and TabLayout.
     */
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
                    //fetch student list from db only if any changes were made.
                    if (UPDATION_IN_LIST) {
                        updateList();
                        UPDATION_IN_LIST = false;
                    }
                } else if (tab.getPosition() == FORM_TAB) {
                    //Clear all EditTexts and set btn text as "Add", when form fragment is opened.
                    FormFragment formFragment = (FormFragment) fragmentList.get(FORM_TAB).getFragment();
                    formFragment.initBtn();
                    formFragment.clearAllEditTexts();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getText() != null && tab.getText().equals(TAB_TWO_EDIT_TITLE)) {
                    //If form tab's title is "Edit Student" then switch it back to "Add student".
                    tab.setText(TAB_TWO_ADD_TITLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    /**
     * Fetch the updated list from db.
     */
    public void updateList() {
        ListFragment listFragment = (ListFragment) fragmentList.get(LIST_TAB).getFragment();
        listFragment.fetchDataFromDb();
    }

    /**
     * Initialize the HopePageAdapter(ViewPager Adapter) and set it to the recyclerView.
     */
    private void setAdapterForViewPager() {
        HomePageAdapter homePageAdapter = new HomePageAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(homePageAdapter);
    }

    /**
     * Switch to tab passed as parameter.
     *
     * @param tabNum the tab number to switched to. It can be LIST_TAB(0) or FORM_TAB(1).
     */
    public void switchToTab(int tabNum) {
        mViewPager.setCurrentItem(tabNum);
    }

    /**
     * Change the second tab's title to "Edit Student", then switch to it and set student's data in the EditTexts.
     *
     * @param student the student whose details have to be modified.
     */
    @Override
    public void editStudentDetails(Student student) {
        fragmentList.get(FORM_TAB).setTitle(TAB_TWO_EDIT_TITLE);
        setAdapterForViewPager();
        mViewPager.setCurrentItem(FORM_TAB);
        FormFragment formFragment = (FormFragment) fragmentList.get(FORM_TAB).getFragment();
        formFragment.setStudentData(student);
    }

    /**
     * The "Add student" button of list fragment is clicked. Simply the second fragment is switched.
     */
    @Override
    public void addStudentClicked() {
        mViewPager.setCurrentItem(FORM_TAB);
    }

    /**
     * The function is called after the db operations have taken place in AsyncTask.
     *
     * @param operation   this is one of the CRUD operations.
     * @param studentList the student list for use in read operation.
     */
    @Override
    public void postAsyncTaskExecution(String operation, ArrayList<Student> studentList) {
        postDbChanges(operation, studentList);
    }

    /**
     * After db operations have been completed. This function is called. According to the operation, further code is implemented.
     *
     * @param operation   the operation performed, it will be one of the CRUD operations.
     * @param studentList the student list to be used in read operation.
     */
    private void postDbChanges(String operation, ArrayList<Student> studentList) {
        ListFragment listFragment = (ListFragment) fragmentList.get(LIST_TAB).getFragment();
        if (operation.equals(READ_ALL_OPERATION)) {
            //This studentList is up to date from db and is sent to the ListFragment, to display to the user.
            listFragment.updateList(studentList);
        }
        if (operation.equals(CREATE_OPERATION) || operation.equals(UPDATE_OPERATION)) {
            //For create or update operation simply switch back to ListFragment.
            switchToTab(LIST_TAB);
        } else if (operation.equals(DELETE_OPERATION)) {
            //If a student is deleted from the list, fetch updated list from db.
            listFragment.fetchDataFromDb();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyBroadcastReceiver != null) {
            unregisterReceiver(mMyBroadcastReceiver);
        }
    }

    /**
     * This class receives broadcast from Service and IntentService, once the db operations have been performed.
     */
    public class MyBroadcastReceiver extends BroadcastReceiver {
        public MyBroadcastReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            //Operation is one of the CRUD operation.
            String operation = intent.getStringExtra(OPERATION);
            ArrayList<Student> studentList = intent.getParcelableArrayListExtra(STUDENT_LIST);
            postDbChanges(operation, studentList);
        }
    }

}
