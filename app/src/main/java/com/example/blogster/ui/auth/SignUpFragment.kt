package com.example.blogster.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.blogster.MainActivity
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentSignUpBinding
import com.example.blogster.utils.Constants.TOKEN
import com.example.blogster.utils.Constants.USERNAME
import com.example.blogster.utils.PrefsHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.userResponse.observe(viewLifecycleOwner) {
            Log.d("LoginFragment Up", "${it?.data?.email}")
            binding.progressBar?.visibility = View.INVISIBLE
            when (it) {
                is Resource.Success -> {
                    Log.d("LoginFrgment", "${it.data}")
                    Toast.makeText(
                        requireContext(),
                        "User registered successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    val token = it.data?.token
                    val userName = it.data?.username
                    Log.d("SignupFragment token", "$token")
                    if (token != null) {
                        PrefsHelper.write(TOKEN,token)
                    }
                    if (userName != null) {
                        PrefsHelper.write(USERNAME,userName)
                    }

                    startActivity(Intent(requireContext(), MainActivity::class.java))
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
                binding.emailEt.editText?.text.toString(),
                binding.passwordEt.editText?.text.toString(),
                binding.nameEt.editText?.text.toString()
            )
        }
    }
}