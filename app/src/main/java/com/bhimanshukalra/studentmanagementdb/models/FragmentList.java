package com.bhimanshukalra.studentmanagementdb.models;

import android.support.v4.app.Fragment;

public class FragmentList {
    private String title;
    private Fragment fragment;

    public FragmentList(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
