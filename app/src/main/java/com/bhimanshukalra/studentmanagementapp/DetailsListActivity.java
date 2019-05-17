package com.bhimanshukalra.studentmanagementapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static Utilities.Util.hideList;
import static Utilities.Util.showList;
import static com.bhimanshukalra.studentmanagementapp.Constants.GRID_VIEW_COLS_DETAILS_LIST_ACTIVITY;
import static com.bhimanshukalra.studentmanagementapp.Constants.REQUEST_CODE_ADAPTER;
import static com.bhimanshukalra.studentmanagementapp.Constants.REQUEST_CODE_STUDENT_LIST_ACTIVITY;
import static com.bhimanshukalra.studentmanagementapp.RecyclerViewAdapter.setDataInRecycler;

/**
 * The Students Details list activity.
 */
public class DetailsListActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerClickListener {

    private ArrayList<Student> mStudentsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TextView mTvNoData;
    private Boolean mIsRecyclerLinear;
    private int mItemClickedPosition;
    private RecyclerView.Adapter mRecyclerAdapter;
    private boolean mIsSortByName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);
        init();
        /**
         * Click Listener for the Add new student button.
         */
        findViewById(R.id.activity_details_list_btn_add_student).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsListActivity.this, DetailsFormActivity.class);
                intent.putExtra(getString(R.string.key), getString(R.string.details_list_new_student_intent_extra));
                ArrayList<Integer> studentRollNumList = new ArrayList<>();
                for (Student student : mStudentsList)
                    studentRollNumList.add(student.getRollNum());
                intent.putExtra(getString(R.string.adapter_intent_list_extra), studentRollNumList);
                startActivityForResult(intent, REQUEST_CODE_STUDENT_LIST_ACTIVITY);
            }
        });
    }

    /**
     * Initialization function.
     */
    private void init() {
        //Set Title of Activity.
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.list_activity_title);

        mRecyclerView = findViewById(R.id.activity_details_list_recycler_student_list);
        mTvNoData = findViewById(R.id.activity_details_list_tv_no_data);

        //Setup the recyclerView i.e. set the adapter and layoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mIsRecyclerLinear = true;
        mRecyclerAdapter = new RecyclerViewAdapter(this);
        setDataInRecycler(mStudentsList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getExtras() == null) {
            return;
        }
        String keyExtra = data.getStringExtra(getString(R.string.key));
        //Return for new student activity
        if (requestCode == REQUEST_CODE_STUDENT_LIST_ACTIVITY && keyExtra.equals(getString(R.string.details_list_new_student_intent_extra))) {
            Student student = (Student) data.getExtras().getSerializable(getString(R.string.all_student));
            mStudentsList.add(student);
            if (mStudentsList.size() == 1)
                showList(mRecyclerView, mRecyclerAdapter, mTvNoData, mIsRecyclerLinear, this);
            mRecyclerAdapter.notifyItemInserted(mStudentsList.size() - 1);

            //Return for update student activity
        } else if (requestCode == REQUEST_CODE_ADAPTER && keyExtra.equals(getString(R.string.list_dialog_update_intent_extra))) {
            Student student = (Student) data.getExtras().getSerializable(getString(R.string.all_student));
            mStudentsList.remove(mItemClickedPosition);
            mStudentsList.add(mItemClickedPosition, student);
            mRecyclerAdapter.notifyItemChanged(mItemClickedPosition);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_student_list_list_grid_switch:
                MenuView.ItemView itemView = findViewById(R.id.menu_student_list_list_grid_switch);
                if (mIsRecyclerLinear) {
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_VIEW_COLS_DETAILS_LIST_ACTIVITY));
                    itemView.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_list));
                    mIsRecyclerLinear = false;
                } else {
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    itemView.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_grid));
                    mIsRecyclerLinear = true;
                }
                break;
            case R.id.menu_student_list_sort_by_name:
                mIsSortByName = true;
                sortList();
                break;
            case R.id.menu_student_list_sort_by_roll_number:
                mIsSortByName = false;
                sortList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This function is to sort the student list.
     */
    private void sortList() {
        Collections.sort(mStudentsList, new CustomComparator());
        mRecyclerAdapter.notifyDataSetChanged();
    }

    /**
     * An alert dialog is display with view, delete, update options.
     *
     * @param context  This is context of the parent activity.
     * @param position This is the index of RecyclerView item tapped.
     */
    private void showAlertDialog(final Context context, final int position) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.list_dialog_title)
                .setMessage(getString(R.string.dialog_message_prefix) + " " + mStudentsList.get(position).getName() + getString(R.string.dialog_message_suffix))
                .setNeutralButton(R.string.list_dialog_neutral_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openDetailsActivity(context, context.getString(R.string.list_dialog_view_intent_extra), position);
                    }
                })
                .setPositiveButton(R.string.list_dialog_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openDetailsActivity(context, context.getString(R.string.list_dialog_update_intent_extra), position);
                    }
                })
                .setNegativeButton(R.string.list_dialog_delete_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mStudentsList.remove(position);
                        mRecyclerAdapter.notifyItemRemoved(position);
                        if (mStudentsList.size() == 0)
                            hideList(mRecyclerView, mTvNoData);
                    }
                })
                .show();
    }

    /**
     * This function creates and putsExtras in and intent to DetailsActivity.
     * @param context The context of current Activity.
     * @param intentExtra The student detials to be displayed.
     * @param position This is the index of RecyclerView item tapped.
     */
    private void openDetailsActivity(Context context, String intentExtra, int position) {
        Intent intent = new Intent(context, DetailsFormActivity.class);
        intent.putExtra(context.getString(R.string.key), intentExtra);
        intent.putExtra(context.getString(R.string.all_student), mStudentsList.get(position));
        mItemClickedPosition = position;
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_ADAPTER);
    }

    /**
     * The recyclerView itemClicked interface call.
     * @param view     The view tapped on
     * @param position This is the index of RecyclerView item tapped.
     */
    @Override
    public void recyclerListClicked(View view, int position) {
        showAlertDialog(DetailsListActivity.this, position);
    }

    /**
     * The Custom comparator class to sort the studentList according to student name or roll num.
     */
    public class CustomComparator implements Comparator<Student> {
        @Override
        public int compare(Student stu1, Student stu2) {
            if (mIsSortByName)
                return stu1.getName().compareTo(stu2.getName());
            return stu1.getRollNum().compareTo(stu2.getRollNum());
        }
    }

}
