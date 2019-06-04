package com.bhimanshukalra.studentmanagementdb.backgroundTasks;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bhimanshukalra.studentmanagementdb.database.DatabaseHandler;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DB_ERROR_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE_STUDENT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.log;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.broadcastPostDbOperations;

public class MyService extends Service {
    private DatabaseHandler mDb;
    private String mOperation;
    private Student mStudent;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("MyService");
        Toast.makeText(this, "Service", Toast.LENGTH_SHORT).show();
        DatabaseHandler db = new DatabaseHandler(this);
        String operation = intent.getStringExtra(SERVICE_MSG);
        Student student = intent.getParcelableExtra(SERVICE_STUDENT);

        //TODO: common function for this stuff.

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
            //TODO: ReadAll operation
            case DELETE_OPERATION:
                result = db.deleteStudent(student.getRollNumber());
                break;
        }
        //TODO: test result via primary key exception

        if (result == -1) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        } else {
            broadcastPostDbOperations(this, operation);
        }
        return START_STICKY;
    }

}