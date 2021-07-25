package com.example.blogster.ui.articles

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ArticlesViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return MyArticlesFragment()
            1 -> return FavoriteArticlesFragment()

        }
        return MyArticlesFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}
