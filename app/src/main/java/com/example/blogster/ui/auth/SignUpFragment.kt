package com.example.blogster.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.blogster.MainActivity
import com.example.blogster.R
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.onSignUpResponse.observe(viewLifecycleOwner) {
            Log.d("LoginFragment Up", "${it.data?.email}")
            when (it) {
                is Resource.Success -> {
                    Log.d("LoginFrgment", "${it.data}")
                    Toast.makeText(
                        requireContext(),
                        "User registered successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    binding.progressBar?.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    Log.d("LoginFragment error", "${it.message}")
                    binding.progressBar?.visibility = View.INVISIBLE
                }
                is Resource.Loading -> {
                    binding.progressBar?.visibility = View.VISIBLE
                }
                else -> {
                    Log.d("LoginFragment else", "$it")
                }
            }
        }
    }

    private fun initClickListeners() {
        binding.signInBtn.setOnClickListener {
            binding.progressBar?.visibility = View.VISIBLE
            viewModel.signInUser(
                binding.emailEt.text.toString(),
                binding.passwordEt.text.toString(),
                binding.nameEt.text.toString()
            )


        }
    }

}