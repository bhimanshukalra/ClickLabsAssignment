package com.bhimanshukalra.studentmanagementappfragments.utilities;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bhimanshukalra.studentmanagementappfragments.Adapter.HomePageAdapter;
import com.bhimanshukalra.studentmanagementappfragments.activities.HomeActivity;
import com.bhimanshukalra.studentmanagementappfragments.fragments.FormFragment;
import com.bhimanshukalra.studentmanagementappfragments.fragments.ListFragment;

import java.util.ArrayList;

import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.INPUT_CLASS_ERROR;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.INPUT_FULL_NAME_ERROR;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.INPUT_NAME_ERROR;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.INPUT_ROLL_NUMBER_ERROR;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.INPUT_ROLL_NUMBER_EXISTS;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.LIST_FRAGMENT_POSITION;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.TAB_ONE_TITLE;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.TAB_ONE_TITLE_PREFIX;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.TAB_ONE_TITLE_SUFFIX;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.TAB_TWO_TITLE;

/**
 * The type Util.
 */
public class Util {
    /**
     * Get text from edit text string.
     *
     * @param editText the edit text
     * @return the string
     */
    public static String getTextFromEditText(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static void displaySnackBar(View parentView, String displayString) {
        Snackbar.make(parentView, displayString, Snackbar.LENGTH_LONG).show();
    }

    public static boolean isInputValid(View parentView, String name, String className, String rollNumber, ArrayList<Integer> rollNumberList, String accessMode) {
        if (name.isEmpty()) {
            displaySnackBar(parentView, INPUT_NAME_ERROR);
            return false;
        } else if (!name.contains(" ")) {
            displaySnackBar(parentView, INPUT_FULL_NAME_ERROR);
            return false;
        } else if (className.isEmpty()) {
            displaySnackBar(parentView, INPUT_CLASS_ERROR);
            return false;
        } else if (rollNumber.isEmpty()) {
            displaySnackBar(parentView, INPUT_ROLL_NUMBER_ERROR);
            return false;
        } else if (rollNumberList.contains(Integer.parseInt(rollNumber))) {
            displaySnackBar(parentView, INPUT_ROLL_NUMBER_EXISTS);
            if (accessMode.equals("Add")) {
                return false;
            }
        }
        return true;
    }

    /**
     * The function creates an ArrayList containing the tab heading.
     *
     * @return The ArrayList containing names of tab headings.
     */
    public static ArrayList<String> getFragmentTitles() {
        ArrayList<String> fragmentTitles = new ArrayList<>();
        fragmentTitles.add(TAB_ONE_TITLE);
        fragmentTitles.add(TAB_TWO_TITLE);
        return fragmentTitles;
    }

    /**
     * The function creates an ArrayList containing the fragment objects.
     *
     * @return The ArrayList containing reference of the list and form fragment.
     */
    public static ArrayList<Fragment> getFragments(HomeActivity homeActivity) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ListFragment listFragment = new ListFragment();
        listFragment.setInstance(homeActivity);
        fragments.add(listFragment);
        FormFragment formFragment = new FormFragment();
        formFragment.setInstance(homeActivity);
        fragments.add(formFragment);
        return fragments;
    }

    public static void setStudentCountInTab(int studentCount, ArrayList<String> fragmentTitles, HomePageAdapter homePageAdapter) {
        if (studentCount >= 1) {
            fragmentTitles.remove(LIST_FRAGMENT_POSITION);
            fragmentTitles.add(LIST_FRAGMENT_POSITION, TAB_ONE_TITLE_PREFIX + studentCount + TAB_ONE_TITLE_SUFFIX);
            homePageAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Show list and hide "No Data" textView.
     */
    public static void showList(RecyclerView recyclerView, TextView tvNoData) {
        recyclerView.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);
    }

    /**
     * Hide the list and show "No Data" textView.
     */
    public static void hideList(RecyclerView recyclerView, TextView tvNoData) {
        recyclerView.setVisibility(View.GONE);
        tvNoData.setVisibility(View.VISIBLE);
    }

}

