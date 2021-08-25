package com.example.blogster.ui.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.blogster.R
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentArticleDetailsBinding
import com.example.blogster.databinding.FragmentMyArticleDetailsBinding
import com.example.blogster.databinding.FragmentMyArticlesBinding
import com.example.blogster.ui.auth.AuthViewModel
import com.example.blogster.ui.feed.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyArticlesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMyArticleDetailsBinding

    private val viewModel: FeedViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyArticleDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val articleId = it.getString(resources.getString(R.string.arg_article_id))
            if (articleId != null) {
                viewModel.getArticle(articleId)
            }
        }
        setUpObservers()
    }
    private fun setUpObservers() {
        viewModel.articleDetailResponse.observe(viewLifecycleOwner) {
            Log.d("ArticleDetails ", "${it.data}")
            when (it) {
                is Resource.Success -> {
                    binding.titleTextView.text = it.data?.title
                    binding.bodyTextView.text = it.data?.body
                    binding.authorTextView.text = it.data?.author?.username
                    binding.dateTextView.text = it.data?.createdAt
                    binding.avatarImageView.load(it.data?.author?.image)

                    val preferences =
                        requireActivity().getSharedPreferences("BLOGSTER", Context.MODE_PRIVATE)
                    val token = preferences.getString("TOKEN", null)

                    if (token != null) {
                        it.data?.let { it1 -> authViewModel.getArticleComments(it1.slug,token) }
                        Log.d("Articles Details", "###################### $token")
                    }


                }
                is Resource.Error -> {
                    Log.d("ArticleDetails error", "${it.message}")
                }
                else -> {
                    Log.d("ArticleDetails else", "$it")
                }
            }
        }

        authViewModel.articleCommentsResponse.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Success -> {
                    Log.d("ArticleDetails list", "${it.data!!}")
                    commentsAdapter = CommentsAdapter()
                    binding.commentRv.visibility=View.VISIBLE
                    binding.commentRv.adapter = commentsAdapter
                    commentsAdapter.submitList(it.data)
                }
                is Resource.Error -> {

                    Log.d("ArticleDetails error", "${it.message}")
                }
                else -> {
                    Log.d("ArticleDetails else", "$it")
                }
            }
        }
    }
}