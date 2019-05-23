package com.bhimanshukalra.studentmanagementappfragments.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhimanshukalra.studentmanagementappfragments.Adapter.StudentListAdapter;
import com.bhimanshukalra.studentmanagementappfragments.R;
import com.bhimanshukalra.studentmanagementappfragments.activities.ViewDetailsActivity;
import com.bhimanshukalra.studentmanagementappfragments.model.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.bhimanshukalra.studentmanagementappfragments.Adapter.StudentListAdapter.setDataInRecycler;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ACCESS_MODE_UPDATE;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ALERT_DIALOG_MSG_PREFIX;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ALERT_DIALOG_MSG_SUFFIX;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ALERT_DIALOG_NEGATIVE_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ALERT_DIALOG_NEUTRAL_BTN_TEXT;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ALERT_DIALOG_POSITIVE_TBN_TEXT;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.ALERT_DIALOG_TITLE;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.GRID_VIEW_COLS;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.MINIMUM_SIZE_TO_DISPLAY_LIST;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.SIZE_ZERO;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.SORT_BY_NAME;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.SORT_BY_ROLL_NUMBER;
import static com.bhimanshukalra.studentmanagementappfragments.constants.Constants.STUDENT_INTENT_KEY;
import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.hideList;
import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.showList;

/**
 * The type List fragment.
 */
public class ListFragment extends Fragment implements StudentListAdapter.RecyclerClickListener {
    private ListInterface mListInterface;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter = new StudentListAdapter(this);
    private ArrayList<Student> mStudentsList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mTvNoData;

    /**
     * Instantiates a new List fragment.
     */
    public ListFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);
        mRecyclerView = view.findViewById(R.id.fragment_list_recycler_student_list);
        mTvNoData = view.findViewById(R.id.fragment_list_tv_no_data);
        setDataInRecycler(mStudentsList);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        view.findViewById(R.id.fragment_list_btn_primary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListInterface.primaryButtonClickedDetailsList();
            }
        });
        return view;
    }

    public ArrayList<Integer> getStudentRollNumberList() {
        ArrayList<Integer> rollNumbers = new ArrayList<>();
        for (Student student : mStudentsList) {
            rollNumbers.add(student.getRollNumber());
        }
        return rollNumbers;
    }

    /**
     * Set instance.
     *
     * @param detailsListInterface the details list interface
     */
    public void setInstance(ListInterface detailsListInterface) {
        mListInterface = detailsListInterface;
    }

    /**
     * Get student data.
     *
     * @param student    the student data.
     * @param position   the position at which student has to added in the list.
     * @param accessMode the access permissions of the currentItem.
     */
    public void getStudentData(Student student, int position, String accessMode) {
        if (accessMode.equals(ACCESS_MODE_UPDATE)) {
            mStudentsList.remove(position);
            mStudentsList.add(position, student);
            mAdapter.notifyDataSetChanged();
        } else {
            addToList(student);
        }
        if (mStudentsList.size() == MINIMUM_SIZE_TO_DISPLAY_LIST) {
            showList(mRecyclerView, mTvNoData);
        }
    }

    /**
     * Add student to list
     *
     * @param student the student data.
     */
    private void addToList(Student student) {
        mStudentsList.add(student);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_list_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_list_fragment_grid_list_switch:
                MenuView.ItemView menuViewIcon = getActivity().findViewById(R.id.menu_list_fragment_grid_list_switch);
                if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    menuViewIcon.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_grid));
                } else {
                    mLayoutManager = new GridLayoutManager(getActivity(), GRID_VIEW_COLS);
                    menuViewIcon.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_list));
                }
                mRecyclerView.setLayoutManager(mLayoutManager);
                break;
            case R.id.menu_list_fragment_sort_by_name:
                sortList(SORT_BY_NAME);
                break;
            case R.id.menu_list_fragment_gsort_by_roll_number:
                sortList(SORT_BY_ROLL_NUMBER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sort the student list.
     *
     * @param sortMethod Sort by name or roll number.
     */
    private void sortList(String sortMethod) {
        boolean sortByName = true;
        if (sortMethod.equals(SORT_BY_ROLL_NUMBER))
            sortByName = false;
        Collections.sort(mStudentsList, new CustomComparator(sortByName));
        mAdapter.notifyDataSetChanged();
    }

    /**
     * This function is called when an item in list in clicked on.
     *
     * @param view     the view clicked on.
     * @param position the position of clicked item in list.
     */
    @Override
    public void listItemClicked(View view, int position) {
        showAlertDialog(getActivity(), position);
    }

    /**
     * Shows the alert dialog.
     *
     * @param context  of parent view.
     * @param position of item clicked on.
     */
    private void showAlertDialog(final Context context, final int position) {
        new AlertDialog.Builder(context)
                .setTitle(ALERT_DIALOG_TITLE)
                .setMessage(ALERT_DIALOG_MSG_PREFIX + mStudentsList.get(position).getName() + ALERT_DIALOG_MSG_SUFFIX)
                .setNeutralButton(ALERT_DIALOG_NEUTRAL_BTN_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ViewDetailsActivity.class);
                        intent.putExtra(STUDENT_INTENT_KEY, (Serializable) mStudentsList.get(position));
                        startActivity(intent);
                    }
                })
                .setPositiveButton(ALERT_DIALOG_POSITIVE_TBN_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mListInterface.openUpdateForm(position, mStudentsList.get(position));
                    }
                })
                .setNegativeButton(ALERT_DIALOG_NEGATIVE_BTN_TEXT, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mStudentsList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        if (mStudentsList.size() == SIZE_ZERO) {
                            hideList(mRecyclerView, mTvNoData);
                        }
                        mListInterface.itemDeleted();
                    }
                })
                .show();
    }

    /**
     * The interface List interface.
     */
    public interface ListInterface {
        /**
         * Primary button clicked details list.
         */
        void primaryButtonClickedDetailsList();

        /**
         * Open update form.
         *
         * @param position the position at which student has to be added.
         * @param student  the student data.
         */
        void openUpdateForm(int position, Student student);

        /**
         * An item is deleted for the list.
         */
        void itemDeleted();
    }

    /**
     * The Custom comparator class.
     */
    public class CustomComparator implements Comparator<Student> {
        boolean sortByName;

        /**
         * Instantiates a new Custom comparator.
         *
         * @param sortByName if the sortByName boolean is true the we sort by name else by roll number.
         */
        CustomComparator(boolean sortByName) {
            this.sortByName = sortByName;
        }

        @Override
        public int compare(Student student1, Student student2) {
            if (sortByName) {
                return student1.getName().compareTo(student2.getName());
            } else {
                return student1.getRollNumber().compareTo(student2.getRollNumber());
            }
        }
    }
}

