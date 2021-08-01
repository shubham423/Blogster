package com.example.blogster.ui.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentGlobalFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GlobalFeedFragment : Fragment() {
    private lateinit var binding: FragmentGlobalFeedBinding
    private lateinit var articleFeedAdapter: ArticleFeedAdapter
    private val viewModel: FeedViewModel by activityViewModels()
    private var callback: ArticleDetailsCallback?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentGlobalFeedBinding.inflate(layoutInflater)
        callback = activity as ArticleDetailsCallback
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchGlobalFeed()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.feed.observe(viewLifecycleOwner){
            Log.d("GLobalFeedFragment ", "${it.data}")
            when(it){
                is Resource.Success->{
                    articleFeedAdapter=ArticleFeedAdapter({openArticle(it)})
                    binding.feedRecyclerView.adapter=articleFeedAdapter
                    articleFeedAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    Log.d("GLobalFeed error", "${it.message}")
                }
                else -> {
                    Log.d("GLobalFeedFragment else", "$it")
                }
            }
        }
    }

    private fun openArticle(articleId: String) {
        callback?.onArticleClicked(articleId)
    }

}