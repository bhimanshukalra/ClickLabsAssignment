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

import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.getTextFromEditText;
import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.isInputValid;

/**
 * The type Form fragment.
 */
public class FormFragment extends Fragment {
    private FormInterface mFormInterface;
    private EditText etName;
    private EditText etClass;
    private EditText etRollNumber;
    private Button primaryBtn;
    private int position;


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
     * @param position the position at which student has to added in the list.
     */
    public void setStudentData(Student student, int position) {
        etName.setText(student.getName());
        etClass.setText(student.getClassName());
        etRollNumber.setText(String.valueOf(student.getRollNumber()));
        primaryBtn.setText("Update");
        this.position = position;
    }

    /**
     * Clear all edit texts.
     */
    public void init() {
        etName.setText("");
        etClass.setText("");
        etRollNumber.setText("");
        primaryBtn.setText("Add");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_form, container, false);
        etName = view.findViewById(R.id.fragment_form_et_name);
        etClass = view.findViewById(R.id.fragment_form_et_student_class);
        etRollNumber = view.findViewById(R.id.fragment_form_et_roll_number);
        primaryBtn = view.findViewById(R.id.fragment_form_btn_primary);
        position = -1;

        if (getArguments() != null) {
            Bundle bundle = getArguments();
            Student student = (Student) bundle.getSerializable("student");
            String accessMode = bundle.getString("accessMode");
            if (accessMode.equals("view")) {
                etName.setEnabled(false);
                etClass.setEnabled(false);
                etRollNumber.setEnabled(false);
                primaryBtn.setVisibility(View.GONE);
            }
            if (student != null) {
                etName.setText(student.getName());
                etClass.setText(student.getClassName());
                etRollNumber.setText(String.valueOf(student.getRollNumber()));
            }
        }

        primaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = getTextFromEditText(etName);
                String className = getTextFromEditText(etClass);
                String rollNumInput = getTextFromEditText(etRollNumber);
                if (!isInputValid(view.findViewById(R.id.fragment_details_form_scrollView), name, className, rollNumInput)) {
                    return;
                }
                Integer rollNumber = Integer.parseInt(rollNumInput);
                etName.setText("");
                etClass.setText("");
                etRollNumber.setText("");
                mFormInterface.getStudentDataViaForm(new Student(name, className, rollNumber), position);
                position = -1;
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
         * @param position the position at which student has to added in the list.
         */
        void getStudentDataViaForm(Student student, int position);
    }

}
