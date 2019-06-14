package com.bhimanshukalra.viewpagerkotlin.fragments


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bhimanshukalra.viewpagerkotlin.R
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.ALERT_DIALOG_MSG_PREFIX
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.ALERT_DIALOG_MSG_SUFFIX
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.ALERT_DIALOG_NEGATIVE_BTN
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.ALERT_DIALOG_NEUTRAL_BTN
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.ALERT_DIALOG_POSITIVE_BTN
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.ALERT_DIALOG_TITLE
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.EMPTY_STRING
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.READ_OPERATION
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.UPDATE_OPERATION
import com.bhimanshukalra.viewpagerkotlin.models.Student
import com.bhimanshukalra.viewpagerkotlin.models.StudentListAdapter
import com.bhimanshukalra.viewpagerkotlin.utilities.Util.Companion.myLog

class ListFragment : Fragment(), StudentListAdapter.RecyclerClickListener {

    private lateinit var mStudentList: MutableList<Student>
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListAdapter: StudentListAdapter
    private lateinit var mTvNoData: TextView
    private lateinit var mListInterface: ListInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)
        mRecyclerView = view.findViewById(R.id.fragment_list_recycler_student_list)
        mTvNoData = view.findViewById(R.id.fragment_list_tv_no_data)
        mStudentList = mutableListOf()
        mListAdapter = StudentListAdapter(mStudentList, this)
        mRecyclerView.adapter = mListAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        val primaryBtn: Button = view.findViewById(R.id.fragment_list_btn_primary)
        primaryBtn.setOnClickListener {
            mListInterface.moveToTabTwo()
        }
        return view
    }

    fun setInstance(listInterface: ListInterface) {
        mListInterface = listInterface
    }

    fun getNewStudent(student: Student) {
        mStudentList.add(student)
        mListAdapter.notifyDataSetChanged()

        if (mStudentList.size == 1) {
            mRecyclerView.visibility = View.VISIBLE
            mTvNoData.visibility = View.GONE
        }
    }

    fun updateStudent(student: Student, position: Int) {
        mStudentList.set(position, student)
        mListAdapter.notifyDataSetChanged()
    }

    interface ListInterface {
        fun moveToTabTwo(accessMode: String = EMPTY_STRING, student: Student = Student(), itemPosition: Int = -1)
    }

    override fun listItemClicked(position: Int) {
        displayAlertDialog(position)
        myLog("$position")
    }

    private fun displayAlertDialog(position: Int) {
        AlertDialog.Builder(context)
            .setTitle(ALERT_DIALOG_TITLE)
            .setMessage("$ALERT_DIALOG_MSG_PREFIX ${mStudentList[position].name} $ALERT_DIALOG_MSG_SUFFIX")
            .setNeutralButton(
                ALERT_DIALOG_NEUTRAL_BTN,
                DialogInterface.OnClickListener { dialog, which ->
                    kotlin.run {
                        mListInterface.moveToTabTwo(READ_OPERATION, mStudentList[position])
                    }
                })
            .setPositiveButton(ALERT_DIALOG_POSITIVE_BTN,
                DialogInterface.OnClickListener { dialog, which ->
                    kotlin.run {
                        mListInterface.moveToTabTwo(UPDATE_OPERATION, mStudentList[position], position)
                    }
                })
            .setNegativeButton(ALERT_DIALOG_NEGATIVE_BTN,
                DialogInterface.OnClickListener { dialog, which ->
                    kotlin.run {
                        mStudentList.removeAt(position)
                        mListAdapter.notifyDataSetChanged()
                        if (mStudentList.size == 0) {
                            mRecyclerView.visibility = View.GONE
                            mTvNoData.visibility = View.VISIBLE
                        }
                    }
                })
            .show()
    }

}
