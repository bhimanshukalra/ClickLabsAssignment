package com.bhimanshukalra.studentmanagementappfragments.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * The Home page ViewPager adapter.
 */
public class HomePageAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mFragmentTitles;

    /**
     * Instantiates a new Home page adapter.
     *
     * @param fm             the FragmentManager
     * @param fragments      the fragments reference list
     * @param fragmentTitles the fragment titles list
     */
    public HomePageAdapter(FragmentManager fm, List<Fragment> fragments, List<String> fragmentTitles) {
        super(fm);
        mFragments = fragments;
        mFragmentTitles = fragmentTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
