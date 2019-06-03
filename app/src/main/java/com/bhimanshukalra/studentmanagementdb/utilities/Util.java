package com.bhimanshukalra.studentmanagementdb.utilities;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.bhimanshukalra.studentmanagementdb.backgroundTasks.IntentServiceBroadcastReceiver;
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.ServiceBroadcastReceiver;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_STUDENT;
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
        Intent intent = new Intent(context, ServiceBroadcastReceiver.class);
        intent.putExtra(SERVICE_MSG,operation);
        intent.putExtra(SERVICE_STUDENT, student);
        context.sendBroadcast(intent);
    }

    public static void intentServiceBroadcast(Context context, String operation, Student student) {
        Intent intent = new Intent(context, IntentServiceBroadcastReceiver.class);
        intent.putExtra(INTENT_SERVICE_MSG ,operation);
        intent.putExtra(INTENT_SERVICE_STUDENT , student);
        context.sendBroadcast(intent);
    }
}
