package com.example.blogster.ui.feed

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentMyFeedBinding
import com.example.blogster.utils.Constants
import com.example.blogster.utils.PrefsHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFeedFragment : Fragment() {
    private lateinit var binding: FragmentMyFeedBinding
    private lateinit var feedAdapter: ArticleFeedAdapter
    private val viewModel: FeedViewModel by activityViewModels()
    private var callback: ArticleDetailsCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyFeedBinding.inflate(layoutInflater)
        callback = activity as ArticleDetailsCallback
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//
//        val preferences =
//            requireActivity().getSharedPreferences("BLOGSTER", Context.MODE_PRIVATE)
//        val token = preferences.getString("TOKEN", null)

        val token= PrefsHelper.read(Constants.TOKEN,null)

        if (token != null) {
            viewModel.fetchMyFeed(token)
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.myFeed.observe(viewLifecycleOwner) {
            Log.d("MyFeedFragment ", "${it.data}")
            when (it) {
                is Resource.Success -> {
                    feedAdapter = ArticleFeedAdapter({ openArticle(it) })
                    binding.feedRecyclerView.adapter = feedAdapter
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
        callback?.onArticleClicked(articleId)
    }

}
interface ArticleDetailsCallback {
    fun onArticleClicked(articleId: String)
}

