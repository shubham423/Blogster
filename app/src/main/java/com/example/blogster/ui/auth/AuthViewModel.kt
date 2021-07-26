package com.example.blogster.ui.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogster.data.remote.Resource
import com.example.blogster.data.remote.responses.Article
import com.example.blogster.data.remote.responses.ArticleResponse
import com.example.blogster.data.remote.responses.User
import com.example.blogster.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _userResponse = MutableLiveData<Resource<User>?>()
    val userResponse: MutableLiveData<Resource<User>?> = _userResponse

    private val _articlesResponse = MutableLiveData<Resource<List<Article>>>()
    val articlesResponse: MutableLiveData<Resource<List<Article>>> = _articlesResponse

    private val _myArticlesResponse = MutableLiveData<Resource<List<Article>>>()
    val myArticlesResponse: MutableLiveData<Resource<List<Article>>> = _myArticlesResponse


    fun loginUser(email: String, password: String) {
        _userResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = mainRepository.loginUser(email, password)
            Log.d("viewmodel", "${response} and $email")
            if (response.isSuccessful) {
                _userResponse.postValue(Resource.Success(response.body()?.user!!))
            } else {
                _userResponse.postValue(Resource.Error(response.message()))
            }

        }
    }

    fun signInUser(email: String, password: String, username: String) {
        _userResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = mainRepository.signUpUser(email, password, username)
            if (response.isSuccessful) {
                _userResponse.postValue(Resource.Success(response.body()?.user!!))
            } else {
                _userResponse.postValue(Resource.Error(response.message()))
            }

        }
    }

    fun logout() {
        _userResponse.postValue(null)
    }

    fun update(
        bio: String?,
        username: String?,
        image: String?,
        email: String?,
        password: String?
    ) = viewModelScope.launch {
        val response = mainRepository.updateUser(bio, username, image, email, password)

        if (response.isSuccessful) {
            _userResponse.postValue(Resource.Success(response.body()?.user!!))
        } else {
            _userResponse.postValue(Resource.Error(response.message()))
        }
    }

    fun getCurrentUser(token: String) = viewModelScope.launch {
        val response = mainRepository.getCurrentUser(token)
        if (response.isSuccessful) {
            _userResponse.postValue(Resource.Success(response.body()?.user!!))
        } else {
            _userResponse.postValue(Resource.Error(response.message()))
        }
    }

    fun getFavoriteArticles(token: String, username: String) = viewModelScope.launch {
        val response = mainRepository.getFavoriteArticles(token,username)

        if (response.isSuccessful) {
            _articlesResponse.postValue(Resource.Success(response.body()?.articles!!))
        } else {
            _userResponse.postValue(Resource.Error(response.message()))
        }
    }

    fun getMyArticles(token: String, username: String) = viewModelScope.launch {
        val response = mainRepository.getMyArticles(token,username)

        if (response.isSuccessful) {
            _myArticlesResponse.postValue(Resource.Success(response.body()?.articles!!))
        } else {
            _myArticlesResponse.postValue(Resource.Error(response.message()))
        }
    }

}