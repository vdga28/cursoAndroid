package com.example.demoandroid.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoandroid.data.api.ApiClient
import com.example.demoandroid.data.models.CharacterResult
import com.example.demoandroid.data.models.CharactersResponse
import com.example.demoandroid.data.persistence.databases.CharacterRepository
import com.example.demoandroid.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class CharactersViewModel(
    private val apiClient: ApiClient,
    private val characterRepository: CharacterRepository
) :
    ViewModel() {

    private val _characters = MutableLiveData<List<CharacterResult>>()
    val characters: LiveData<List<CharacterResult>>
        get() = _characters

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

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

    fun insert(characterResult: CharacterResult) = scope.launch(Dispatchers.IO) {
        characterRepository.insert(characterResult)
    }


}