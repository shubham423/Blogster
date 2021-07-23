package com.example.blogster.ui.feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class FeedViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MyFeedFragment()
            1 -> return GlobalFeedFragment()

        }
        return MyFeedFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}
