package com.example.blogster.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentProfileBinding
import com.example.blogster.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userResponse.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Success -> {
                    binding?.apply {
                        bioEditText.setText(it.data?.bio ?: "")
                        emailEditText.setText(it.data?.email ?: "")
                        usernameEditText.setText(it.data?.username ?: "")
                        imageEditText.setText(it.data?.image ?: "")
                    }
                }
                is Resource.Error -> {
                    Log.d("LoginFragment error", "${it.message}")

                }

                else -> {


                }                }
            }
    }

    private fun setUpClickListeners() {
        binding.apply {
            submitButton.setOnClickListener {
                viewModel.update(
                    bio = bioEditText.text.toString(),
                    username = usernameEditText.text.toString().takeIf { it.isNotBlank() },
                    image = imageEditText.text.toString(),
                    email = emailEditText.text.toString().takeIf { it.isNotBlank() },
                    password = passwordEditText.text.toString().takeIf { it.isNotBlank() }
                )
            }
        }
    }
}