package com.example.blogster.ui.articles

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.blogster.R
import com.example.blogster.data.remote.Resource
import com.example.blogster.data.remote.responses.ArticleCreateRequest
import com.example.blogster.databinding.FragmentCreateArticleBinding
import com.example.blogster.ui.auth.AuthViewModel
import com.example.blogster.ui.feed.ArticleFeedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private lateinit var binding: FragmentCreateArticleBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentCreateArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpClickListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.createArticleResponse.observe(viewLifecycleOwner){
            Log.d("FavoriteFragment1 error", "$it")
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "post created successfully", Toast.LENGTH_SHORT).show()
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

    private fun setUpClickListeners() {
        binding.submitBtn.setOnClickListener {
            val title=binding.titleEt.text.toString()
            val bio=binding.bioEt.text.toString()
            val body=binding.bodyEt.text.toString()

            val preferences =
                requireActivity().getSharedPreferences("BLOGSTER", Context.MODE_PRIVATE)
            val token=preferences.getString("TOKEN", null)

            if (token != null) {
                viewModel.createArticle(ArticleCreateRequest(article = ArticleCreateRequest.Article(body,bio,title = title,tagList = listOf("angular","react"))),token)
            }
        }
    }
}