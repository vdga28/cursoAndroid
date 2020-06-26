package com.example.demoandroid.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoandroid.data.api.ApiClient
import com.example.demoandroid.data.models.CharacterResult
import com.example.demoandroid.data.models.CharactersResponse
import com.example.demoandroid.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersViewModel(val apiClient: ApiClient) : ViewModel() {

    private val _characters = MutableLiveData<List<CharacterResult>>()
    val characters: LiveData<List<CharacterResult>>
        get() = _characters

    fun getComicList() {
        viewModelScope.launch {
            apiClient.getService()
                .getCharacters(
                    Constants.apikey,
                    Constants.hash,
                    Constants.ts
                ).enqueue(object : Callback<CharactersResponse?> {
                    override fun onFailure(call: Call<CharactersResponse?>, t: Throwable) {
                        //Mostrar Dialogo de error
                    }

                    override fun onResponse(
                        call: Call<CharactersResponse?>,
                        response: Response<CharactersResponse?>
                    ) {
                        _characters.value = response.body()?.charactersData?.characterResults
                    }

                })
        }

    }
}