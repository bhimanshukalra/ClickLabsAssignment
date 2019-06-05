package com.bhimanshukalra.studentmanagementdb.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bhimanshukalra.studentmanagementdb.models.FragmentList;

import java.util.ArrayList;

/**
 * The Home page adapter (ViewPager adapter).
 */
public class HomePageAdapter extends FragmentStatePagerAdapter {
    private ArrayList<FragmentList> mFragmentList;

    /**
     * Instantiates a new Home page adapter.
     *
     * @param fragmentManager the fragment manager
     * @param fragmentList    the fragment list containing reference to fragments and there titles.
     */
    public HomePageAdapter(FragmentManager fragmentManager, ArrayList<FragmentList> fragmentList) {
        super(fragmentManager);
        mFragmentList = fragmentList;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).getTitle();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
