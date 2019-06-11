package com.bhimanshukalra.retrofitassignment.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhimanshukalra.retrofitassignment.R;
import com.bhimanshukalra.retrofitassignment.models.Post;
import com.bhimanshukalra.retrofitassignment.models.User;

import java.util.List;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
    private List<User> mUserList;
    private List<Post> mPostList;
    private ListClickListener mListClickListener;

    public void setUserList(List<User> userList){
        mUserList = userList;
    }

    public void setPostList(List<Post> postList){
        mPostList = postList;
    }

    public void setInstance(ListClickListener listClickListener){
        mListClickListener = listClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder((CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_user, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        if(mUserList != null) {
            myViewHolder.tvRight.setText(String.valueOf(mUserList.get(position).getId()));
            myViewHolder.tvCenter.setText(mUserList.get(position).getPrimaryUserDetails());
        }else if(mPostList != null){
            myViewHolder.tvCenter.setText(mPostList.get(position).getPostDetails());
            myViewHolder.tvCenter.setGravity(Gravity.NO_GRAVITY);
            myViewHolder.tvRight.setVisibility(View.GONE);
            myViewHolder.imgUser.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if(mUserList != null) {
            return mUserList.size();
        }else if(mPostList != null){
            return mPostList.size();
        }else{
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRight;
        private TextView tvCenter;
        private ImageView imgUser;

        public MyViewHolder(@NonNull CardView cardView) {
            super(cardView);
            tvCenter = cardView.findViewById(R.id.item_user_tv_center);
            tvRight = cardView.findViewById(R.id.item_user_tv_right);
            imgUser = cardView.findViewById(R.id.item_user_img_user);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = mUserList.get(getAdapterPosition());
                    mListClickListener.listItemClicked(user.getId(), user.getName(), user.getEmail());
                }
            });
        }
    }
    public interface ListClickListener{
        void listItemClicked(int id, String name, String email);
    }
}
