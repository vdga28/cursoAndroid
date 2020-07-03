package com.example.demoandroid.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demoandroid.data.models.CharacterResult
import com.example.demoandroid.data.persistence.databases.CharacterRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteCharactersViewModel(
    val characterRepository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableLiveData<List<CharacterResult>>()
    var characters: LiveData<List<CharacterResult>>?
        get() = _characters

    init {
        characters = characterRepository.allCharacters
    }

    fun getAll() = viewModelScope.launch {
        _characters.value = characterRepository.getAll()
    }

    fun delete(characterResult: CharacterResult) = viewModelScope.launch {
        characterRepository.delete(characterResult)
        _characters.value = characterRepository.getAll()
    }

}