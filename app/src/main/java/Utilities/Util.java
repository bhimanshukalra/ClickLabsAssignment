package Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bhimanshukalra.studentmanagementapp.R;
import com.bhimanshukalra.studentmanagementapp.Student;

import static com.bhimanshukalra.studentmanagementapp.Constants.GRID_VIEW_COLS_DETAILS_LIST_ACTIVITY;

/**
 * The Utility class.
 */
public class Util {

    /**
     * Gets text from edit text.
     *
     * @param editText the edit text whose the we want to fetch
     * @return the text from edit text
     */
    public static String getTextFromEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * The function tells whether the string contains space or not.
     *
     * @param string the string
     * @return the boolean If string contains space or not.
     */
    public static boolean containsSpace(String string) {
        return string.contains(" ");
    }

    /**
     * Display snack bar.
     *
     * @param parentView    the parent view on which the snackBar will be diplayed.
     * @param displayString the string to be displayed on SnackBar.
     */
    public static void displaySnackBar(View parentView, String displayString) {
        Snackbar.make(parentView, displayString, Snackbar.LENGTH_LONG).show();
    }

    /**
     * Is edit text empty boolean.
     *
     * @param editText the edit text which we want to check.
     * @return the boolean If the EditText is empty or not.
     */
    public static boolean isEditTextEmpty(EditText editText) {
        return getTextFromEditText(editText).length() == 0;
    }

    /**
     * Open activity after delay.
     *
     * @param context     The context of current activity.
     * @param newActivity The new activity to be opened.
     * @param delay       The delay to open new Activity in seconds.
     */
    public static void openActivityAfterDelay(final Context context, final Class newActivity, int delay) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ((Activity) context).finish();
                context.startActivity(new Intent(context, newActivity));
            }
        };
        handler.postDelayed(runnable, delay * 1000);
    }

    /**
     * Is input valid boolean.
     *
     * @param context   The context of current activity.
     * @param etName    The EditText containing name
     * @param etClass   The EditText containing class
     * @param etRollNum The EditText containing roll num
     * @param view      The view to display error SnackBar (if any).
     * @return the boolean is  input valid.
     */
    public static boolean isInputValid(Context context, EditText etName, EditText etClass, EditText etRollNum, View view) {
        if (!isEditTextEmpty(etName)) {
            if (!containsSpace(getTextFromEditText(etName))) {
                return giveError(view, context.getString(R.string.error_full_name));
            }
        } else {
            return giveError(view, context.getString(R.string.error_name));
        }
        if (isEditTextEmpty(etClass)) {
            return giveError(view, context.getString(R.string.error_class));
        }
        if (isEditTextEmpty(etRollNum)) {
            return giveError(view, context.getString(R.string.error_roll_num));
        }
        return true;
    }

    /**
     * Gives error boolean i.e. false.
     *
     * @param view  The view to display error SnackBar.
     * @param error The string containing error
     * @return the boolean false.
     */
    public static boolean giveError(View view, String error) {
        displaySnackBar(view, error);
        return false;
    }

    /**
     * Create new student.
     *
     * @param etName    The EditText containing name
     * @param etClass   The EditText containing class
     * @param etRollNum The EditText containing  num
     * @return the student
     */
    public static Student createNewStudent(EditText etName, EditText etClass, EditText etRollNum) {
        String name = getTextFromEditText(etName);
        String className = getTextFromEditText(etClass);
        int rollNum = Integer.parseInt(getTextFromEditText(etRollNum));
        return new Student(name, className, rollNum);
    }

    /**
     * Show the list i.e. recyclerView.
     *
     * @param recyclerView     The recycler view
     * @param mRecyclerAdapter The recyclerView adapter
     * @param tvNoData         The TextView containing "no data"
     * @param isRecyclerLinear This boolean tells of the recycler is linear or not.
     * @param context          The context of parent Activity.
     */
    public static void showList(RecyclerView recyclerView, RecyclerView.Adapter mRecyclerAdapter,
                                TextView tvNoData, boolean isRecyclerLinear, Context context) {
        recyclerView.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
        if (isRecyclerLinear) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, GRID_VIEW_COLS_DETAILS_LIST_ACTIVITY));
        }
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    /**
     * Hide list.
     *
     * @param recyclerView The recycler view
     * @param tvNoData     The TextView containing "no data"
     */
    public static void hideList(RecyclerView recyclerView, TextView tvNoData) {
        recyclerView.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
    }


}















