package Utilities;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Util {
    private static int listItemClickedPosition;

    public static void log(String string) {
        Log.i("BhimanshuKalra", string);
    }

//    public static void setListSize(int listSize) {
//        Util.listSize = listSize;
//    }

    public static int getListItemClickedPosition() {
        return listItemClickedPosition;
    }

    //    private static int listSize;
    public static void setListItemClickedPosition(int position) {
        listItemClickedPosition = position;
    }

    public static boolean areAllEdiTextsFilled(EditText... editTexts) {
        for (EditText editText : editTexts) {
            String etValue = editText.getText().toString().trim();
            if (etValue.isEmpty())
                return true;
        }
        return false;
    }

    public static String getTextFromEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static boolean containsSpace(String string) {
        return string.contains(" ");
    }

    public static void displaySnackBar(View parentView, String displayString) {
        Snackbar.make(parentView, displayString, Snackbar.LENGTH_LONG).show();
    }

    public static boolean isEditTextEmpty(EditText editText) {
        return getTextFromEditText(editText).length() == 0;
    }
}















