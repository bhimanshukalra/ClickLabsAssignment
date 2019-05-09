package com.bhimanshukalra.loginapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import utilities.Util;

/**
 * This activity is for Login.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextEmail, mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextEmail = findViewById(R.id.activity_login_et_email);
        mEditTextPassword = findViewById(R.id.activity_login_et_password);

        findViewById(R.id.activity_login_tv_register).setOnClickListener(this);
        findViewById(R.id.activity_login_btn_login).setOnClickListener(this);
        findViewById(R.id.activity_login_img_btn_password_eye).setOnClickListener(this);
    }

    /**
     * All editTexts are emptied and focus is set to the first one.
     */
    @Override
    protected void onStart() {
        super.onStart();
        mEditTextEmail.setText("");
        mEditTextEmail.requestFocus();
        mEditTextPassword.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login_tv_register:
                startActivityForResult(new Intent(LoginActivity.this, RegistrationActivity.class), 2);
                break;
            case R.id.activity_login_btn_login:
                validateInput();
                break;
            case R.id.activity_login_img_btn_password_eye:
                togglePasswordVisibility();
                break;
        }
    }

    /**
     * If password is obscured, it will be revealed, else if it is revealed then it will be obscured.
     */
    private void togglePasswordVisibility() {
        //129 is the inputType code for obscured version of password.
        if (mEditTextPassword.getInputType() == 129)
            mEditTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        else
            mEditTextPassword.setInputType(129);
    }

    /**
     * This function will call the getText function in util class.
     *
     * @param editText the EditText for which the function will return the containing text.
     * @return Text contained in the given editText.
     */
    private String getText(EditText editText) {
        return new Util().getText(editText);
    }

    /**
     * This function will check if the emailId and password entered are valid.
     */
    private void validateInput() {
        if (getText(mEditTextEmail).isEmpty())
            displaySnackbar("Enter email Id.");
        else if (isEmailInvalid(getText(mEditTextEmail)))
            displaySnackbar("Incorrect email Id.");
        else if (getText(mEditTextPassword).length() < 6)
            displaySnackbar("Password should be greater than 5 characters.");
        else {
            hideKeyBoard();
            displaySnackbar("Logged in successfully.");
        }
    }

    /**
     * This function will call the display Snackbar function in the util class
     *
     * @param displayStr the string which will be displayed on the Snackbar.
     */
    private void displaySnackbar(String displayStr) {
        new Util().displaySnackbar(findViewById(R.id.activity_login_scroll_view), displayStr);
    }

    /**
     * This function simply hides the soft keyboard.
     */
    private void hideKeyBoard() {
            new Util().hideSoftKeyboard(mEditTextEmail);
            new Util().hideSoftKeyboard(mEditTextPassword);
    }

    /**
     * This function checks the validation of emailId entered.
     *
     * @param email This is the emailId entered by user.
     * @return if emailId is valid or not.
     */
    private boolean isEmailInvalid(String email) {
        int count = 0;
        if (email.contains("@"))
            count++;
        if (email.contains(".com"))
            count++;
        if (email.indexOf(".com") - email.indexOf("@") > 1)
            count++;
        if (!email.contains(" "))
            count++;
        return count != 4;
    }
}
