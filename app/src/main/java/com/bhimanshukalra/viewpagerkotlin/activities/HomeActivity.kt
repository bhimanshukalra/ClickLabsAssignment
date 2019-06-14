package com.bhimanshukalra.viewpagerkotlin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bhimanshukalra.viewpagerkotlin.R
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.EMPTY_STRING
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.FORM_TAB
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.LIST_TAB
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.TAB_ONE_TITLE
import com.bhimanshukalra.viewpagerkotlin.constants.Constants.Companion.TAB_TWO_TITLE
import com.bhimanshukalra.viewpagerkotlin.fragments.FormFragment
import com.bhimanshukalra.viewpagerkotlin.fragments.ListFragment
import com.bhimanshukalra.viewpagerkotlin.models.FragmentDetails
import com.bhimanshukalra.viewpagerkotlin.models.HomePageAdapter
import com.bhimanshukalra.viewpagerkotlin.models.Student
import com.google.android.material.tabs.TabLayout

class HomeActivity : AppCompatActivity(), FormFragment.FormInterface, ListFragment.ListInterface {

    private lateinit var mViewPager: ViewPager
    private lateinit var mFragmentDetails: List<FragmentDetails>
    private lateinit var mHomePageAdapter: HomePageAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()
    }

    private fun init() {
        initViewPager()
    }

    private fun initViewPager() {
        mViewPager = findViewById(R.id.activity_home_view_pager)
        val tabLayout: TabLayout = findViewById(R.id.activity_home_tab_layout)
        mFragmentDetails = getFragmentList(this)
        mHomePageAdapter = HomePageAdapter(supportFragmentManager, mFragmentDetails)
        mViewPager.adapter = mHomePageAdapter
        tabLayout.setupWithViewPager(mViewPager)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == LIST_TAB) {
                    val formFragment = getFormFragment()
                    formFragment.init()
                }
            }

        }
        )
    }

    private fun getFragmentList(homeActivity: HomeActivity): List<FragmentDetails> {
        val formFragment = FormFragment()
        formFragment.setInstance(homeActivity)
        val listFragment = ListFragment()
        listFragment.setInstance(homeActivity)

        return listOf(
            FragmentDetails(listFragment, TAB_ONE_TITLE),
            FragmentDetails(formFragment, TAB_TWO_TITLE)
        )
    }

    override fun setNewStudent(student: Student) {
        mViewPager.currentItem = LIST_TAB
        val listFragment = mFragmentDetails.get(LIST_TAB).fragment as ListFragment
        listFragment.getNewStudent(student)
    }

    private fun switchToTab(tabNum: Int) {
        mViewPager.currentItem = tabNum
    }

    override fun moveToTabTwo(accessMode: String, student: Student, itemPosition: Int) {
        if (accessMode != EMPTY_STRING) {
            val formFragment = getFormFragment()
            formFragment.setStudentData(accessMode, student, itemPosition)
        }
        switchToTab(FORM_TAB)
    }

    fun getFormFragment(): FormFragment = mFragmentDetails.get(FORM_TAB).fragment as FormFragment

    override fun updateStudent(student: Student, itemPosition: Int) {
        mViewPager.currentItem = LIST_TAB
        val listFragment = mFragmentDetails.get(LIST_TAB).fragment as ListFragment
        listFragment.updateStudent(student, itemPosition)
    }
}

















