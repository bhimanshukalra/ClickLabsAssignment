package com.bhimanshukalra.studentmanagementdb.backgroundTasks;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bhimanshukalra.studentmanagementdb.database.DatabaseHandler;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DB_ERROR_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_STUDENT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.READ_ALL_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.broadcastPostDbOperations;


/**
 * The My intent service for database operations.
 */
public class MyIntentService extends IntentService {

    /**
     * Instantiates a new My intent service.
     */
    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Intent contains the db operation to be performed, and the student on whom the operation has to performed.
        String operation = intent.getStringExtra(INTENT_SERVICE_MSG);
        Student student = intent.getParcelableExtra(INTENT_SERVICE_STUDENT);

        DatabaseHandler db = new DatabaseHandler(this);
        ArrayList<Student> studentList = new ArrayList<>();
        long result = -1;
        String error = DB_ERROR_MSG;
        switch (operation) {
            case CREATE_OPERATION:
                String response = db.addStudent(student);
                if (response.equals(EMPTY_STRING)) {
                    result = 1;
                } else {
                    error = response;
                }
                break;
            case UPDATE_OPERATION:
                result = db.updateStudent(student);
                break;
            case READ_ALL_OPERATION:
                studentList = db.getAllStudents();
                result = 1;
                break;
            case DELETE_OPERATION:
                result = db.deleteStudent(student.getRollNumber());
                break;
        }

        if (result == -1) {
            //An error has occurred.
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        } else {
            //Task completed successfully, now do necessary changes in the recyclerView list.
            broadcastPostDbOperations(this, operation, studentList);
        }
        return START_NOT_STICKY;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
    }
}

