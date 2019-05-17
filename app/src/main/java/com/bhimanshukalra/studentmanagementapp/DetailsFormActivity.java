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

import static Utilities.Util.containsSpace;
import static Utilities.Util.displaySnackBar;
import static Utilities.Util.getTextFromEditText;
import static Utilities.Util.isEditTextEmpty;

public class DetailsFormActivity extends AppCompatActivity {

    private String mIntentExtrasKey;
    private EditText mEtName, mEtClass, mEtRollNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);
/**
 * TODO: Add back buttton on appbar
 * TODO: Change visiblity setting of text and recycler
 */
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setTitle(R.string.form_activity_title);
        }
//        else
//            log("app bar null");
        mIntentExtrasKey = getIntent().getStringExtra(getString(R.string.key));
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
        //There is only one btn. Hence no switch.
        finish();
        return super.onOptionsItemSelected(item);
    }

    private void handlePrimaryButtonProperties() {
        Button primaryBtn = findViewById(R.id.activity_detail_from_btn_primary);
        if (mIntentExtrasKey.equals(getString(R.string.list_dialog_view_intent_extra)))
            primaryBtn.setVisibility(View.GONE);
        if (mIntentExtrasKey.equals(getString(R.string.details_list_new_student_intent_extra)))
            primaryBtn.setText(R.string.form_primary_btn_add);
    }

    private void handleButtonClick() {
        if (mIntentExtrasKey.equals(getString(R.string.details_list_new_student_intent_extra))) {
            if (isInputValid()) {
                Intent returnIntent = getIntent();
                Student newStudent = createNewStudent();
                ArrayList<Integer> studentRollNumList = (ArrayList<Integer>) getIntent().getSerializableExtra(getString(R.string.adapter_intent_list_extra));
                if (studentRollNumList.contains(newStudent.getRollNum())) {
                    giveError("This student already exists.");
                    return;
                }
//                for(Integer rollNum : studentRollNumList){
//                    if(student.getRollNum().equals(newStudent.getRollNum())){
//                        giveError("This student already exists.");
//                        return;
//                    }
//                }
                returnIntent.putExtra(getString(R.string.key), getString(R.string.details_list_new_student_intent_extra));
                returnIntent.putExtra(getString(R.string.all_student), newStudent);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        } else if (mIntentExtrasKey.equals(getString(R.string.list_dialog_update_intent_extra))) {
            if (isInputValid()) {
                Intent returnIntent = getIntent();
                Student student = createNewStudent();
                returnIntent.putExtra(getString(R.string.key), getString(R.string.list_dialog_update_intent_extra));
                returnIntent.putExtra(getString(R.string.all_student), student);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }
    }

    private boolean isInputValid() {
        if (!isEditTextEmpty(mEtName)) {
            if (!containsSpace(getTextFromEditText(mEtName))) {
                return giveError("Please enter full name.");
            }
        } else {
            return giveError("Please enter name.");
        }
        if (isEditTextEmpty(mEtClass)) {
            return giveError("Please enter class.");
        }
        if (isEditTextEmpty(mEtRollNum)) {
            return giveError("Please enter roll number.");
        }
        return true;
    }

    private boolean giveError(String error) {
        displaySnackBar(findViewById(R.id.details_form_ll), error);
        return false;
    }
    //TODO: Make seperate arraylist for the roll no unique check

    private Student createNewStudent() {
        Student student = null;
        String name = getTextFromEditText(mEtName);
        String className = getTextFromEditText(mEtClass);
        int rollNum = Integer.parseInt(getTextFromEditText(mEtRollNum));
        student = new Student(name, className, rollNum);
        return student;
    }

    private void init() {
        mEtName = findViewById(R.id.activity_details_form_et_name);
        mEtClass = findViewById(R.id.activity_detail_form_et_student_class);
        mEtRollNum = findViewById(R.id.activity_detail_form_et_roll_num);

        if (mIntentExtrasKey.equals(getString(R.string.list_dialog_view_intent_extra)) ||
                mIntentExtrasKey.equals(getString(R.string.list_dialog_update_intent_extra))) {
            Student student = (Student) getIntent().getExtras().getSerializable(getString(R.string.all_student));
            mEtName.setText(student.getName());
            mEtClass.setText(student.getClassName());
            mEtRollNum.setText(String.valueOf(student.getRollNum()));
        }
        handlePrimaryButtonProperties();
        if (mIntentExtrasKey.equals(getString(R.string.list_dialog_view_intent_extra))) {
            mEtName.setEnabled(false);
            mEtClass.setEnabled(false);
            mEtRollNum.setEnabled(false);
        }
    }
}
