package com.bhimanshukalra.viewpagerkotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bhimanshukalra.viewpagerkotlin.R
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.EMPTY_STRING
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.FORM_BTN_TEXT
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.READ_OPERATION
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.UPDATE_OPERATION
import com.bhimanshukalra.viewpagerkotlin.models.Student

class FormFragment : Fragment() {

    private lateinit var mFormInterface: FormInterface
    private lateinit var mTvName: TextView
    private lateinit var mTvClass: TextView
    private lateinit var mTvRollNumber: TextView
    private lateinit var mPrimaryBtn: Button
    private lateinit var mAccessMode: String
    private var mItemPosition: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form, container, false)
        mPrimaryBtn = view.findViewById(R.id.fragment_form_btn_primary)
        mTvName = view.findViewById(R.id.fragment_form_et_name)
        mTvClass = view.findViewById(R.id.fragment_form_et_student_class)
        mTvRollNumber = view.findViewById(R.id.fragment_form_et_roll_number)
        mPrimaryBtn.setOnClickListener {
            val name = mTvName.text.toString()
            val className = mTvClass.text.toString()
            val rollNum = mTvRollNumber.text.toString().toInt()
            if (mPrimaryBtn.text.equals(UPDATE_OPERATION)) {
                mFormInterface.updateStudent(Student(name, className, rollNum), mItemPosition)
            } else {
                mFormInterface.setNewStudent(Student(name, className, rollNum))
            }
        }
        return view
    }

    fun setStudentData(accessMode: String, student: Student, itemPosition: Int) {
        mTvName.text = student.name
        mTvClass.text = student.className
        mTvRollNumber.text = student.rollNumber.toString()
        mAccessMode = accessMode
        mItemPosition = itemPosition
        if (accessMode.equals(READ_OPERATION)) {
            mTvName.isEnabled = false
            mTvClass.isEnabled = false
            mTvRollNumber.isEnabled = false
            mPrimaryBtn.visibility = View.GONE
        } else if (accessMode.equals(UPDATE_OPERATION)) {
            mTvRollNumber.isEnabled = false
            mPrimaryBtn.text = UPDATE_OPERATION
        }
    }

    fun init() {
        mTvName.text = EMPTY_STRING
        mTvClass.text = EMPTY_STRING
        mTvRollNumber.text = EMPTY_STRING

        mTvName.isEnabled = true
        mTvClass.isEnabled = true
        mTvRollNumber.isEnabled = true

        mPrimaryBtn.text = FORM_BTN_TEXT
    }

    fun setInstance(formInterface: FormInterface) {
        mFormInterface = formInterface
    }

    interface FormInterface {
        fun setNewStudent(student: Student)
        fun updateStudent(student: Student, itemPosition: Int)
    }
}
