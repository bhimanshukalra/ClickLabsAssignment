package com.bhimanshukalra.studentmanagementapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private static ArrayList<Student> mStudentsList;
    private static RecyclerClickListener mClickListener;
    private Context mContext;

    public RecyclerViewAdapter(Context context, RecyclerClickListener clickListener) {
        mContext = context;
        mClickListener = clickListener;
    }

    public static void setDataInRecycler(ArrayList<Student> studentsList) {
        mStudentsList = studentsList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.i("RecyclerViewAdapter", "onCreateViewHolder");
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_student, viewGroup, false);
        return new RecyclerViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int position) {
        Log.i("RecyclerViewAdapter", "onBindViewHolder");
        TextView tvName = recyclerViewHolder.mCardView.findViewById(R.id.recycler_list_item_tv_name);
        TextView tvClass = recyclerViewHolder.mCardView.findViewById(R.id.recycler_list_item_tv_class);
        TextView tvRollNum = recyclerViewHolder.mCardView.findViewById(R.id.recycler_list_item_tv_roll_num);
        tvName.setText(mStudentsList.get(position).getName());
        tvClass.setText(mStudentsList.get(position).getClassName());
        tvRollNum.setText(String.valueOf(mStudentsList.get(position).getRollNum()));
    }

    @Override
    public int getItemCount() {
        Log.i("RecyclerViewAdapter", "getItemCount");
        if (mStudentsList != null)
            return mStudentsList.size();
        return 0;
    }

    public interface RecyclerClickListener {

        public void recyclerListClicked(View view, int position);

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView mCardView;

        public RecyclerViewHolder(@NonNull CardView cardView) {
            super(cardView);
            Log.i("RecyclerViewAdapter", "RecyclerViewHolder constructor");
            mCardView = cardView;
            mCardView.setOnClickListener(this);
        }
//        private void openDetailsActivity(Context context, String intentExtra,int position) {
//            Intent intent = new Intent(context, DetailsFormActivity.class);
//            intent.putExtra(context.getString(R.string.key), intentExtra);
//            intent.putExtra(context.getString(R.string.all_student), mStudentsList.get(position));
//            setListItemClickedPosition(position);
//            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_ADAPTER);
//        }

        @Override
        public void onClick(View view) {
            mClickListener.recyclerListClicked(view, this.getLayoutPosition());
        }
    }

}
