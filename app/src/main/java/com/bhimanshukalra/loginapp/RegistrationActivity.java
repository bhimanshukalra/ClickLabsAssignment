package com.bhimanshukalra.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import utilities.Util;

/**
 * The type Registration activity.
 */
public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextFullName, mEditTextGender, mEditTextUserType, mEditTextOccupation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mEditTextFullName = findViewById(R.id.activity_registration_et_full_name);
        mEditTextGender = findViewById(R.id.activity_registration_et_gender);
        mEditTextUserType = findViewById(R.id.activity_registration_et_user_type);
        mEditTextOccupation = findViewById(R.id.activity_registration_et_occupation);

        //To give focus to the first EditText on startup.
        mEditTextFullName.requestFocus();

        findViewById(R.id.activity_registration_tv_different_account).setOnClickListener(this);
        findViewById(R.id.activity_registration_btn_cont).setOnClickListener(this);
        findViewById(R.id.activity_registration_img_btn_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_registration_tv_different_account:
            case R.id.activity_registration_img_btn_back:
                onBackPressed();
                break;
            case R.id.activity_registration_btn_cont:
                if (isInputValid())
                    startActivityForResult(new Intent(RegistrationActivity.this, OtpActivity.class), 3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If the user wants to go straight to the previous activity i.e. LoginActivity.
        if (data != null && data.getStringExtra("key").equals("closeActivity"))
            finish();
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
     * This function checks if the input is valid or not.
     *
     * @return true if input is valid else, false.
     */
    private boolean isInputValid() {
        if (getText(mEditTextFullName).length() == 0 || !getText(mEditTextFullName).contains(" "))
            return showError("Enter full name.");
        else if (getText(mEditTextGender).length() == 0)
            return showError("Enter gender.");
        else if (getText(mEditTextUserType).length() == 0)
            return showError("Enter user type.");
        else if (getText(mEditTextOccupation).length() == 0)
            return showError("Enter occupation.");
        return true;
    }

    /**
     * This function calls the displaySnackbar function and returns false as the input isn't valid.
     *
     * @param error This is the error message, the string which will be displayed on the snackbar.
     * @return This returns false as the input isn't valid.
     */
    private boolean showError(String error) {
        displaySnackBar(error);
        return false;
    }

    /**
     * This function calls the displaySnackbar function in util class.
     *
     * @param displayStr The string to be displayed on the snackbar
     */
    private void displaySnackBar(String displayStr) {
        new Util().displaySnackbar(findViewById(R.id.activity_registration_scroll_view), displayStr);
    }
}
