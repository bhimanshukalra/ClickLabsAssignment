package com.bhimanshukalra.studentmanagementdb.backgroundTasks;

import android.content.Context;

import com.bhimanshukalra.studentmanagementdb.adapter.StudentListAdapter;
import com.bhimanshukalra.studentmanagementdb.database.DatabaseHandler;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.READ_ALL_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.log;

public class MyAsyncTask extends android.os.AsyncTask<Student, Student, Void> {
    private DatabaseHandler mDb;
    private ArrayList<Student> mStudentList;
    private String mOperation;
    private StudentListAdapter mRecyclerAdapter;

    public MyAsyncTask(String operation, Context context, StudentListAdapter recyclerAdapter) {
        mOperation = operation;
        if(context != null) {
            mDb = new DatabaseHandler(context);
        }else{
            log("context is null!!!!!");
        }
        if (recyclerAdapter != null) {
            mRecyclerAdapter = recyclerAdapter;
        }
        log("MyAsyncTask");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Student... students) {
        switch (mOperation) {
            case CREATE_OPERATION:
                mDb.addStudent(students[0]);
                break;
            case UPDATE_OPERATION:
                mDb.updateStudent(students[0]);
                break;
            case READ_ALL_OPERATION:
                mStudentList = new ArrayList<>();
                mDb.getAllStudents(mStudentList);
                break;
            case DELETE_OPERATION:
                mDb.deleteStudent(students[0].getRollNumber());
                break;
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Student... students) {
        super.onProgressUpdate(students);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (mOperation.equals("readAll")) {
            mRecyclerAdapter.setListInRecycler(mStudentList);
            mRecyclerAdapter.notifyDataSetChanged();
        }
    }
}
