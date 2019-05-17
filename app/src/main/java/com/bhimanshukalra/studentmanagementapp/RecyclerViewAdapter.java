package com.bhimanshukalra.studentmanagementapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The Recycler view adapter.
 */
class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private static ArrayList<Student> mStudentsList;
    private static RecyclerClickListener mClickListener;

    /**
     * Instantiates a new Recycler view adapter.
     *
     * @param clickListener the clickListener interface initialization
     */
    public RecyclerViewAdapter(RecyclerClickListener clickListener) {
        mClickListener = clickListener;
    }

    /**
     * Sets data in recycler.
     *
     * @param studentsList the students details list
     */
    public static void setDataInRecycler(ArrayList<Student> studentsList) {
        mStudentsList = studentsList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student, viewGroup, false);
        return new RecyclerViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int position) {
        //Get reference of all the textViews.
        TextView tvName = recyclerViewHolder.mCardView.findViewById(R.id.recycler_list_item_tv_name);
        TextView tvClass = recyclerViewHolder.mCardView.findViewById(R.id.recycler_list_item_tv_class);
        TextView tvRollNum = recyclerViewHolder.mCardView.findViewById(R.id.recycler_list_item_tv_roll_num);

        //Set text in all of the textViews.
        tvName.setText(mStudentsList.get(position).getName());
        tvClass.setText(mStudentsList.get(position).getClassName());
        tvRollNum.setText(String.valueOf(mStudentsList.get(position).getRollNum()));
    }

    @Override
    public int getItemCount() {
        if (mStudentsList != null)
            return mStudentsList.size();
        return 0;
    }

    /**
     * The interface Recycler click listener.
     */
    public interface RecyclerClickListener {
        /**
         * Recycler list clicked.
         *
         * @param view     the view tapped on.
         * @param position This is the index of RecyclerView item tapped.
         */
        void recyclerListClicked(View view, int position);
    }

    /**
     * The type Recycler view holder.
     */
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView mCardView;

        /**
         * Instantiates a new Recycler view holder.
         *
         * @param cardView the card view parent of student item of recyclerView
         */
        public RecyclerViewHolder(@NonNull CardView cardView) {
            super(cardView);
            mCardView = cardView;
            mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.recyclerListClicked(view, this.getLayoutPosition());
        }
    }

}
