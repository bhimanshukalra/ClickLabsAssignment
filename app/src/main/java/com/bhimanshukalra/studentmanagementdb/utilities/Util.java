package com.bhimanshukalra.studentmanagementdb.utilities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyIntentService;
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyService;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BROADCAST_UDATE_UI;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_STUDENT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE_STUDENT;

public class Util {

    public static void log(String string){
        Log.i("BhimanshuKalra", string);
    }

    public static String getTextFromEditText(EditText editText){
        return editText.getText().toString().trim();
    }

    public static void serviceBroadcast(Context context, String operation, Student student) {
//        log("serviceBroadcast");
        Intent intent = new Intent(context, MyService.class);
        intent.putExtra(SERVICE_MSG,operation);
        intent.putExtra(SERVICE_STUDENT, student);
        context.startService(intent);
    }

    public static void startDbIntentService(Context context, String operation, Student student) {
//        Toast.makeText(context, "IntentService", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, MyIntentService.class);
        intent.putExtra(INTENT_SERVICE_MSG ,operation);
        intent.putExtra(INTENT_SERVICE_STUDENT , student);
        context.startService(intent);
    }


    public static void broadcastPostDbOperations(Context context, String operation) {
        log("broadcastPostDbOperations");
        Intent intent = new Intent(BROADCAST_UDATE_UI);
        intent.putExtra(OPERATION, operation);
        context.sendBroadcast(intent);
    }


}
