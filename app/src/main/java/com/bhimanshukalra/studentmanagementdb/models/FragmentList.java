package com.bhimanshukalra.studentmanagementdb.models;

import android.support.v4.app.Fragment;

/**
 * The type Fragment list.
 */
public class FragmentList {
    private String title;
    private Fragment fragment;

    /**
     * Instantiates a new Fragment list.
     *
     * @param title    the title
     * @param fragment the fragment
     */
    public FragmentList(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets fragment.
     *
     * @return the fragment
     */
    public Fragment getFragment() {
        return fragment;
    }
}
