package com.bhimanshukalra.studentmanagementdb.utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyIntentService;
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyService;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BROADCAST_UDATE_UI;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE_STUDENT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE_MSG;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE_STUDENT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.STUDENT_LIST;

/**
 * The type Util.
 */
public class Util {

    /**
     * Get text from edit text string.
     *
     * @param editText the edit text
     * @return the string contained by EditText.
     */
    public static String getTextFromEditText(EditText editText){
        return editText.getText().toString().trim();
    }

    /**
     * Start db service.
     *
     * @param context   the context for intent
     * @param operation the db operation
     * @param student   the student to be operated on.
     */
    public static void startDbService(Context context, String operation, Student student) {
        Intent intent = new Intent(context, MyService.class);
        intent.putExtra(SERVICE_MSG,operation);
        intent.putExtra(SERVICE_STUDENT, student);
        context.startService(intent);
    }

    /**
     * Broadcast post db operations.
     *
     * @param context     the context
     * @param operation   the db operation
     * @param studentList the student list
     */
    public static void broadcastPostDbOperations(Context context, String operation, ArrayList<Student> studentList) {
        Intent intent = new Intent(BROADCAST_UDATE_UI);
        intent.putExtra(OPERATION, operation);
        intent.putExtra(STUDENT_LIST, studentList);
        context.sendBroadcast(intent);
    }

    /**
     * Is input valid checks if all editTexts filled by user.
     *
     * @param editTexts the edit texts
     * @return the boolean true if all editexts are filled, else false if at least one is left blank.
     */
    public static boolean isInputValid(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (getTextFromEditText(editText).equals(EMPTY_STRING)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Show list and hide "No Data" textView.
     *
     * @param recyclerView the recycler view
     * @param tvNoData     the tv no data
     */
    public static void showList(RecyclerView recyclerView, TextView tvNoData) {
        recyclerView.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
    }

    /**
     * Hide the list and show "No Data" textView.
     *
     * @param recyclerView the recycler view
     * @param tvNoData     the tv no data
     */
    public static void hideList(RecyclerView recyclerView, TextView tvNoData) {
        recyclerView.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
    }

    /**
     * Start db intent service.
     *
     * @param context   the context
     * @param operation the db operation
     * @param student   the student to be operated on.
     */
    public static void startDbIntentService(Context context, String operation, Student student) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.putExtra(INTENT_SERVICE_MSG, operation);
        intent.putExtra(INTENT_SERVICE_STUDENT, student);
        context.startService(intent);
    }

}
