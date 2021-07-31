package com.example.blogster.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentArticleDetailsBinding
import com.example.blogster.ui.feed.FeedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private lateinit var binding: FragmentArticleDetailsBinding
    private val args: ArticleDetailsFragmentArgs by navArgs()
    private val viewModel: FeedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding= FragmentArticleDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleId=args.articleId
        articleId.let {
            viewModel.getArticle(it)
        }

        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.articleDetailResponse.observe(viewLifecycleOwner){
            Log.d("ArticleDetails ", "${it.data}")
            when(it){
                is Resource.Success->{
                    binding.titleTextView.text = it.data?.title
                    binding.bodyTextView.text = it.data?.body
                    binding.authorTextView.text = it.data?.author?.username
                    binding.dateTextView.text = it.data?.createdAt
                    binding.avatarImageView.load(it.data?.author?.image)

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