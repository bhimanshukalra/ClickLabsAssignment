package com.bhimanshukalra.studentmanagementappfragments.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bhimanshukalra.studentmanagementappfragments.R;
import com.bhimanshukalra.studentmanagementappfragments.model.Student;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ACCESS_MODE;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ACCESS_MODE_VIEW;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ADD_NEW_STUDENT;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.FORM_ADD_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.FORM_UPDATE_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.getTextFromEditText;
import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.isInputValid;

/**
 * The type Form fragment.
 */
public class FormFragment extends Fragment {
    private FormInterface mFormInterface;
    private EditText mEtName;
    private EditText mEtClass;
    private EditText mEtRollNumber;
    private Button mPrimaryBtn;
    private int mPosition;
    private ArrayList<Integer> mRollNumbers;


    /**
     * Instantiates a new Form fragment.
     */
    public FormFragment() {
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

    public void setStudentRollNumberList(ArrayList<Integer> rollNumbers) {
        mRollNumbers = rollNumbers;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Set instance.
     *
     * @param formInterface the form interface
     */
    public void setInstance(FormInterface formInterface) {
        mFormInterface = formInterface;
    }

    /**
     * Set student data.
     *
     * @param student  the student object
     * @param position the mPosition at which student has to added in the list.
     */
    public void setStudentData(Student student, int position) {
        mEtName.setText(student.getName());
        mEtClass.setText(student.getClassName());
        mEtRollNumber.setText(String.valueOf(student.getRollNumber()));
        mPrimaryBtn.setText(FORM_UPDATE_BTN_TEXT);
        this.mPosition = position;
    }

    /**
     * Clear all edit texts.
     */
    public void init() {
        mEtName.setText(EMPTY_STRING);
        mEtClass.setText(EMPTY_STRING);
        mEtRollNumber.setText(EMPTY_STRING);
        mPrimaryBtn.setText(FORM_ADD_BTN_TEXT);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_form, container, false);
        mEtName = view.findViewById(R.id.fragment_form_et_name);
        mEtClass = view.findViewById(R.id.fragment_form_et_student_class);
        mEtRollNumber = view.findViewById(R.id.fragment_form_et_roll_number);
        mPrimaryBtn = view.findViewById(R.id.fragment_form_btn_primary);
        mPosition = -1;

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            Student student = (Student) bundle.getSerializable("student");
            String accessMode = bundle.getString(ACCESS_MODE);
            if (accessMode.equals(ACCESS_MODE_VIEW)) {
                mEtName.setEnabled(false);
                mEtClass.setEnabled(false);
                mEtRollNumber.setEnabled(false);
                mPrimaryBtn.setVisibility(View.GONE);
            }
            if (student != null) {
                mEtName.setText(student.getName());
                mEtClass.setText(student.getClassName());
                mEtRollNumber.setText(String.valueOf(student.getRollNumber()));
            }
        }

        mPrimaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getTextFromEditText(mEtName);
                String className = getTextFromEditText(mEtClass);
                String rollNumInput = getTextFromEditText(mEtRollNumber);
                Button primaryBtn = view.findViewById(R.id.fragment_form_btn_primary);
                String accessMode = primaryBtn.getText().toString();
                if (!isInputValid(view.findViewById(R.id.fragment_details_form_scrollView), name, className, rollNumInput, mRollNumbers, accessMode)) {
                    return;
                }
                Integer rollNumber = Integer.parseInt(rollNumInput);
                mEtName.setText(EMPTY_STRING);
                mEtClass.setText(EMPTY_STRING);
                mEtRollNumber.setText(EMPTY_STRING);
                mFormInterface.getStudentDataViaForm(new Student(name, className, rollNumber), mPosition);
                mPosition = ADD_NEW_STUDENT;
            }
        });
        return view;
    }

    /**
     * The interface Form interface.
     */
    public interface FormInterface {
        /**
         * Gets student data via form.
         *
         * @param student  the student data.
         * @param position the mPosition at which student has to added in the list.
         */
        void getStudentDataViaForm(Student student, int position);
    }

}
