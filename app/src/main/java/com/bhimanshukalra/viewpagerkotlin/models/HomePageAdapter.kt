package com.bhimanshukalra.viewpagerkotlin.models

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class HomePageAdapter(
    fragmentManager: FragmentManager,
    var fragmentDetails: List<FragmentDetails>
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragmentDetails.get(position).fragment
    }

    override fun getCount(): Int {
        return fragmentDetails.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentDetails.get(position).title
    }
}
