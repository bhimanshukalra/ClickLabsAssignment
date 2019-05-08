package com.bhimanshukalra.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import utilities.CustomTextWatcher;
import utilities.Util;

/**
 * The One Time Password activity.
 */
public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextFirst, mEditTextSecond, mEditTextThird, mEditTextFourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        mEditTextFirst = findViewById(R.id.activity_otp_et_first);
        mEditTextSecond = findViewById(R.id.activity_otp_et_second);
        mEditTextThird = findViewById(R.id.activity_otp_et_third);
        mEditTextFourth = findViewById(R.id.activity_otp_et_fourth);

        findViewById(R.id.activity_otp_btn_resend).setOnClickListener(this);
        findViewById(R.id.activity_otp_btn_submit).setOnClickListener(this);
        findViewById(R.id.activity_otp_img_btn_back).setOnClickListener(this);

        //To give focus to the first EditText on startup.
        mEditTextFirst.requestFocus();

        //Adding textChangedListener to the custom text watcher. In order to enable focus change for one edit text to another.
        mEditTextFirst.addTextChangedListener(new CustomTextWatcher(null, mEditTextSecond));
        mEditTextSecond.addTextChangedListener(new CustomTextWatcher(mEditTextFirst, mEditTextThird));
        mEditTextThird.addTextChangedListener(new CustomTextWatcher(mEditTextSecond, mEditTextFourth));
        mEditTextFourth.addTextChangedListener(new CustomTextWatcher(mEditTextThird, null));
    }

    /**
     * This function calls the displaySnackbar function in util class
     *
     * @param displayStr The string to be displayed on the snackbar
     */
    private void displaySnackBar(String displayStr) {
        new Util().displaySnackbar(findViewById(R.id.activity_otp_scroll_view), displayStr);
    }

    @Override
    public void onClick(View button) {
        switch (button.getId()) {
            case R.id.activity_otp_btn_resend:
                resendOtp();
                break;
            case R.id.activity_otp_btn_submit:
                onSubmit();
                break;
            case R.id.activity_otp_img_btn_back:
                onBackPressed();
                break;
        }
    }


    /**
     * This function is called when the resend otp button is tapped.
     * All edit texts are cleared and a snackbar is displayed.
     */
    private void resendOtp() {
        mEditTextFirst.setText("");
        mEditTextSecond.setText("");
        mEditTextThird.setText("");
        mEditTextFourth.setText("");
        mEditTextFirst.requestFocus();
        displaySnackBar("OTP is sent again.");
    }

    /**
     * This function calls the getText function in util class.
     *
     * @param editText This is reference of the EditText whose contents are to be fetched.
     * @return The contents of the EditText.
     */
    private String getText(EditText editText) {
        return new Util().getText(editText);
    }

    /**
     * This function checks if any of the EditTexts are empty.
     *
     * @return if any of the EditTexts are empty or not.
     */
    private boolean areEditTextsEmpty() {
        return getText(mEditTextFirst).isEmpty() || getText(mEditTextSecond).isEmpty() ||
                getText(mEditTextThird).isEmpty() || getText(mEditTextFourth).isEmpty();
    }

    /**
     * This function is called when submit button is tapped.
     * If any of the TextFields are empty then error is displayed via Snackbar, else LoginActivity is displayed.
     */
    private void onSubmit() {
        if (areEditTextsEmpty())
            displaySnackBar("Wrong OTP.");
        else {
            Intent intent = getIntent();
            intent.putExtra("key", "closeActivity");
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
