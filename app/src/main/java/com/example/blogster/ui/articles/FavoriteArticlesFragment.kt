package com.example.blogster.ui.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.blogster.MainActivity
import com.example.blogster.R
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentArticlesBinding
import com.example.blogster.databinding.FragmentFavoriteArticlesBinding
import com.example.blogster.ui.auth.AuthViewModel
import com.example.blogster.ui.feed.ArticleDetailsCallback
import com.example.blogster.ui.feed.ArticleFeedAdapter
import com.example.blogster.ui.feed.GlobalFeedFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteArticlesFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteArticlesBinding
    private val viewModel : AuthViewModel by activityViewModels()
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
        val preferences =
            requireActivity().getSharedPreferences("BLOGSTER", Context.MODE_PRIVATE)
        token=preferences.getString("TOKEN", null)

        if (token != null) {
            viewModel.getCurrentUser(token!!)
            Log.d("FavoriteArticlesFrag", "###################### $token")
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userResponse.observe(viewLifecycleOwner){
            Log.d("FavoriteFragment error", "${it}")
            when (it) {
                is Resource.Success -> {
                    it.data?.username?.let { it1 -> token?.let { it2 ->
                        viewModel.getFavoriteArticles(
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

        viewModel.articlesResponse.observe(viewLifecycleOwner){
            Log.d("FavoriteFragment1 error", "$it")
            when (it) {
                is Resource.Success -> {
                    articleAdapter=ArticleFeedAdapter({openArticle(it)})
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