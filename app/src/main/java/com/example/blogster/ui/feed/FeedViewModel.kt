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



}