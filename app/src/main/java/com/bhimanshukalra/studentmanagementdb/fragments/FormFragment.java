package com.bhimanshukalra.studentmanagementdb.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bhimanshukalra.studentmanagementdb.R;
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyAsyncTask;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ADD_FORM_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ASYNC_TASK;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BACKGROUND_TASK_HANDLER;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BUNDLE_STUDENT_KEY;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.FORM_EMPTY_EDIT_TEXT_ERROR;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_FORM_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATION_IN_LIST;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.getTextFromEditText;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.isInputValid;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.startDbIntentService;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.startDbService;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment {
    private EditText mEtName;
    private EditText mEtClass;
    private EditText mEtRollNumber;
    private Button primaryBtn;

    /**
     * Instantiates a new Form fragment.
     */
    public FormFragment() {
        // Required empty public constructor
    }

    /**
     * New instance form fragment.
     *
     * @param bundle the bundle passed from the activity.
     * @return the form fragment with bundle as arguments (if bundle isn't null).
     */
    public static FormFragment newInstance(final Bundle bundle) {
        FormFragment formFragment = new FormFragment();

        if (bundle != null) {
            formFragment.setArguments(bundle);
        }
        return formFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mEtName = view.findViewById(R.id.fragment_form_et_name);
        mEtClass = view.findViewById(R.id.fragment_form_et_class_name);
        mEtRollNumber = view.findViewById(R.id.fragment_form_et_roll_number);
        primaryBtn = view.findViewById(R.id.fragment_form_btn_primary);
        primaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick();
            }
        });

//        Only for the ViewDetails Activity
        if (getArguments() != null) {
            Bundle bundle = getArguments();
//            Get the student details.
            Student student = bundle.getParcelable(BUNDLE_STUDENT_KEY);
//            String accessMode = bundle.getString(ACCESS_MODE);
//            if (ACCESS_MODE_VIEW.equals(accessMode)) {
//            Set the EditTexts to view-only mode.
            mEtName.setEnabled(false);
            mEtClass.setEnabled(false);
            mEtRollNumber.setEnabled(false);
            primaryBtn.setVisibility(View.GONE);
//            }
//            Set student details in the EditTexts.
            if (student != null) {
                mEtName.setText(student.getStudentName());
                mEtClass.setText(student.getClassName());
                mEtRollNumber.setText(String.valueOf(student.getRollNumber()));
            }
        }
    }

    private void handleClick() {
        if (!isInputValid(mEtName, mEtClass, mEtRollNumber)) {
            Toast.makeText(getActivity(), FORM_EMPTY_EDIT_TEXT_ERROR, Toast.LENGTH_SHORT).show();
            return;
        }
//        Fetch input from user.
        String name = getTextFromEditText(mEtName);
        String className = getTextFromEditText(mEtClass);
        int rollNumber = Integer.parseInt(getTextFromEditText(mEtRollNumber));

        UPDATION_IN_LIST = true;
//        boolean value to trigger db select statement only when a new student is added to db.

        Student student = new Student(name, className, rollNumber);

        if (primaryBtn.getText().equals(ADD_FORM_BTN_TEXT)) {
//            Add new student to db.
            if (BACKGROUND_TASK_HANDLER == ASYNC_TASK) {
                new MyAsyncTask(CREATE_OPERATION, getActivity()).execute(student);
            } else if (BACKGROUND_TASK_HANDLER == SERVICE) {
                startDbService(getActivity(), CREATE_OPERATION, student);
            } else if (BACKGROUND_TASK_HANDLER == INTENT_SERVICE) {
                startDbIntentService(getActivity(), CREATE_OPERATION, student);
            }
        } else if (primaryBtn.getText().equals(UPDATE_FORM_BTN_TEXT)) {
//            Update student details.
            if (BACKGROUND_TASK_HANDLER == ASYNC_TASK) {
                new MyAsyncTask(UPDATE_OPERATION, getActivity()).execute(student);
            } else if (BACKGROUND_TASK_HANDLER == SERVICE) {
                startDbService(getActivity(), UPDATE_OPERATION, student);
            } else if (BACKGROUND_TASK_HANDLER == INTENT_SERVICE) {
                startDbIntentService(getActivity(), UPDATE_OPERATION, student);
            }
        }
    }

    /**
     * Initialize button, set it to "Add Button".
     */
    public void initBtn() {
        primaryBtn.setText(ADD_FORM_BTN_TEXT);
    }

    /**
     * Clear all edit texts.
     */
    public void clearAllEditTexts() {
        mEtName.setText(EMPTY_STRING);
        mEtName.requestFocus();
        mEtClass.setText(EMPTY_STRING);
        mEtRollNumber.setText(EMPTY_STRING);
        if (!mEtRollNumber.isEnabled()) {
            mEtRollNumber.setEnabled(true);
        }
    }

    /**
     * Sets student data is called when "Edit" mode is enabled.
     *
     * @param student the student details to be displayed.
     */
    public void setStudentData(Student student) {
        mEtName.setText(student.getStudentName());
        mEtClass.setText(student.getClassName());
        mEtRollNumber.setText(String.valueOf(student.getRollNumber()));
//        Disable change in student roll number
        mEtRollNumber.setEnabled(false);
//        Set button to say "Update".
        primaryBtn.setText(UPDATE_FORM_BTN_TEXT);
    }

}

