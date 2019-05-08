package utilities;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

/**
 * This is a utility class.
 */
public class Util {

    /**
     * Displays Snackbar.
     *
     * @param view       the root view on which the snackBar will be displayed.
     * @param displayStr the string which will be displayed on the snackBar.
     */
    public void displaySnackbar(View view, String displayStr) {
        Snackbar.make(view, displayStr, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Hide the soft keyboard.
     *
     * @param editText the edit text whose reference will be used to close the editText.
     */
    public void hideSoftKeyboard(EditText editText) {
        editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }

    /**
     * Fetches the text of the given EditText.
     *
     * @param editText the edit text for which the function will return the containing text.
     * @return the text contained in the given editText.
     */
    public String getText(EditText editText) {
        return editText.getText().toString().trim();
    }

}

/*
    To-Do:
        comment the code
        indent all files
    Done:
        onSavedInstance(); For rotation
        Buttons rounded corners
        Styles.xml
        check usage of all strings
        implement textwatcher
 */
