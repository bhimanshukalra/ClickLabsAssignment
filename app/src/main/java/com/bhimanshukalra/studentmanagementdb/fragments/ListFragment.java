package com.bhimanshukalra.studentmanagementdb.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhimanshukalra.studentmanagementdb.R;
import com.bhimanshukalra.studentmanagementdb.adapter.StudentListAdapter;
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyAsyncTask;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ASYNCTASK;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BACKGROUND_TASK_HANDLER;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.READ_ALL_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.intentServiceBroadcast;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.log;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.serviceBroadcast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements StudentListAdapter.StudentListInterface {
    private RecyclerView mRecyclerView;
    private StudentListAdapter mAdapter;
    private ListFragmentInterface mListFragmentInterface;


    public ListFragment() {
        // Required empty public constructor
    }

    public void setInstance(ListFragmentInterface listFragmentInterface) {
        mListFragmentInterface = listFragmentInterface;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        new MyAsyncTask("insert", HomeActivity.this).execute(new Student("E","f",3));
//        new MyAsyncTask("insert", HomeActivity.this).execute(new Student("g","h",4));
//        new MyAsyncTask("readAll", getActivity()).execute();
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = view.findViewById(R.id.fragment_list_recycler_student_list);
        mAdapter = new StudentListAdapter();
        mAdapter.setInstance(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), layoutManager.getOrientation()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);

//        new MyAsyncTask("create", getActivity(), adapter).execute(new Student("z","x",5));
        fetchDataFromDb();
        return view;
    }

    public void fetchDataFromDb() {
        log("fetchDataFromDb");
            new MyAsyncTask(READ_ALL_OPERATION, getActivity(), mAdapter).execute();
//        new MyAsyncTask(READ_ALL_OPERATION, getActivity(), mAdapter).execute();
    }

    @Override
    public void editClicked(int position, Student student) {
        mListFragmentInterface.editStudentDetails(position, student);
    }

    @Override
    public void deleteClicked(int position, Student student) {
//        log("Delete "+position);
        if (BACKGROUND_TASK_HANDLER == ASYNCTASK) {
            new MyAsyncTask(DELETE_OPERATION, getActivity(), mAdapter).execute(student);
        } else if (BACKGROUND_TASK_HANDLER == SERVICE) {
            serviceBroadcast(getActivity(), DELETE_OPERATION, student);
        } else if (BACKGROUND_TASK_HANDLER == INTENT_SERVICE) {
            intentServiceBroadcast(getActivity(), DELETE_OPERATION, student);
        }
//        log("Deleted: "+student.getStudentName());
        fetchDataFromDb();
    }

    public interface ListFragmentInterface {
        void editStudentDetails(int position, Student student);
    }
}
