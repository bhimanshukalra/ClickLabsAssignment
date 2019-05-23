package com.bhimanshukalra.studentmanagementappfragments.utilities;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * The type Util.
 */
public class Util {

    /**
     * Log.
     *
     * @param string the string
     */
    public static void log(String string) {
        Log.i("BhimanshuKalra", string);
    }

    /**
     * Get text from edit text string.
     *
     * @param editText the edit text
     * @return the string
     */
    public static String getTextFromEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static void displaySnackBar(View parentView, String displayString) {
        Snackbar.make(parentView, displayString, Snackbar.LENGTH_LONG).show();
    }

    public static boolean isInputValid(View parentView, String name, String className, String rollNumber) {
        if (name.isEmpty()) {
            displaySnackBar(parentView, "Enter Name.");
            return false;
        } else if (!name.contains(" ")) {
            displaySnackBar(parentView, "Enter Full Name.");
            return false;
        } else if (className.isEmpty()) {
            displaySnackBar(parentView, "Enter Class.");
            return false;
        } else if (rollNumber.isEmpty()) {
            displaySnackBar(parentView, "Enter Roll Number.");
            return false;
        }
        return true;
    }


}






















