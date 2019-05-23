package com.bhimanshukalra.studentmanagementappfragments.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhimanshukalra.studentmanagementappfragments.R;
import com.bhimanshukalra.studentmanagementappfragments.model.Student;

import java.util.ArrayList;

/**
 * The Student list RecyclerView adapter.
 */
public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.RecyclerViewHolder> {
    private static ArrayList<Student> mStudentList;
    private static RecyclerClickListener mClickListener;

    /**
     * Instantiates a new Student list adapter.
     *
     * @param clickListener the click listener of list item.
     */
    public StudentListAdapter(RecyclerClickListener clickListener) {
        mClickListener = clickListener;
    }

    /**
     * Set data in recycler.
     *
     * @param studentList the list of student details.
     */
    public static void setDataInRecycler(ArrayList<Student> studentList) {
        mStudentList = studentList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student, viewGroup, false);
        return new RecyclerViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int position) {
        View view = recyclerViewHolder.mCardView;
        TextView tvName = view.findViewById(R.id.item_student_tv_name);
        TextView tvClass = view.findViewById(R.id.item_student_tv_class);
        TextView tvRollNumber = view.findViewById(R.id.item_student_tv_roll_num);

        tvName.setText(mStudentList.get(position).getName());
        tvClass.setText(mStudentList.get(position).getClassName());
        tvRollNumber.setText(String.valueOf(mStudentList.get(position).getRollNumber()));
    }

    @Override
    public int getItemCount() {
        if (mStudentList != null) {
            return mStudentList.size();
        }
        return 0;
    }

    /**
     * The interface Recycler click listener.
     */
    public interface RecyclerClickListener {
        /**
         * List item clicked.
         *
         * @param view     the view clicked on.
         * @param position the position of clicked item in list.
         */
        void listItemClicked(View view, int position);
    }

    /**
     * The type Recycler view holder.
     */
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;

        /**
         * Instantiates a new Recycler view holder.
         *
         * @param cardView the card view showing all the details of individual students.
         */
        public RecyclerViewHolder(@NonNull CardView cardView) {
            super(cardView);
            mCardView = cardView;
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.listItemClicked(view, getLayoutPosition());
                }
            });
        }
    }
}
