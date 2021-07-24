package com.example.blogster.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.blogster.R
import com.example.blogster.databinding.FragmentFeedBinding
import com.google.android.material.tabs.TabLayoutMediator


class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private lateinit var feedViewPagerAdapter: FeedViewPagerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        feedViewPagerAdapter= FeedViewPagerAdapter(requireActivity())
        binding.pager.adapter = feedViewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "My Feed"
                }
                1 -> {
                    tab.text = "Global Feed"
                }
            }
        }.attach()

    }

}