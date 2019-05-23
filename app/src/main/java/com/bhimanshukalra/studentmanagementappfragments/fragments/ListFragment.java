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
import android.widget.Toast;

import com.bhimanshukalra.studentmanagementappfragments.Adapter.StudentListAdapter;
import com.bhimanshukalra.studentmanagementappfragments.R;
import com.bhimanshukalra.studentmanagementappfragments.activities.ViewDetailsActivity;
import com.bhimanshukalra.studentmanagementappfragments.model.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static com.bhimanshukalra.studentmanagementappfragments.Adapter.StudentListAdapter.setDataInRecycler;
import static com.bhimanshukalra.studentmanagementappfragments.utilities.Util.log;

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
        log("onCreateView");
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);
        mRecyclerView = view.findViewById(R.id.fragment_list_recycler_student_list);
        mTvNoData = view.findViewById(R.id.fragment_list_tv_no_data);
        log("onCreateView listFragment");
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
        if (accessMode.equals("update")) {
            mStudentsList.remove(position);
            mStudentsList.add(position, student);
            mAdapter.notifyDataSetChanged();
        } else {
            addToList(student);
        }
        if (mStudentsList.size() == 1) {
            showList();
        }
    }

    /**
     * Show list and hide "No Data" textView.
     */
    private void showList() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mTvNoData.setVisibility(View.GONE);
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
                    mLayoutManager = new GridLayoutManager(getActivity(), 2);
                    menuViewIcon.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_list));
                }
                mRecyclerView.setLayoutManager(mLayoutManager);
                break;
            case R.id.menu_list_fragment_sort_by_name:
                sortList("byName");
                break;
            case R.id.menu_list_fragment_gsort_by_roll_number:
                sortList("byRollNumber");
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
        if (sortMethod.equals("byRollNumber"))
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
                .setTitle("Alert")
                .setMessage("What action would you like to perform on " + mStudentsList.get(position).getName() + "?")
                .setNeutralButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, ViewDetailsActivity.class);
                        intent.putExtra("student", (Serializable) mStudentsList.get(position));
                        startActivity(intent);
                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Update", Toast.LENGTH_SHORT).show();
                        mListInterface.openUpdateForm(position, mStudentsList.get(position));
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();

                        mStudentsList.remove(position);
                        mAdapter.notifyItemRemoved(position);
                        if (mStudentsList.size() == 0) {
                            hideList();
                        }
                    }
                })
                .show();
    }

    /**
     * Hide the list and show "No Data" textView.
     */
    private void hideList() {
        mRecyclerView.setVisibility(View.GONE);
        mTvNoData.setVisibility(View.VISIBLE);
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























