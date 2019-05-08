package com.bhimanshukalra.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import utilities.CustomTextWatcher;
import utilities.Util;

/**
 * The type Otp activity.
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

        mEditTextFirst.requestFocus();

        mEditTextFirst.addTextChangedListener(new CustomTextWatcher(null, mEditTextSecond));
        mEditTextSecond.addTextChangedListener(new CustomTextWatcher(mEditTextFirst, mEditTextThird));
        mEditTextThird.addTextChangedListener(new CustomTextWatcher(mEditTextSecond, mEditTextFourth));
        mEditTextFourth.addTextChangedListener(new CustomTextWatcher(mEditTextThird, null));
    }

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

    private void resendOtp() {
        mEditTextFirst.setText("");
        mEditTextSecond.setText("");
        mEditTextThird.setText("");
        mEditTextFourth.setText("");
        mEditTextFirst.requestFocus();
        displaySnackBar("OTP is sent again.");
    }

    private String getText(EditText editText) {
        return new Util().getText(editText);
    }

    private boolean anyTextFieldIsEmpty() {
        return getText(mEditTextFirst).isEmpty() || getText(mEditTextSecond).isEmpty() ||
                getText(mEditTextThird).isEmpty() || getText(mEditTextFourth).isEmpty();
    }

    private void onSubmit() {
        if (anyTextFieldIsEmpty())
            displaySnackBar("Wrong OTP.");
        else {
            Intent intent = getIntent();
            intent.putExtra("key", "closeActivity");
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
