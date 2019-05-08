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
        if (data != null && data.getStringExtra("key").equals("closeActivity"))
            finish();
    }

    private String getText(EditText editText) {
        return new Util().getText(editText);
    }

    private boolean isInputValid() {
        if (getText(mEditTextFullName).length() == 0 || !getText(mEditTextFullName).contains(" "))
            return displaySnackBar("Enter full name.");
        else if (getText(mEditTextGender).length() == 0)
            return displaySnackBar("Enter gender.");
        else if (getText(mEditTextUserType).length() == 0)
            return displaySnackBar("Enter user type.");
        else if (getText(mEditTextOccupation).length() == 0)
            return displaySnackBar("Enter occupation.");
        return true;
    }

    private boolean displaySnackBar(String displayStr) {
        new Util().displaySnackbar(findViewById(R.id.activity_registration_scroll_view), displayStr);
        return false;
    }
}
