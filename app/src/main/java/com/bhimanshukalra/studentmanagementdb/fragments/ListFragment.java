package com.bhimanshukalra.studentmanagementdb.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhimanshukalra.studentmanagementdb.R;
import com.bhimanshukalra.studentmanagementdb.adapter.StudentListAdapter;
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyAsyncTask;
import com.bhimanshukalra.studentmanagementdb.database.DatabaseHandler;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ASYNCTASK;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BACKGROUND_TASK_HANDLER;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DB_ERROR_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.READ_ALL_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.log;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.broadcastPostDbOperations;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.serviceBroadcast;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.startDbIntentService;

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
            startDbIntentService(getActivity(), DELETE_OPERATION, student);
        }
//        log("Deleted: "+student.getStudentName());
//        fetchDataFromDb();
    }

    public interface ListFragmentInterface {
        void editStudentDetails(int position, Student student);
    }


    public static void dbTasks(String operation, Student student, Context context) {
        String error = DB_ERROR_MSG;
        DatabaseHandler db = new DatabaseHandler(context);
        long responseCode = -1;
        switch (operation) {
            case CREATE_OPERATION:
                String response = db.addStudent(student);
                if (response.equals(EMPTY_STRING)) {
                    responseCode = 1;
                } else {
                    error = response;
                }
                break;
            case UPDATE_OPERATION:
                responseCode = db.updateStudent(student);
                break;
            case READ_ALL_OPERATION:
                //TODO: remove getAllStudent's argument.
                ArrayList<Student> mStudentList = new ArrayList<>();
                db.getAllStudents(mStudentList);
                mAdapter.setListInRecycler(mStudentList);
                mAdapter.notifyDataSetChanged();
                responseCode = 1;
                break;
            case DELETE_OPERATION:
                responseCode = db.deleteStudent(student.getRollNumber());
                break;
        }
        if (responseCode == -1) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        } else if (BACKGROUND_TASK_HANDLER == SERVICE || BACKGROUND_TASK_HANDLER == INTENT_SERVICE) {
            broadcastPostDbOperations(context, operation);
        }
    }
}
