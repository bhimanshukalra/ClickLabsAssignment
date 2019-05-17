package com.bhimanshukalra.studentmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import static Utilities.Util.createNewStudent;
import static Utilities.Util.giveError;
import static Utilities.Util.isInputValid;

/**
 * The Student Details form activity.
 * Here the user updates and/or inserts student name, class and roll number.
 */
public class DetailsFormActivity extends AppCompatActivity {

    private String mIntentExtrasKey;
    private EditText mEtName, mEtClass, mEtRollNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);
        init();
        findViewById(R.id.activity_detail_from_btn_primary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //There is only one menu item. Hence, no switch.
        finish();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Depending on the use case of activity show/hide/change contents of the primary button.
     */
    private void handlePrimaryButtonProperties() {
        Button primaryBtn = findViewById(R.id.activity_detail_from_btn_primary);
        if (mIntentExtrasKey.equals(getString(R.string.list_dialog_view_intent_extra)))
            primaryBtn.setVisibility(View.GONE);
        if (mIntentExtrasKey.equals(getString(R.string.details_list_new_student_intent_extra)))
            primaryBtn.setText(R.string.form_primary_btn_add);
    }

    /**
     * Handle primary button click
     */
    @SuppressWarnings("unchecked")
    private void handleButtonClick() {
        if (mIntentExtrasKey.equals(getString(R.string.details_list_new_student_intent_extra))) {
            if (isInputValid(this, mEtName, mEtClass, mEtRollNum, findViewById(R.id.details_form_ll))) {
                Intent returnIntent = getIntent();
                Student newStudent = createNewStudent(mEtName, mEtClass, mEtRollNum);
                ArrayList<Integer> studentRollNumList = (ArrayList<Integer>) getIntent().getSerializableExtra(getString(R.string.adapter_intent_list_extra));
                //Check if the roll num entered by the user is already in the list. If yes, then give error, else proceed.
                if (studentRollNumList.contains(newStudent.getRollNum())) {
                    giveError(findViewById(R.id.details_form_ll), getString(R.string.error_student_duplicity));
                    return;
                }
                //Put student details in the returnIntent to display the student in the list.
                returnIntent.putExtra(getString(R.string.key), getString(R.string.details_list_new_student_intent_extra));
                returnIntent.putExtra(getString(R.string.all_student), newStudent);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        } else if (mIntentExtrasKey.equals(getString(R.string.list_dialog_update_intent_extra))) {
            if (isInputValid(this, mEtName, mEtClass, mEtRollNum, findViewById(R.id.details_form_ll))) {
                Intent returnIntent = getIntent();
                Student student = createNewStudent(mEtName, mEtClass, mEtRollNum);
                //Put student details in the returnIntent to display the student in the list.
                returnIntent.putExtra(getString(R.string.key), getString(R.string.list_dialog_update_intent_extra));
                returnIntent.putExtra(getString(R.string.all_student), student);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
    }

    /**
     * The initialization function.
     */
    private void init() {
        if (getSupportActionBar() != null) {
            //Set back button on app bar.
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle(R.string.form_activity_title);
        }
        mIntentExtrasKey = getIntent().getStringExtra(getString(R.string.key));

        mEtName = findViewById(R.id.activity_details_form_et_name);
        mEtClass = findViewById(R.id.activity_detail_form_et_student_class);
        mEtRollNum = findViewById(R.id.activity_detail_form_et_roll_num);

//If status is view or update then getExtras from intent and setText
        if (mIntentExtrasKey.equals(getString(R.string.list_dialog_view_intent_extra)) || mIntentExtrasKey.equals(getString(R.string.list_dialog_update_intent_extra))) {
            if (getIntent().getExtras() == null) {
                return;
            }
            Student student = (Student) getIntent().getExtras().getSerializable(getString(R.string.all_student));
            if (student == null) {
                return;
            }
            //We have a student object. Now, we set these values in the EditTexts.
            mEtName.setText(student.getName());
            mEtClass.setText(student.getClassName());
            mEtRollNum.setText(String.valueOf(student.getRollNum()));
        }
        handlePrimaryButtonProperties();
        if (mIntentExtrasKey.equals(getString(R.string.list_dialog_view_intent_extra))) {
            //If this is view only, then disable editText changes.
            mEtName.setEnabled(false);
            mEtClass.setEnabled(false);
            mEtRollNum.setEnabled(false);
        }
    }
}
