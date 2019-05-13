package com.bhimanshukalra.recyclerviewcrud;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<User> mUsersList;

    public MyAdapter(ArrayList<User> usersList) {
        mUsersList = usersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        TextView textViewName = myViewHolder.mCardView.findViewById(R.id.list_item_tv_name);
        TextView textViewAge = myViewHolder.mCardView.findViewById(R.id.list_item_tv_age);
        TextView textViewPhoneNumber = myViewHolder.mCardView.findViewById(R.id.list_item_tv_phone);
        RatingBar ratingBar = myViewHolder.mCardView.findViewById(R.id.list_item_rating_bar);
        ImageView imageButtonProfileUrl = myViewHolder.mCardView.findViewById(R.id.list_item_img_user);
        TextView textViewLikes = myViewHolder.mCardView.findViewById(R.id.list_item_tv_like_count);

        textViewName.setText(mUsersList.get(position).getName());
        textViewAge.setText(String.valueOf(mUsersList.get(position).getAge()));
        textViewPhoneNumber.setText(mUsersList.get(position).getPhoneNumber());
        ratingBar.setRating(mUsersList.get(position).getRating());
        Picasso.get().load(mUsersList.get(position).getProfileUrl()).into(imageButtonProfileUrl);
        textViewLikes.setText(String.valueOf(mUsersList.get(position).getLikes()));
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView mCardView;

        public MyViewHolder(@NonNull CardView cardView) {
            super(cardView);
            mCardView = cardView;
            mCardView.findViewById(R.id.list_item_btn_like).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User user = mUsersList.get(getAdapterPosition());
                    user.setLikes(user.getLikes() + 1);
                    notifyItemChanged(getAdapterPosition());
                }
            });

            RatingBar ratingBar = mCardView.findViewById(R.id.list_item_rating_bar);
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser) {
                        User user = mUsersList.get(getAdapterPosition());
                        user.setRating(rating);
                    }
                }
            });
            mCardView.findViewById(R.id.list_item_img_btn_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUsersList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

        }
    }
}





