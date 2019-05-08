package utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * This is a Custom TextWatcher.
 * This class has the value of previous and next EditText.
 * When an integer is entered in the EditText then the focus will shift to the next EditText.
 * Also if the contents of an EditText are removed then the focus will be shifted to the previous EditText.
 */
public class CustomTextWatcher implements TextWatcher {

    private EditText prevEditText, nextEditText;

    /**
     * This is constructor for Custom text watcher class.
     *
     * @param prevEditText the EditText previous to the current EditText
     * @param nextEditText the EditText next to the current EditText
     */
    public CustomTextWatcher(EditText prevEditText, EditText nextEditText) {
        this.prevEditText = prevEditText;
        this.nextEditText = nextEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    /**
     * If content of an EditText is removed then the focus shifts to the previous EditText.
     * If content is added to an EditText then the focus shifts to the next EditText.
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() == 0 && prevEditText != null)
            prevEditText.requestFocus();
        else if (s.length() == 1 && nextEditText != null)
            nextEditText.requestFocus();
    }
}
