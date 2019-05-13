package com.bhimanshukalra.recyclerviewcrud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.activity_main_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<User> usersList = SetupUserList();
        adapter = new MyAdapter(usersList);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<User> SetupUserList() {
        ArrayList<User> usersList = new ArrayList<>();
        usersList.add(new User("One", 1, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        usersList.add(new User("Two", 2, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        usersList.add(new User("Three", 3, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        usersList.add(new User("Four", 4, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        usersList.add(new User("Five", 5, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        usersList.add(new User("Six", 6, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        usersList.add(new User("Seven", 7, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        usersList.add(new User("Eight", 8, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        usersList.add(new User("Nine", 9, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        usersList.add(new User("Ten", 10, "123456789", 0, "https://avatars3.githubusercontent.com/u/32154931?s=460&v=4", 0));
        return usersList;
    }
}
