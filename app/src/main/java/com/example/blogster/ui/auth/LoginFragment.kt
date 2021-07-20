package com.example.blogster.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.blogster.data.remote.Resource
import com.example.blogster.data.remote.requests.LoginRequest
import com.example.blogster.data.remote.requests.UserLogInCredentials
import com.example.blogster.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    private val viewModel :AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.onLoginResponse.observe(viewLifecycleOwner){
            Log.d("LoginFrgment Up","${it.data}")
            when(it){
                is Resource.Success->{
                   Log.d("LoginFrgment","${it.data}")
                    binding.progressBar.visibility=View.INVISIBLE
                }
                is Resource.Error->{
                    Log.d("LoginFrgment error","${it.message}")
                }
                else -> {
                    Log.d("LoginFrgment else","${it}")
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility=View.INVISIBLE
                }
            }
        }
    }

    private fun setUpClickListeners() {
        binding.signInBtn.setOnClickListener {
            binding.progressBar.visibility=View.VISIBLE
            viewModel.loginUser(binding.emailEt.text.toString(),binding.passwordEt.text.toString())
        }
    }

}