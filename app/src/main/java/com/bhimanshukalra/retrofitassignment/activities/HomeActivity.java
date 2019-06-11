package com.bhimanshukalra.retrofitassignment.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bhimanshukalra.retrofitassignment.R;
import com.bhimanshukalra.retrofitassignment.adapters.HomeListAdapter;
import com.bhimanshukalra.retrofitassignment.models.JsonPlaceHolderApi;
import com.bhimanshukalra.retrofitassignment.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.bhimanshukalra.retrofitassignment.constants.constants.ALERT_DIALOG_TITLE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.BASE_URL;
import static com.bhimanshukalra.retrofitassignment.constants.constants.ERROR_NO_USER_SELECTED;
import static com.bhimanshukalra.retrofitassignment.constants.constants.SERVER_ERROR_OCCURED;
import static com.bhimanshukalra.retrofitassignment.constants.constants.TV_ROLL_NUM_LABEL;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_INTENT_KEY;

public class HomeActivity extends AppCompatActivity implements HomeListAdapter.ListClickListener, View.OnClickListener {
    private JsonPlaceHolderApi mJsonPlaceHolderApi;
    private TextView mTvId;
    private TextView mTvName;
    private TextView mTvEmail;
    private List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
        init();
    }

    private void init() {
        mTvId = findViewById(R.id.list_layout_tv_roll_num);
        mTvName = findViewById(R.id.list_layout_tv_name);
        mTvEmail = findViewById(R.id.list_layout_tv_email);
        initRetrofit();
        findViewById(R.id.list_layout_btn_details).setOnClickListener(this);
        findViewById(R.id.list_layout_btn_posts).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mTvId.getText().toString().trim().equals(TV_ROLL_NUM_LABEL)) {
            Toast.makeText(this, ERROR_NO_USER_SELECTED, Toast.LENGTH_SHORT).show();
            return;
        }
        int id = Integer.parseInt(mTvId.getText().toString().trim()) - 1;
        switch (view.getId()) {
            case R.id.list_layout_btn_details:
                new AlertDialog.Builder(this)
                        .setTitle(ALERT_DIALOG_TITLE)
                        .setMessage(mUsers.get(id).getAllUserDetails())
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
            case R.id.list_layout_btn_posts:
                Intent postListIntent = new Intent(HomeActivity.this, PostListActivity.class);
                postListIntent.putExtra(USER_INTENT_KEY, mUsers.get(id));
                startActivity(postListIntent);
                break;
        }
    }

    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mJsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getUserList();
    }

    private void getUserList() {
        Call<List<User>> call = mJsonPlaceHolderApi.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, SERVER_ERROR_OCCURED, Toast.LENGTH_SHORT).show();
                    return;
                }
                mUsers = response.body();
                initRecyclerView(mUsers);
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable throwable) {
                Toast.makeText(HomeActivity.this, SERVER_ERROR_OCCURED, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView(List<User> users) {
        RecyclerView recyclerView = findViewById(R.id.list_layout_recycler_view);
        HomeListAdapter homeListAdapter = new HomeListAdapter();
        homeListAdapter.setUserList(users);
        homeListAdapter.setInstance(this);
        recyclerView.setAdapter(homeListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void listItemClicked(int id, String name, String email) {
        mTvId.setText(String.valueOf(id));
        mTvName.setText(name);
        mTvEmail.setText(email);
    }
}
