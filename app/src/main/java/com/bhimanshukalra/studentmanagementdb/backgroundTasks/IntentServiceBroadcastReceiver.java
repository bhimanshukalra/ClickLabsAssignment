package com.bhimanshukalra.studentmanagementdb.backgroundTasks;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bhimanshukalra.studentmanagementdb.activities.HomeActivity;
import com.bhimanshukalra.studentmanagementdb.database.DatabaseHandler;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_STUDENT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.log;

public class IntentServiceBroadcastReceiver extends BroadcastReceiver {
    private DatabaseHandler mDb;
    private String mOperation;
    private Student mStudent;

    @Override
    public void onReceive(Context context, Intent intent) {
        mDb = new DatabaseHandler(context);
        mOperation = intent.getStringExtra(INTENT_SERVICE_MSG);
        mStudent = intent.getParcelableExtra(INTENT_SERVICE_STUDENT);
        Toast.makeText(context, "IntentService", Toast.LENGTH_SHORT).show();
        new MyIntentService().onHandleIntent(intent);
        HomeActivity.updateList();
    }

    public class MyIntentService extends IntentService{
        public MyIntentService() {
            super("MyIntentService");
        }

        @Override
        protected void onHandleIntent( @Nullable Intent intent) {
            log("MyIntentService");
            switch (mOperation){
                case CREATE_OPERATION:
                    mDb.addStudent(mStudent);
                    break;
                case UPDATE_OPERATION:
                    mDb.updateStudent(mStudent);
                    break;
                case DELETE_OPERATION:
                    mDb.deleteStudent(mStudent.getRollNumber());
                    break;
            }
        }
    }

}
