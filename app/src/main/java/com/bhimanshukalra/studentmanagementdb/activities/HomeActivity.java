package com.bhimanshukalra.studentmanagementdb.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyAsyncTask;
import com.bhimanshukalra.studentmanagementdb.fragments.FormFragment;
import com.bhimanshukalra.studentmanagementdb.fragments.ListFragment;
import com.bhimanshukalra.studentmanagementdb.models.FragmentList;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ASYNCTASK;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BACKGROUND_TASK_HANDLER;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BROADCAST_UDATE_UI;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.FORM_TAB;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.LIST_TAB;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.TAB_TWO_ADD_TITLE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.TAB_TWO_EDIT_TITLE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATION_IN_LIST;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.log;

public class HomeActivity extends AppCompatActivity implements FormFragment.FormInterface,
        ListFragment.ListFragmentInterface, MyAsyncTask.AsyncTaskInterface {
    //, MyService.ServiceInterface
    private ViewPager mViewPager;
    private ArrayList<FragmentList> fragmentList;
    PostDbOperation postDbOperation;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (postDbOperation != null) {
            unregisterReceiver(postDbOperation);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }

        new MyAsyncTask(this);
//        MyService myService = new MyService();
//        myService.setInstance(this);

        initViewPager();
        initBroadcastReceiver();
    }

    private void initBroadcastReceiver() {
        postDbOperation = new PostDbOperation();
        IntentFilter intentFilter = new IntentFilter(BROADCAST_UDATE_UI);
        registerReceiver(postDbOperation, intentFilter);
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
                        log("NO updation in list");
                        //Add button is not clicked in the second i.e. 'Add student' fragment, then hide the keyboard.
//                        hideKeyboard(HomeActivity.this);
                    }
                } else if (tab.getPosition() == FORM_TAB) {
                    FormFragment formFragment = (FormFragment) fragmentList.get(FORM_TAB).getFragment();
                    formFragment.initBtn();
                    formFragment.clearAllEditTexts();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getText().equals(TAB_TWO_EDIT_TITLE)) {
                    tab.setText(TAB_TWO_ADD_TITLE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void updateList() {
        ListFragment listFragment = (ListFragment) fragmentList.get(LIST_TAB).getFragment();
        listFragment.fetchDataFromDb();
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
        mViewPager.setCurrentItem(tabNum);
    }

    @Override
    public void editStudentDetails(int position, Student student) {
        fragmentList.get(FORM_TAB).setTitle(TAB_TWO_EDIT_TITLE);
        setAdapterForViewPager();
        mViewPager.setCurrentItem(FORM_TAB);
        FormFragment formFragment = (FormFragment) fragmentList.get(FORM_TAB).getFragment();
        formFragment.setStudentData(student);
    }

    @Override
    public void postAsyncTaskExecution(String operation) {
        postDbChanges(operation);
    }

    private void postDbChanges(String operation) {
        if (operation.equals(CREATE_OPERATION) || operation.equals(UPDATE_OPERATION)) {
            switchToTab(LIST_TAB);
        } else if (operation.equals(DELETE_OPERATION)) {
            ListFragment listFragment = (ListFragment) fragmentList.get(LIST_TAB).getFragment();
            listFragment.fetchDataFromDb();
        }
    }

    public class PostDbOperation extends BroadcastReceiver {
        public PostDbOperation() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String operation = intent.getStringExtra(OPERATION);
            postDbChanges(operation);
        }
    }


}
