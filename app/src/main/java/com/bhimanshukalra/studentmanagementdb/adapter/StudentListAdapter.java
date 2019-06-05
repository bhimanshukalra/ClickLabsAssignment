package com.bhimanshukalra.studentmanagementdb.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhimanshukalra.studentmanagementdb.R;
import com.bhimanshukalra.studentmanagementdb.models.Student;

import java.util.ArrayList;

/**
 * The Student list adapter (RecyclerView adapter).
 */
public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.RecyclerViewHolder> {
    private StudentListInterface mStudentListInterface;
    private ArrayList<Student> mStudentList;

    /**
     * Set list in recycler.
     *
     * @param studentList the student list to be set in recyclerView
     */
    public void setListInRecycler(ArrayList<Student> studentList){
        mStudentList = studentList;
    }

    /**
     * Set instance for interface.
     *
     * @param studentListInterface the student list interface
     */
    public void setInstance(StudentListInterface studentListInterface){
        mStudentListInterface = studentListInterface;
    }

    @NonNull
    @Override
    public StudentListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student, viewGroup, false);
        return new RecyclerViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.RecyclerViewHolder recyclerViewHolder, int position) {
        recyclerViewHolder.mTvName.setText(mStudentList.get(position).getStudentName());
        recyclerViewHolder.mTvClass.setText(mStudentList.get(position).getClassName());
        recyclerViewHolder.mTvRollNum.setText(String.valueOf(mStudentList.get(position).getRollNumber()));
    }

    @Override
    public int getItemCount() {
        if (mStudentList != null) {
            return mStudentList.size();
        } else {
            return 0;
        }
    }

    /**
     * The interface Student list interface.
     */
    public interface StudentListInterface {
        /**
         * When a list item is clicked this function is called.
         *
         * @param student  the student on which user tapped.
         * @param position the position of student in the list.
         */
        void listItemClicked(Student student, int position);
    }

    /**
     * The Recycler view holder.
     */
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvName;
        private TextView mTvClass;
        private TextView mTvRollNum;

        /**
         * Instantiates a new Recycler view holder.
         *
         * @param linearLayout the ViewGroup linear layout
         */
        public RecyclerViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            mTvName = linearLayout.findViewById(R.id.item_student_tv_name);
            mTvClass = linearLayout.findViewById(R.id.item_student_tv_class);
            mTvRollNum = linearLayout.findViewById(R.id.item_student_tv_roll_num);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //When a list it is clicked this is called.
                    mStudentListInterface.listItemClicked(mStudentList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
        }

    }

}
