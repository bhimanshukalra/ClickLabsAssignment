package com.bhimanshukalra.studentmanagementdb.backgroundTasks;

import android.content.Context;
import android.widget.Toast;

import com.bhimanshukalra.studentmanagementdb.database.DatabaseHandler;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DB_ERROR_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.READ_ALL_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;

/**
 * The My async task for database operations.
 */
public class MyAsyncTask extends android.os.AsyncTask<Student, Student, Long> {
    private ArrayList<Student> mStudentList;
    private String mOperation;
    private WeakReference<Context> weakReference;
    private static AsyncTaskInterface mListener;
    private String error;

    /**
     * Instantiates a new My async task to fetch reference for interface.
     *
     * @param listener the interface listener
     */
    public MyAsyncTask(AsyncTaskInterface listener) {
        mListener = listener;
    }

    /**
     * Instantiates a new My async task for db operations.
     *
     * @param operation the operation is one of the CRUD operations.
     * @param context   the context is for displaying toast if any error occurs.
     */
    public MyAsyncTask(String operation, Context context) {
        mOperation = operation;
        weakReference = new WeakReference<>(context);
    }

    @Override
    protected Long doInBackground(Student... students) {
        DatabaseHandler db = new DatabaseHandler(weakReference.get());
        long result = -1;
        error = DB_ERROR_MSG;
        switch (mOperation) {
            case CREATE_OPERATION:
                String response = db.addStudent(students[0]);
                if (response.equals(EMPTY_STRING)) {
                    result = 1;
                } else {
                    error = response;
                }
                break;
            case UPDATE_OPERATION:
                result = db.updateStudent(students[0]);
                break;
            case DELETE_OPERATION:
                result = db.deleteStudent(students[0].getRollNumber());
                break;
            case READ_ALL_OPERATION:
                mStudentList = db.getAllStudents();
                result = 1;
                break;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
        if (result == -1) {
            //An error has occurred.
            Toast.makeText(weakReference.get(), error, Toast.LENGTH_SHORT).show();
        } else {
            //Task completed successfully, now do necessary changes in the recyclerView list.
            mListener.postAsyncTaskExecution(mOperation, mStudentList);
        }
    }

    /**
     * The interface Async task interface.
     */
    public interface AsyncTaskInterface {
        /**
         * Post async task execution.
         *
         * @param operation   one of the CRUD operation
         * @param studentList the student list for read operations usage
         */
        void postAsyncTaskExecution(String operation, ArrayList<Student> studentList);
    }
}
