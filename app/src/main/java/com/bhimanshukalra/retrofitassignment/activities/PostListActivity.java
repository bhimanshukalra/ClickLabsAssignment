package com.bhimanshukalra.retrofitassignment.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bhimanshukalra.retrofitassignment.R;
import com.bhimanshukalra.retrofitassignment.adapters.PostListAdapter;
import com.bhimanshukalra.retrofitassignment.models.JsonPlaceHolderApi;
import com.bhimanshukalra.retrofitassignment.models.Post;
import com.bhimanshukalra.retrofitassignment.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bhimanshukalra.retrofitassignment.constants.constants.INTERNET_CONNECTION_ERROR;
import static com.bhimanshukalra.retrofitassignment.constants.constants.INTERNET_ERROR_PRIMARY_BTN;
import static com.bhimanshukalra.retrofitassignment.constants.constants.INTERNET_ERROR_TITLE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.POST_LIST_ACTIVITY_TITLE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.SERVER_ERROR_OCCURRED;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_INTENT_KEY;
import static com.bhimanshukalra.retrofitassignment.utilities.Util.getRetrofitInstance;
import static com.bhimanshukalra.retrofitassignment.utilities.Util.isNetworkConnected;

/**
 * The Post list activity, this activity displays a list of all user's posts.
 */
public class PostListActivity extends AppCompatActivity {
    private List<Post> mPosts;
    private ProgressBar mProgressBar;
    private TextView mTvId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        init();
    }

    /**
     * Initialization function
     */
    private void init() {
        decelerationsAndInitializations();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(POST_LIST_ACTIVITY_TITLE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (!isNetworkConnected(this)) {
            mProgressBar.setVisibility(View.INVISIBLE);
            showInternetErrorDialog();
            return;
        }
        if (mProgressBar.getVisibility() != View.VISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        initRetrofit();
    }

    /**
     * This function pops open a dialog saying "Connectivity error", user can tap on "Try again" to retry.
     */
    private void showInternetErrorDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setTitle(INTERNET_ERROR_TITLE)
                .setMessage(INTERNET_CONNECTION_ERROR)
                .setPositiveButton(INTERNET_ERROR_PRIMARY_BTN, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        init();
                    }
                })
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * This function declares and initializes all views used in this activity.
     */
    private void decelerationsAndInitializations() {
        mProgressBar = findViewById(R.id.activity_post_list_progress_bar);
        mTvId = findViewById(R.id.activity_post_list_tv_roll_num);
        TextView tvName = findViewById(R.id.activity_post_list_tv_name);
        TextView tvEmail = findViewById(R.id.activity_post_list_tv_email);

        Intent intent = getIntent();
        User user = intent.getParcelableExtra(USER_INTENT_KEY);
        mTvId.setText(String.valueOf(user.getId()));
        tvName.setText(user.getName());
        tvEmail.setText(user.getEmail());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This function will only be called when the back button on app bar is clicked.
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    /**
     * This function initializes retrofit.
     */
    private void initRetrofit() {
        JsonPlaceHolderApi jsonPlaceHolderApi = getRetrofitInstance();
        getUserList(jsonPlaceHolderApi);
    }

    /**
     * This function handles API call and depending on the response, displays user list or an error(if occurred).
     */
    private void getUserList(JsonPlaceHolderApi jsonPlaceHolderApi) {
        int userId = Integer.parseInt(mTvId.getText().toString());
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(userId);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PostListActivity.this, SERVER_ERROR_OCCURRED, Toast.LENGTH_SHORT).show();
                    return;
                }
                mPosts = response.body();
                initRecyclerView(mPosts);
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable throwable) {
                Toast.makeText(PostListActivity.this, SERVER_ERROR_OCCURRED, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This function after successful API call, initializes the RecyclerView and displays the user list.
     *
     * @param posts This is the user list fetched from API call.
     */
    private void initRecyclerView(List<Post> posts) {
        RecyclerView recyclerView = findViewById(R.id.activity_post_list_recycler_view);
        PostListAdapter postListAdapter = new PostListAdapter(posts);
        recyclerView.setAdapter(postListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

}
