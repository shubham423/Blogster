package com.example.blogster.ui.articles

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentMyArticlesBinding
import com.example.blogster.ui.auth.AuthViewModel
import com.example.blogster.ui.feed.ArticleFeedAdapter
import com.example.blogster.utils.Constants
import com.example.blogster.utils.PrefsHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyArticlesFragment : Fragment() {

    private lateinit var binding: FragmentMyArticlesBinding
    private val viewModelArticles: ArticlesViewModel by activityViewModels()
    private val viewModelAuth: AuthViewModel by activityViewModels()
    private lateinit var articleAdapter: ArticleFeedAdapter
    var username: String?=null
    var token: String?=null

    private var callback: MyArticleDetailsCallback? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyArticlesBinding.inflate(layoutInflater)
        callback = activity as MyArticleDetailsCallback
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token = PrefsHelper.read(Constants.TOKEN,null)
        username = PrefsHelper.read(Constants.USERNAME,null)

        viewModelArticles.getMyArticles(token!!,username!!)
        setupObservers()
    }

    private fun setupObservers() {

        viewModelArticles.myArticlesResponse.observe(viewLifecycleOwner) { it ->
            when (it) {
                is Resource.Success -> {
                    articleAdapter = ArticleFeedAdapter { openArticle(it) }
                    binding.myArticlesRecyclerView.adapter = articleAdapter
                    articleAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    Log.d("FavoriteFragment1 error", "${it.message}")
                }
                else -> {
                    Log.d("FavoriteFragment1 else", "$it")
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        Log.d("onResume", "token $token and $username ")
        token?.let { username?.let { it1 -> viewModelArticles.getMyArticles(it, it1) } }
    }

    private fun openArticle(articleId: String) {
        callback?.onMyArticleClicked(articleId)
    }
}


interface MyArticleDetailsCallback {
    fun onMyArticleClicked(articleId: String)
}