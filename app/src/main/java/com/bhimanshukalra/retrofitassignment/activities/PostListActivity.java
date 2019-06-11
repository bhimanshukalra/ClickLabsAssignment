package com.bhimanshukalra.retrofitassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhimanshukalra.retrofitassignment.R;
import com.bhimanshukalra.retrofitassignment.adapters.HomeListAdapter;
import com.bhimanshukalra.retrofitassignment.models.JsonPlaceHolderApi;
import com.bhimanshukalra.retrofitassignment.models.Post;
import com.bhimanshukalra.retrofitassignment.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhimanshukalra.retrofitassignment.constants.constants.SERVER_ERROR_OCCURED;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_INTENT_KEY;
import static com.bhimanshukalra.retrofitassignment.utilities.Util.getRetrofitInstance;

public class PostListActivity extends AppCompatActivity {
    private List<Post> mPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        init();
    }

    private void init() {
        ScrollView scrollView = findViewById(R.id.list_layout_scroll_view);
        scrollView.setVisibility(View.GONE);

        CardView userDetails = findViewById(R.id.list_layout_card_view_user_details);
        userDetails.setVisibility(View.VISIBLE);

        TextView mTvId = findViewById(R.id.list_layout_card_view_tv_roll_num);
        TextView  mTvName = findViewById(R.id.list_layout_card_view_tv_name);
        TextView  mTvEmail = findViewById(R.id.list_layout_card_view_tv_email);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra(USER_INTENT_KEY);
        mTvId.setText(String.valueOf(user.getId()));
        mTvName.setText(user.getName());
        mTvEmail.setText(user.getEmail());

        initRetrofit();
    }

    private void initRetrofit() {
        JsonPlaceHolderApi jsonPlaceHolderApi = getRetrofitInstance();
        getUserList(jsonPlaceHolderApi);
    }

    private void getUserList(JsonPlaceHolderApi jsonPlaceHolderApi) {
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(1);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PostListActivity.this, SERVER_ERROR_OCCURED, Toast.LENGTH_SHORT).show();
                    return;
                }
                mPosts = response.body();
                initRecyclerView(mPosts);
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable throwable) {
                Toast.makeText(PostListActivity.this, SERVER_ERROR_OCCURED, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(List<Post> posts) {
        RecyclerView recyclerView = findViewById(R.id.list_layout_recycler_view);
        HomeListAdapter homeListAdapter = new HomeListAdapter();
        homeListAdapter.setPostList(posts);
        recyclerView.setAdapter(homeListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

}
