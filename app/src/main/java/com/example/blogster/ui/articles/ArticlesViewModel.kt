package com.example.blogster.ui.articles

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogster.data.remote.Resource
import com.example.blogster.data.remote.responses.Article
import com.example.blogster.data.remote.responses.ArticleCreateRequest
import com.example.blogster.data.remote.responses.Comment
import com.example.blogster.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _articlesResponse = MutableLiveData<Resource<List<Article>>>()
    val articlesResponse: MutableLiveData<Resource<List<Article>>> = _articlesResponse

    private val _myArticlesResponse = MutableLiveData<Resource<List<Article>>>()
    val myArticlesResponse: MutableLiveData<Resource<List<Article>>> = _myArticlesResponse

    private val _createArticleResponse = MutableLiveData<Resource<Article>>()
    val createArticleResponse: MutableLiveData<Resource<Article>> = _createArticleResponse

    private val _articleCommentsResponse = MutableLiveData<Resource<List<Comment>>>()
    val articleCommentsResponse: MutableLiveData<Resource<List<Comment>>> = _articleCommentsResponse


    fun getFavoriteArticles(token: String, username: String) = viewModelScope.launch {
        val response = mainRepository.getFavoriteArticles(token, username)

        if (response.isSuccessful) {
            _articlesResponse.postValue(Resource.Success(response.body()?.articles!!))
        } else {
            _articlesResponse.postValue(Resource.Error(response.message()))
        }
    }

    fun getMyArticles(token: String, username: String) = viewModelScope.launch {
        val response = mainRepository.getMyArticles(token, username)

        if (response.isSuccessful) {
            _myArticlesResponse.postValue(Resource.Success(response.body()?.articles!!))
        } else {
            _myArticlesResponse.postValue(Resource.Error(response.message()))
        }
    }


    fun createArticle(articleCreateRequest: ArticleCreateRequest, token: String) =
        viewModelScope.launch {
            val response = mainRepository.createArticle(articleCreateRequest, token)

            if (response.isSuccessful) {
                _createArticleResponse.postValue(Resource.Success(response.body()?.article!!))
            } else {
                _myArticlesResponse.postValue(Resource.Error(response.message()))
            }
        }

    fun getArticleComments(slug: String, token: String) = viewModelScope.launch {
        val response = mainRepository.getArticleComments(slug, token)

        if (response.isSuccessful) {
            _articleCommentsResponse.postValue(Resource.Success(response.body()?.comments!!))
        } else {
            _articleCommentsResponse.postValue(Resource.Error(response.message()))
        }
    }
}