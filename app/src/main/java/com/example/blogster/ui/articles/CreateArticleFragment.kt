package com.example.blogster.ui.articles

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.blogster.utils.Resource
import com.example.blogster.data.remote.requests.ArticleCreateRequest
import com.example.blogster.databinding.FragmentCreateArticleBinding
import com.example.blogster.utils.Constants.TOKEN
import com.example.blogster.utils.PrefsHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateArticleFragment : Fragment() {

    private lateinit var binding: FragmentCreateArticleBinding
    private val viewModel: ArticlesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpClickListeners()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.createArticleResponse.observe(viewLifecycleOwner) {
            binding.progressBar7.visibility=View.GONE
            Log.d("FavoriteFragment1 error", "$it")
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(
                        requireContext(),
                        "post created successfully",
                        Toast.LENGTH_SHORT
                    ).show()
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
            binding.progressBar7.visibility=View.VISIBLE
            val title = binding.titleEt.text.toString()
            val bio = binding.bioEt.text.toString()
            val body = binding.bodyEt.text.toString()

            val token= PrefsHelper.read(TOKEN,null)

            if (token != null) {
                viewModel.createArticle(
                    ArticleCreateRequest(
                        article = ArticleCreateRequest.Article(
                            body,
                            bio,
                            title = title,
                            tagList = listOf("angular", "react")
                        )
                    ), token
                )
            }
        }
    }
}