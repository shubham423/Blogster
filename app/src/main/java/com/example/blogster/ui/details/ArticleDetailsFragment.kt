package com.example.blogster.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.blogster.R
import com.example.blogster.utils.Resource
import com.example.blogster.databinding.FragmentArticleDetailsBinding
import com.example.blogster.ui.articles.ArticlesViewModel
import com.example.blogster.ui.feed.FeedViewModel
import com.example.blogster.utils.Constants.TOKEN
import com.example.blogster.utils.PrefsHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedViewModel by activityViewModels()
    private val authViewModel: ArticlesViewModel by activityViewModels()
    private lateinit var commentsAdapter: CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailsBinding.inflate(layoutInflater)
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
                    val token= PrefsHelper.read(TOKEN,null)

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

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}