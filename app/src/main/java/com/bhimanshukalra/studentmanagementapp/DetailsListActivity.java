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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static Utilities.Util.log;
import static com.bhimanshukalra.studentmanagementapp.Constants.GRID_VIEW_COLS_STUDENT_LIST_ACTIVITY;
import static com.bhimanshukalra.studentmanagementapp.Constants.REQUEST_CODE_ADAPTER;
import static com.bhimanshukalra.studentmanagementapp.Constants.REQUEST_CODE_STUDENT_LIST_ACTIVITY;
import static com.bhimanshukalra.studentmanagementapp.RecyclerViewAdapter.setDataInRecycler;

public class DetailsListActivity extends AppCompatActivity implements RecyclerViewAdapter.RecyclerClickListener {

    private ArrayList<Student> mStudentsList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TextView mTvNoData;
    private Boolean mIsRecyclerLinear;
    private int mItemClickedPosition;
    private RecyclerView.Adapter mRecyclerAdapter;
    private boolean mIsSortByName;

//    public void setItemClickedPosition(int itemClickedPosition) {
//        mItemClickedPosition = itemClickedPosition;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.list_activity_title);
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

//        mStudentsList.add(new Student("One", "Eleven", 55));
//        mStudentsList.add(new Student("Two", "Twenty two", 444));
//        mStudentsList.add(new Student("Three", "Thirty three", 12));
//        mStudentsList.add(new Student("Four", "Forty four", 56));
//        mStudentsList.add(new Student("Five", "Eleven", 100));
//        mStudentsList.add(new Student("Two", "Twenty two", 22));
//        mStudentsList.add(new Student("Three", "Thirty three", 33));
//        mStudentsList.add(new Student("Four", "Forty four", 44));
//        mStudentsList.add(new Student("One", "Eleven", 11));
//        mStudentsList.add(new Student("Two", "Twenty two", 22));
//        mStudentsList.add(new Student("Three", "Thirty three", 33));
//        mStudentsList.add(new Student("Four", "Forty four", 44));

        mRecyclerView = findViewById(R.id.activity_details_list_recycler_student_list);
        mTvNoData = findViewById(R.id.activity_details_list_tv_no_data);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mIsRecyclerLinear = true;
        mRecyclerAdapter = new RecyclerViewAdapter(DetailsListActivity.this, this);
        setDataInRecycler(mStudentsList);
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    private void showList() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mTvNoData.setVisibility(View.GONE);
        if (mIsRecyclerLinear) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_VIEW_COLS_STUDENT_LIST_ACTIVITY));
        }
        mRecyclerView.setAdapter(mRecyclerAdapter);
    }

    public void hideList() {
        mRecyclerView.setVisibility(View.GONE);
        mTvNoData.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getExtras() == null) {
            Log.i("Bhimanshukalra", "data is null");
            return;
        }
        Log.i("Bhimanshukalra", "" + requestCode + " " + requestCode + " " + data);
        String keyExtra = data.getStringExtra(getString(R.string.key));
        if (requestCode == REQUEST_CODE_STUDENT_LIST_ACTIVITY &&
                keyExtra.equals(getString(R.string.details_list_new_student_intent_extra))) {
            Student student = (Student) data.getExtras().getSerializable(getString(R.string.all_student));
            //resultCode == RESULT_OK
            //TODO: check back press
            mStudentsList.add(student);
            if (mStudentsList.size() == 1)
                showList();
            mRecyclerAdapter.notifyItemInserted(mStudentsList.size() - 1);

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
                    mRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_VIEW_COLS_STUDENT_LIST_ACTIVITY));
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

    private void sortList() {
        Collections.sort(mStudentsList, new CustomComparator());
        for (int i = 0; i < mStudentsList.size(); i++)
            log("" + mStudentsList.get(i).getName());
        mRecyclerAdapter.notifyDataSetChanged();
    }

    private void showAlertDialog(final Context context, final int position) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.list_dialog_title)
                .setMessage(getString(R.string.dialog_message_prefix) + " " + mStudentsList.get(position).getName() + getString(R.string.dialog_message_subfix))
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
                            hideList();
                    }
                })
                .show();
    }

    private void openDetailsActivity(Context context, String intentExtra, int position) {
        Intent intent = new Intent(context, DetailsFormActivity.class);
        intent.putExtra(context.getString(R.string.key), intentExtra);
        intent.putExtra(context.getString(R.string.all_student), mStudentsList.get(position));
        mItemClickedPosition = position;
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_ADAPTER);
    }

    @Override
    public void recyclerListClicked(View view, int position) {
        showAlertDialog(DetailsListActivity.this, position);
    }

    public class CustomComparator implements Comparator<Student> {
        @Override
        public int compare(Student stu1, Student stu2) {
            if (mIsSortByName)
                return stu1.getName().compareTo(stu2.getName());
            return stu1.getRollNum().compareTo(stu2.getRollNum());
        }
    }

}
