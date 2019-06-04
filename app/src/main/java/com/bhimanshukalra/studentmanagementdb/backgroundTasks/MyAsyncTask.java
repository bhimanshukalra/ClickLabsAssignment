package com.bhimanshukalra.studentmanagementdb.backgroundTasks;

import android.content.Context;

import com.bhimanshukalra.studentmanagementdb.adapter.StudentListAdapter;
import com.bhimanshukalra.studentmanagementdb.database.DatabaseHandler;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DB_ERROR_MSG;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.log;

public class MyAsyncTask extends android.os.AsyncTask<Student, Student, Long> {
    private DatabaseHandler mDb;
    private ArrayList<Student> mStudentList;
    private String mOperation;
    private StudentListAdapter mRecyclerAdapter;
    //    private Context mContext;
    private static AsyncTaskInterface mListener;
    private String error = DB_ERROR_MSG;

    public MyAsyncTask(AsyncTaskInterface listener) {
        mListener = listener;
    }

    public MyAsyncTask(String operation, Context context, StudentListAdapter recyclerAdapter) {
        log("MyAsyncTask");
//        mOperation = operation;
//        mDb = new DatabaseHandler(context);
//        if (recyclerAdapter != null) {
//            mRecyclerAdapter = recyclerAdapter;
//        }
        //TODO: recyclerView in post execute, don't take it as a parameter.
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Long doInBackground(Student... students) {
        long result = -1;

//        switch (mOperation) {
//            case CREATE_OPERATION:
//                error = mDb.addStudent(students[0]);
//                result = 1;
//                break;
//            case UPDATE_OPERATION:
//                result = mDb.updateStudent(students[0]);
//                break;
//            case READ_ALL_OPERATION:
//                mStudentList = new ArrayList<>();
//                mDb.getAllStudents(mStudentList);
//                result = 1;
//                break;
//            case DELETE_OPERATION:
//                result = mDb.deleteStudent(students[0].getRollNumber());
//                break;
//        }
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
//        if(result == -1){
////            Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        mListener.postAsyncTaskExecution(mOperation);
//        //TODO: below code in mainActiity;
//        if (mOperation.equals("readAll")) {
//            mRecyclerAdapter.setListInRecycler(mStudentList);
//            mRecyclerAdapter.notifyDataSetChanged();
//        }
    }

    public interface AsyncTaskInterface {
        void postAsyncTaskExecution(String operation);
    }
}
