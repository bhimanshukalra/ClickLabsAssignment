package com.bhimanshukalra.studentmanagementdb.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhimanshukalra.studentmanagementdb.R;
import com.bhimanshukalra.studentmanagementdb.activities.ViewDetailsActivity;
import com.bhimanshukalra.studentmanagementdb.adapter.StudentListAdapter;
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyAsyncTask;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ALERT_DIALOG_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ALERT_DIALOG_NEGATIVE_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ALERT_DIALOG_NEUTRAL_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ALERT_DIALOG_POSITIVE_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ALERT_DIALOG_TITLE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ASYNC_TASK;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BACKGROUND_TASK_HANDLER;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_STUDENT_KEY;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.LIST_MIN_SIZE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.READ_ALL_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.hideList;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.showList;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.startDbIntentService;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.startDbService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements StudentListAdapter.StudentListInterface {

    private RecyclerView mRecyclerView;
    private StudentListAdapter mAdapter;
    private ListFragmentInterface mListFragmentInterface;
    private TextView mTvNoData;

    /**
     * Instantiates a new List fragment.
     */
    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Sets instance for interface listener.
     *
     * @param listFragmentInterface the list fragment interface
     */
    public void setInstance(ListFragmentInterface listFragmentInterface) {
        mListFragmentInterface = listFragmentInterface;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        if (getActivity() == null) {
            return;
        }
//        initialize recyclerView
        mRecyclerView = view.findViewById(R.id.fragment_list_recycler_student_list);
        mAdapter = new StudentListAdapter();
        mAdapter.setInstance(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), layoutManager.getOrientation()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        view.findViewById(R.id.fragment_list_primary_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListFragmentInterface.addStudentClicked();
            }
        });
        mTvNoData = view.findViewById(R.id.fragment_list_tv_no_data);
//        Fetch the saved data from db on start up.
        fetchDataFromDb();
    }

    /**
     * Fetch data from db.
     */
    public void fetchDataFromDb() {
        if (BACKGROUND_TASK_HANDLER == ASYNC_TASK) {
            new MyAsyncTask(READ_ALL_OPERATION, getActivity()).execute();
        } else if (BACKGROUND_TASK_HANDLER == SERVICE) {
            startDbService(getActivity(), READ_ALL_OPERATION, null);
        } else if (BACKGROUND_TASK_HANDLER == INTENT_SERVICE) {
            startDbIntentService(getActivity(), READ_ALL_OPERATION, null);
        }
    }

    /**
     * When a list item is clicked. We display an AlertDialog
     *
     * @param student  the student on which user tapped.
     * @param position the position of student in the list.
     */
    @Override
    public void listItemClicked(Student student, int position) {
        showAlertDialog(getActivity(), student, position);
    }

    /**
     * Update list.
     *
     * @param studentList the latest student list.
     */
    public void updateList(ArrayList<Student> studentList) {
        int listSize = studentList.size();
        if (listSize > LIST_MIN_SIZE) {
            showList(mRecyclerView, mTvNoData);
        } else if (listSize == LIST_MIN_SIZE) {
            hideList(mRecyclerView, mTvNoData);
        }
        mAdapter.setListInRecycler(studentList);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * The alert dialog displaying options to view, update and delete.
     *
     * @param context  for AlertDialog Builder
     * @param student  on whom user tapped.
     * @param position the position of student in the list.
     */
    private void showAlertDialog(final Context context, final Student student, final int position) {
        new AlertDialog.Builder(context)
                .setTitle(ALERT_DIALOG_TITLE)
                .setMessage(ALERT_DIALOG_MSG)
                .setNeutralButton(ALERT_DIALOG_NEUTRAL_BTN_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ViewDetailsActivity.class);
                        intent.putExtra(INTENT_STUDENT_KEY, student);
                        startActivity(intent);
                    }
                })
                .setPositiveButton(ALERT_DIALOG_POSITIVE_BTN_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListFragmentInterface.editStudentDetails(student);
                    }
                })
                .setNegativeButton(ALERT_DIALOG_NEGATIVE_BTN_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (BACKGROUND_TASK_HANDLER == ASYNC_TASK) {
                            new MyAsyncTask(DELETE_OPERATION, getActivity()).execute(student);
                        } else if (BACKGROUND_TASK_HANDLER == SERVICE) {
                            startDbService(getActivity(), DELETE_OPERATION, student);
                        } else if (BACKGROUND_TASK_HANDLER == INTENT_SERVICE) {
                            startDbIntentService(getActivity(), DELETE_OPERATION, student);
                        }
                        fetchDataFromDb();
                    }
                })
                .show();
    }

    /**
     * The interface List fragment interface.
     */
    public interface ListFragmentInterface {
        /**
         * Edit student details.
         *
         * @param student the student
         */
        void editStudentDetails(Student student);

        /**
         * The "Add student" button is clicked.
         */
        void addStudentClicked();
    }
}
