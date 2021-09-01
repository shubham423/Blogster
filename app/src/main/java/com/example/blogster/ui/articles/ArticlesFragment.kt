package com.example.blogster.ui.articles

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.blogster.R
import com.example.blogster.databinding.FragmentArticlesBinding
import com.example.blogster.ui.feed.FeedViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var articlesViewPagerAdapter: ArticlesViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticlesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        articlesViewPagerAdapter = ArticlesViewPagerAdapter(requireActivity())
        binding.pager.adapter = articlesViewPagerAdapter


        TabLayoutMediator(
            binding.tabLayout, binding.pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "My Articles"
                }
                1 -> {
                    tab.text = "Favorite Articles"
                }
            }
        }.attach()
    }


}