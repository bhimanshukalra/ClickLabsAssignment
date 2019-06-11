package com.bhimanshukalra.retrofitassignment.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhimanshukalra.retrofitassignment.R;
import com.bhimanshukalra.retrofitassignment.models.Post;

import java.util.List;

import static com.bhimanshukalra.retrofitassignment.constants.constants.NEXT_LINE;

/**
 * The Home list adapter for RecyclerView.
 */
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyViewHolder> {
    private List<Post> mPostList;

    /**
     * Initialize app with postList.
     *
     * @param postList the list containing posts.
     */
    public PostListAdapter(List<Post> postList) {
        mPostList = postList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder((CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        String title = NEXT_LINE + mPostList.get(position).getTitle();
        String body = NEXT_LINE + mPostList.get(position).getBody();
        myViewHolder.tvPostUserId.setText(String.valueOf(mPostList.get(position).getUserId()));
        myViewHolder.tvPostId.setText(String.valueOf(mPostList.get(position).getId()));
        myViewHolder.tvPostTitle.setText(title);
        myViewHolder.tvPostBody.setText(body);
    }

    @Override
    public int getItemCount() {
        if (mPostList != null) {
            return mPostList.size();
        } else {
            return 0;
        }
    }

    /**
     * The type My view holder.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvPostUserId;
        private TextView tvPostId;
        private TextView tvPostTitle;
        private TextView tvPostBody;

        /**
         * Instantiates a new My view holder.
         *
         * @param cardView the card view
         */
        public MyViewHolder(@NonNull CardView cardView) {
            super(cardView);
            tvPostUserId = cardView.findViewById(R.id.item_post_tv_user_id);
            tvPostId = cardView.findViewById(R.id.item_post_tv_id);
            tvPostTitle = cardView.findViewById(R.id.item_post_tv_title);
            tvPostBody = cardView.findViewById(R.id.item_post_tv_body);
        }
    }
}
