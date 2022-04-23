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
import androidx.navigation.fragment.findNavController
import com.example.blogster.MainActivity
import com.example.blogster.R
import com.example.blogster.data.remote.Resource
import com.example.blogster.databinding.FragmentLoginBinding
import com.example.blogster.utils.Constants.TOKEN
import com.example.blogster.utils.Constants.USERNAME
import com.example.blogster.utils.PrefsHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.userResponse.observe(viewLifecycleOwner) {
            Log.d("LoginFragment Up", "${it?.data?.email}")
            when (it) {
                is Resource.Success -> {
                    Log.d("LoginFrgment", "${it.data}")
                    Toast.makeText(
                        requireContext(),
                        "User Logged in successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    val token = it.data?.token
                    if (token != null) {
                        PrefsHelper.write(TOKEN,token)
                        PrefsHelper.write(USERNAME,it.data.username)
                    }

                    startActivity(Intent(requireContext(), MainActivity::class.java)).also {
                        requireActivity().finish()
                    }
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Error -> {
                    Log.d("LoginFragment error", "${it.message}")
                    binding.progressBar.visibility = View.INVISIBLE
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                else -> {
                    Log.d("LoginFragment else", "$it")
                }
            }
        }
    }

    private fun setUpClickListeners() {
        binding.signInBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.loginUser(
                binding.emailEt.text.toString(),
                binding.passwordEt.text.toString()
            )
        }

        binding.signUpTv.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_signUpFragment2)
        }
    }

}