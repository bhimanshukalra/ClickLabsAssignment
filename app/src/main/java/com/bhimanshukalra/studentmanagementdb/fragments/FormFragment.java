package com.bhimanshukalra.studentmanagementdb.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bhimanshukalra.studentmanagementdb.R;
import com.bhimanshukalra.studentmanagementdb.backgroundTasks.MyAsyncTask;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ADD_FORM_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.ASYNCTASK;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.BACKGROUND_TASK_HANDLER;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.CREATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.EMPTY_STRING;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.INTENT_SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.SERVICE;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_FORM_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATE_OPERATION;
import static com.bhimanshukalra.studentmanagementdb.constants.Constants.UPDATION_IN_LIST;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.getTextFromEditText;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.intentServiceBroadcast;
import static com.bhimanshukalra.studentmanagementdb.utilities.Util.serviceBroadcast;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormFragment extends Fragment {
    private FormInterface mFormInterface;
    private EditText mEtName;
    private EditText mEtClass;
    private EditText mEtRollNumber;
    private Button primaryBtn;

    int count = 0;

    public FormFragment() {
        // Required empty public constructor
    }

    public void setInstance(FormInterface clickListener) {
        mFormInterface = clickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        init(view);
//        mFormInterface.showKeyboard(mEtName);

//        mEtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                    mFormInterface.showKeyboard();
//                }
//            }
//        });

        return view;
    }

    private void init(View view) {

        mEtName = view.findViewById(R.id.fragment_form_et_name);
        mEtClass = view.findViewById(R.id.fragment_form_et_class_name);
        mEtRollNumber = view.findViewById(R.id.fragment_form_et_roll_number);
        primaryBtn = view.findViewById(R.id.fragment_form_btn_primary);

        view.findViewById(R.id.fragment_form_btn_primary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Fetch input from user.
                String name = getTextFromEditText(mEtName);
                String className = getTextFromEditText(mEtClass);
                int rollNumber = Integer.parseInt(getTextFromEditText(mEtRollNumber));

                UPDATION_IN_LIST = true;
                //boolean value to trigger db select statement only when a new student is added to db.

                Student student = new Student(name, className, rollNumber);

                if(primaryBtn.getText().equals(ADD_FORM_BTN_TEXT)) {
                    //Add new student to db.
                    if(BACKGROUND_TASK_HANDLER == ASYNCTASK) {
                        new MyAsyncTask(CREATE_OPERATION, getActivity(), null).execute(student);
                    }else if(BACKGROUND_TASK_HANDLER == SERVICE) {
                        serviceBroadcast(getActivity(), CREATE_OPERATION, student);
                    }else if(BACKGROUND_TASK_HANDLER == INTENT_SERVICE){
                        intentServiceBroadcast(getActivity(), CREATE_OPERATION, student);
                    }
                }else if(primaryBtn.getText().equals(UPDATE_FORM_BTN_TEXT)){
                    //Update student details.
                    if(BACKGROUND_TASK_HANDLER == ASYNCTASK) {
                        new MyAsyncTask(UPDATE_OPERATION, getActivity(), null).execute(student);
                    }else if(BACKGROUND_TASK_HANDLER == SERVICE) {
                        serviceBroadcast(getActivity(), UPDATE_OPERATION, student);
                    }else if(BACKGROUND_TASK_HANDLER == INTENT_SERVICE){
                        intentServiceBroadcast(getActivity(), UPDATE_OPERATION, student);
                    }
                }
//                mFormInterface.switchToTab(LIST_TAB);
                clearAllEditTexts();
            }
        });
    }



    public void initBtn() {
        primaryBtn.setText(ADD_FORM_BTN_TEXT);

    }

    public void clearAllEditTexts() {
        mEtName.setText(EMPTY_STRING);
        mEtClass.setText(EMPTY_STRING);
        mEtRollNumber.setText(EMPTY_STRING);
    }

    public interface FormInterface {
        void showKeyboard(EditText etName);

        void switchToTab(int tabNum);
    }

    public void setStudentData(Student student) {
//        log("setStudentData");
        mEtName.setText(student.getStudentName());
        mEtClass.setText(student.getClassName());
        mEtRollNumber.setText(String.valueOf(student.getRollNumber()));
        primaryBtn.setText(UPDATE_FORM_BTN_TEXT);
    }

}
















