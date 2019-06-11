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
import android.widget.Button;
import android.widget.ProgressBar;
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

import static com.bhimanshukalra.retrofitassignment.constants.constants.ALERT_DIALOG_TITLE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.ERROR_NO_USER_SELECTED;
import static com.bhimanshukalra.retrofitassignment.constants.constants.HOME_ACTIVITY_TITLE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.INTERNET_CONNECTION_ERROR;
import static com.bhimanshukalra.retrofitassignment.constants.constants.INTERNET_ERROR_PRIMARY_BTN;
import static com.bhimanshukalra.retrofitassignment.constants.constants.INTERNET_ERROR_TITLE;
import static com.bhimanshukalra.retrofitassignment.constants.constants.SERVER_ERROR_OCCURRED;
import static com.bhimanshukalra.retrofitassignment.constants.constants.TV_ROLL_NUM_LABEL;
import static com.bhimanshukalra.retrofitassignment.constants.constants.USER_INTENT_KEY;
import static com.bhimanshukalra.retrofitassignment.utilities.Util.getRetrofitInstance;
import static com.bhimanshukalra.retrofitassignment.utilities.Util.isNetworkConnected;

/**
 * The Home Page activity.
 */
public class HomeActivity extends AppCompatActivity implements HomeListAdapter.ListClickListener, View.OnClickListener {
    private TextView mTvId;
    private TextView mTvName;
    private TextView mTvEmail;
    private List<User> mUsers;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    /**
     * Initialization function
     */
    private void init() {
        decelerationsAndInitializations();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(HOME_ACTIVITY_TITLE);
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
        mTvId = findViewById(R.id.activity_home_tv_roll_num);
        mTvName = findViewById(R.id.activity_home_tv_name);
        mTvEmail = findViewById(R.id.activity_home_tv_email);
        mProgressBar = findViewById(R.id.activity_home_progress_bar);
        findViewById(R.id.activity_home_btn_details).setOnClickListener(this);
        findViewById(R.id.activity_home_btn_posts).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Check if any user is selected.
        if (mTvId.getText().toString().trim().equals(TV_ROLL_NUM_LABEL)) {
            Toast.makeText(this, ERROR_NO_USER_SELECTED, Toast.LENGTH_SHORT).show();
            return;
        }
        int id = Integer.parseInt(mTvId.getText().toString().trim()) - 1;
        switch (view.getId()) {
            case R.id.activity_home_btn_details:
                showDetailsAlertDialog(id);
                break;
            case R.id.activity_home_btn_posts:
                Intent postListIntent = new Intent(HomeActivity.this, PostListActivity.class);
                postListIntent.putExtra(USER_INTENT_KEY, mUsers.get(id));
                startActivity(postListIntent);
                break;
        }
    }

    /**
     * This function pops open a dialog showing user details.
     *
     * @param id This is the id of user whose details have to be displayed.
     */
    private void showDetailsAlertDialog(int id) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogStyle)
                .setTitle(ALERT_DIALOG_TITLE)
                .setView(R.layout.layout_alert_dialog)
                .show();

        //Set text in dialog.
        TextView tvMessage = alertDialog.findViewById(R.id.layout_alert_dialog_tv_primary);
        if (tvMessage != null) {
            tvMessage.setText(mUsers.get(id).getAllUserDetails());
        }

        //Set alert dialog button click listener.
        Button alertDialogPrimaryBtn = alertDialog.findViewById(R.id.layout_alert_dialog_btn_primary);
        if (alertDialogPrimaryBtn != null) {
            alertDialogPrimaryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
        }
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
        Call<List<User>> call = jsonPlaceHolderApi.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, SERVER_ERROR_OCCURRED, Toast.LENGTH_SHORT).show();
                    return;
                }
                mUsers = response.body();
                initRecyclerView(mUsers);
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable throwable) {
                Toast.makeText(HomeActivity.this, SERVER_ERROR_OCCURRED, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * This function after successful API call, initializes the RecyclerView and displays the user list.
     *
     * @param users This is the user list fetched from API call.
     */
    private void initRecyclerView(List<User> users) {
        RecyclerView recyclerView = findViewById(R.id.activity_home_recycler_view);
        HomeListAdapter homeListAdapter = new HomeListAdapter(users);
        homeListAdapter.setInstance(this);
        recyclerView.setAdapter(homeListAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * This function is called when a list item is tapped.
     *
     * @param id    the id of user selected.
     * @param name  the name of user selected.
     * @param email the email of user selected.
     */
    @Override
    public void listItemClicked(int id, String name, String email) {
        mTvId.setText(String.valueOf(id));
        mTvName.setText(name);
        mTvEmail.setText(email);
    }
}
