package com.example.blogster.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogster.data.remote.Resource
import com.example.blogster.data.remote.responses.Article
import com.example.blogster.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val mainRepository: MainRepository
)  : ViewModel() {

    private val _onFeedResponse = MutableLiveData<Resource<List<Article>>>()
    val feed: LiveData<Resource<List<Article>>> = _onFeedResponse

    private val _onMyFeedResponse = MutableLiveData<Resource<List<Article>>>()
    val myFeed: LiveData<Resource<List<Article>>> = _onMyFeedResponse

    private val _articleDetailResponse = MutableLiveData<Resource<Article>>()
    val articleDetailResponse: LiveData<Resource<Article>> = _articleDetailResponse

    fun fetchGlobalFeed() = viewModelScope.launch {
        _onFeedResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = mainRepository.getGlobalFeed()
            if (response.isSuccessful){
                _onFeedResponse.postValue(response.body()?.let { Resource.Success(it.articles) })
            }else{
                _onFeedResponse.postValue(Resource.Error(response.message()))
            }

        }
    }

    fun fetchMyFeed(token :String) = viewModelScope.launch {
        _onFeedResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = mainRepository.getMyFeed(token)
            if (response.isSuccessful){
                _onMyFeedResponse.postValue(response.body()?.let { Resource.Success(it.articles) })
            }else{
                _onMyFeedResponse.postValue(Resource.Error(response.message()))
            }

        }
    }

    fun getArticle(articleId :String) = viewModelScope.launch {
        _onFeedResponse.postValue(Resource.Loading())
        viewModelScope.launch {
            val response = mainRepository.getArticle(articleId)
            if (response.isSuccessful){
                _articleDetailResponse.postValue(response.body()?.let { Resource.Success(it.article) })
            }else{
                _articleDetailResponse.postValue(Resource.Error(response.message()))
            }

        }
    }



}