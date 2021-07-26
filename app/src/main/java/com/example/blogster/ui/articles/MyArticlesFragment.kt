package com.example.blogster.ui.articles

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.blogster.R
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentFavoriteArticlesBinding
import com.example.blogster.databinding.FragmentMyArticlesBinding
import com.example.blogster.ui.auth.AuthViewModel
import com.example.blogster.ui.feed.ArticleFeedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyArticlesFragment : Fragment() {

    private lateinit var binding: FragmentMyArticlesBinding
    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var articleAdapter: ArticleFeedAdapter

    private var token : String?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentMyArticlesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preferences =
            requireActivity().getSharedPreferences("BLOGSTER", Context.MODE_PRIVATE)
        token=preferences.getString("TOKEN", null)

        if (token != null) {
            viewModel.getCurrentUser(token!!)
            Log.d("MyArticlesFrag", "###################### $token")
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userResponse.observe(viewLifecycleOwner){
            Log.d("MyArticlesFrag error", "$it")
            when (it) {
                is Resource.Success -> {
                    it.data?.username?.let { it1 -> token?.let { it2 ->
                        viewModel.getMyArticles(
                            it2, it1)
                    } }
                }
                is Resource.Error -> {
                    Log.d("MyArticlesFrag error", "${it.message}")
                }
                else -> {
                    Log.d("MyArticlesFrag else", "$it")
                }
            }
        }

        viewModel.articlesResponse.observe(viewLifecycleOwner){
            Log.d("FavoriteFragment1 error", "$it")
            when (it) {
                is Resource.Success -> {
                    articleAdapter=ArticleFeedAdapter()
                    binding.myArticlesRecyclerView.adapter=articleAdapter
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


}