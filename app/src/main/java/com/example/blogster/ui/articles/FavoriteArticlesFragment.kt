package com.example.blogster.ui.articles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentFavoriteArticlesBinding
import com.example.blogster.ui.auth.AuthViewModel
import com.example.blogster.ui.feed.ArticleDetailsCallback
import com.example.blogster.ui.feed.ArticleFeedAdapter
import com.example.blogster.utils.Constants.TOKEN
import com.example.blogster.utils.PrefsHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteArticlesFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteArticlesBinding
    private val viewModelArticles : ArticlesViewModel by activityViewModels()
    private val viewModelAuth : AuthViewModel by activityViewModels()
    private lateinit var articleAdapter: ArticleFeedAdapter
    private var callback: ArticleDetailsCallback?=null

    private var token : String?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFavoriteArticlesBinding.inflate(layoutInflater)
        callback= activity as ArticleDetailsCallback
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token=PrefsHelper.read(TOKEN,null)

        if (token != null) {
            viewModelAuth.getCurrentUser(token!!)
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModelAuth.userResponse.observe(viewLifecycleOwner){
            Log.d("FavoriteFragment error", "${it}")
            when (it) {
                is Resource.Success -> {
                    it.data?.username?.let { it1 -> token?.let { it2 ->
                        viewModelArticles.getFavoriteArticles(
                            it2, it1)
                    } }
                }
                is Resource.Error -> {
                    Log.d("FavoriteFragment error", "${it.message}")
                }
                else -> {
                    Log.d("FavoriteFragment else", "$it")
                }
            }
        }

        viewModelArticles.articlesResponse.observe(viewLifecycleOwner){
            Log.d("FavoriteFragment1 error", "$it")
            when (it) {
                is Resource.Success -> {
                    articleAdapter=ArticleFeedAdapter { openArticle(it) }
                    binding.favoriteRecyclerView.adapter=articleAdapter
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

    private fun openArticle(articleId: String) {
        callback?.onArticleClicked(articleId)
    }

}