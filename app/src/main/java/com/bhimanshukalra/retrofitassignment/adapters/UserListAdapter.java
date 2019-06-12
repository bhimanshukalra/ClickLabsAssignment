package com.bhimanshukalra.retrofitassignment.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhimanshukalra.retrofitassignment.R;
import com.bhimanshukalra.retrofitassignment.models.User;

import java.util.List;

/**
 * The Home list adapter for RecyclerView.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    private List<User> mUserList;
    private ListClickListener mListClickListener;

    /**
     * Initialize app with user list.
     *
     * @param userList the list containing user details.
     */
    public UserListAdapter(List<User> userList) {
        mUserList = userList;
    }

    /**
     * Set instance, for callback when a user is selected.
     *
     * @param listClickListener the list click listener
     */
    public void setInstance(ListClickListener listClickListener) {
        mListClickListener = listClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder((CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.tvUserId.setText(String.valueOf(mUserList.get(position).getId()));
        myViewHolder.tvUserName.setText(String.valueOf(mUserList.get(position).getName()));
        myViewHolder.tvUserEmail.setText(mUserList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        if (mUserList != null) {
            return mUserList.size();
        } else {
            //Fallback
            return 0;
        }
    }

    /**
     * The interface List click listener.
     */
    public interface ListClickListener {
        /**
         * This function will be called when a list item will be clicked.
         *
         * @param id    the id of user selected.
         * @param name  the name of user selected.
         * @param email the email of user selected.
         */
        void listItemClicked(int id, String name, String email);
    }

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUserId;
        private TextView tvUserName;
        private TextView tvUserEmail;

        /**
         * Instantiates a new My view holder.
         *
         * @param cardView the card view
         */
        public MyViewHolder(@NonNull CardView cardView) {
            super(cardView);
            //post list will not be null, when UserListActivity is using the adapter. Hence, we initialize the TextViews of item_post.
            tvUserId = cardView.findViewById(R.id.item_user_tv_id);
            tvUserName = cardView.findViewById(R.id.item_user_tv_name);
            tvUserEmail = cardView.findViewById(R.id.item_user_tv_email);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Display user details in the second half of screen.
                    User user = mUserList.get(getAdapterPosition());
                    mListClickListener.listItemClicked(user.getId(), user.getName(), user.getEmail());
                }
            });
        }
    }
}
