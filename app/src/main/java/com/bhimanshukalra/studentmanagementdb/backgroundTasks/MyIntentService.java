package com.bhimanshukalra.studentmanagementdb.backgroundTasks;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.bhimanshukalra.studentmanagementdb.database.DatabaseHandler;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DB_ERROR_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.DELETE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_STUDENT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.log;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.broadcastPostDbOperations;


public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        log("MyIntentService");
        String operation = intent.getStringExtra(INTENT_SERVICE_MSG);
        Student student = intent.getParcelableExtra(INTENT_SERVICE_STUDENT);
        DatabaseHandler mDb = new DatabaseHandler(this);


        long result = -1;
        String error = DB_ERROR_MSG;
        switch (operation) {
            case CREATE_OPERATION:
                error = mDb.addStudent(student);
                log(error);
                result = 1;
                break;
            case UPDATE_OPERATION:
                result = mDb.updateStudent(student);
                break;
            //TODO: ReadAll operation
            case DELETE_OPERATION:
                result = mDb.deleteStudent(student.getRollNumber());
                break;
        }

        if (result == -1) {
            log("result = -1");
//            if(!(operation.equals(CREATE_OPERATION))) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
//            }
        } else {
            broadcastPostDbOperations(this, operation);
        }
    }

//    public void broadcastPostDbOperations(Context context){
//        log("receiveBroadcastPostDbOperations");
////        Intent intent=new Intent(context, HomeActivity.PostDbOperation.class);
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        context.sendBroadcast(intent);
//        Intent intent = new Intent(BROADCAST_UDATE_UI);
//        sendBroadcast(intent);
//    }
}

