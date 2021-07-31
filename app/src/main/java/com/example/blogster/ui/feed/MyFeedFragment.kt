package com.example.blogster.ui.feed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.blogster.MainActivity
import com.example.blogster.R
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentMyFeedBinding
import com.example.blogster.ui.articles.FavoriteArticlesFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFeedFragment : Fragment() {
    private lateinit var binding : FragmentMyFeedBinding
    private lateinit var feedAdapter: ArticleFeedAdapter
    private val viewModel: FeedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentMyFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences =
            requireActivity().getSharedPreferences("BLOGSTER", Context.MODE_PRIVATE)
        val token=preferences.getString("TOKEN", null)

        if (token != null) {
            viewModel.fetchMyFeed(token)
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.myFeed.observe(viewLifecycleOwner){
            Log.d("MyFeedFragment ", "${it.data}")
            when(it){
                is Resource.Success->{
                    feedAdapter=ArticleFeedAdapter({openArticle(it)})
                    binding.feedRecyclerView.adapter=feedAdapter
                    feedAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    Log.d("MyFeedFragment error", "${it.message}")
                }
                else -> {
                    Log.d("MyFeedFragment else", "$it")
                }
            }
        }
    }

    private fun openArticle(articleId: String) {
        val action= MyFeedFragmentDirections.actionMyFeedFragmentToArticleDetailsFragment(articleId)
        findNavController().navigate(action)
    }

}

