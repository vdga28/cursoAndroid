package com.example.demoandroid.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoandroid.data.api.ApiClient
import com.example.demoandroid.data.models.ComicResult
import com.example.demoandroid.utils.Constants
import kotlinx.coroutines.launch

class ComicsViewModel(private val apiClient: ApiClient) : ViewModel() {

    private val _comicsList = MutableLiveData<List<ComicResult>?>()
    val comicsList: LiveData<List<ComicResult>?>
        get() = _comicsList


    fun getComics() {
        viewModelScope.launch {
            val comics = apiClient.getService().getComics(
                Constants.apikey,
                Constants.hash,
                Constants.ts
            )

            _comicsList.value = comics?.comicsData?.comicResults
        }
    }

}