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

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.RecyclerViewHolder> {
    private StudentListInterface mStudentListInterface;
    private ArrayList<Student> mStudentList;

    public void setListInRecycler(ArrayList<Student> studentList){
        mStudentList = studentList;
    }

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
        /**
         * TODO: view mapping
         */
        recyclerViewHolder.mTvName.setText(mStudentList.get(position).getStudentName());
        recyclerViewHolder.mTvClass.setText(mStudentList.get(position).getClassName());
        recyclerViewHolder.mTvRollNum.setText(String.valueOf(mStudentList.get(position).getRollNumber()));
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTvName;
        private TextView mTvClass;
        private TextView mTvRollNum;

        public RecyclerViewHolder(@NonNull LinearLayout linearLayout) {
            super(linearLayout);
            mTvName = linearLayout.findViewById(R.id.item_student_tv_name);
            mTvClass = linearLayout.findViewById(R.id.item_student_tv_class);
            mTvRollNum = linearLayout.findViewById(R.id.item_student_tv_roll_num);
            linearLayout.findViewById(R.id.item_student_tv_edit).setOnClickListener(this);
            linearLayout.findViewById(R.id.item_student_tv_delete).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.item_student_tv_edit:
                    mStudentListInterface.editClicked(getAdapterPosition(), mStudentList.get(getAdapterPosition()));
                    break;
                case R.id.item_student_tv_delete:
                    mStudentListInterface.deleteClicked(getAdapterPosition(), mStudentList.get(getAdapterPosition()));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(mStudentList != null) {
            return mStudentList.size();
        }else{
            return 0;
        }
    }

    public interface StudentListInterface{
        void editClicked(int position, Student student);
        void deleteClicked(int position, Student student);
    }

}
