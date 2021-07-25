package com.example.blogster.ui.articles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blogster.R
import com.example.blogster.databinding.FragmentMyArticlesBinding

class MyArticlesFragment : Fragment() {

    private lateinit var binding: FragmentMyArticlesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentMyArticlesBinding.inflate(layoutInflater)
        return binding.root
    }

}