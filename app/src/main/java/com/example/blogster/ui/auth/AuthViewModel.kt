package com.example.blogster.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogster.data.remote.Resource
import com.example.blogster.data.remote.responses.User
import com.example.blogster.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {
    private val _onLoginResponse = MutableLiveData<Resource<User>>()
    val onLoginResponse: LiveData<Resource<User>> = _onLoginResponse

    private val _onSignUpResponse = MutableLiveData<Resource<User>>()
    val onSignUpResponse: LiveData<Resource<User>> = _onSignUpResponse


    fun loginUser(email: String, password: String) {
        _onLoginResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = mainRepository.loginUser(email, password)
            Log.d("viewmodel", "${response} and $email")
            if (response != null) {
                _onLoginResponse.postValue(Resource.Success(response))
            } else {
                _onLoginResponse.postValue(Resource.Error("response is null"))
            }

        }
    }

    fun signInUser(email: String, password: String, username: String) {
        _onSignUpResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = mainRepository.signUpUser(email, password, username)
            if (response != null) {
                _onSignUpResponse.postValue(Resource.Success(response))
            } else {
                _onLoginResponse.postValue(Resource.Error("response is null"))
            }
        }
    }

}