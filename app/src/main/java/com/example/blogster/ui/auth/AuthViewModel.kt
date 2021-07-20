package com.example.blogster.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogster.data.remote.Resource
import com.example.blogster.data.remote.requests.LoginRequest
import com.example.blogster.data.remote.requests.SignUpRequest
import com.example.blogster.data.remote.requests.UserLogInCredentials
import com.example.blogster.data.remote.requests.UserSignUpCredentials
import com.example.blogster.data.remote.responses.AuthResponse
import com.example.blogster.data.remote.responses.User
import com.example.blogster.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel(){
    private val _onLoginResponse = MutableLiveData<Resource<User>>()
    val onLoginResponse: LiveData<Resource<User>> = _onLoginResponse

    private val _onSignUpResponse = MutableLiveData<Resource<User>>()
    val onSignUpResponse: LiveData<Resource<User>> = _onSignUpResponse


    fun loginUser(email: String,password: String) {
        _onLoginResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = mainRepository.loginUser(email,password)
            Log.d("viewmodel","${response.body()} and $email")
            if (response.isSuccessful){
                _onLoginResponse.postValue(Resource.Success(response.body()?.user!!))
            }else{
                _onLoginResponse.postValue(Resource.Error(response.message()))
            }

        }
    }

//    fun signInUser(userSingUpCreds: UserSignUpCredentials) {
//        _onSignUpResponse.postValue(Resource.Loading())
//        viewModelScope.launch {
//            val response = mainRepository.signUpUser(userSingUpCreds)
//            _onSignUpResponse.postValue(Resource.Success(response.user))
//        }
//    }

}