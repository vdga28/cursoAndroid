package com.example.demoandroid.data.persistence.databases

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.demoandroid.data.models.CharacterResult
import com.example.demoandroid.data.persistence.daos.CharacterDao

class CharacterRepository(context: Context) {

    var characterDao: CharacterDao? = null
    var allCharacters: LiveData<List<CharacterResult>>? = null

    init {
        val characterDatabase = CharacterDatabase.getInstance(context)
        characterDao = characterDatabase.CharacterDao()
    }

    suspend fun getAll(): List<CharacterResult>? {
        return characterDao?.loadAllCharacters()
    }

    fun insert(character: CharacterResult) {
        characterDao?.insertCharacter(character)
    }

    fun update(character: CharacterResult) {
        characterDao?.updateCharacter(character)
    }

    suspend fun delete(character: CharacterResult) {
        characterDao?.deleteCharacter(character)
    }
}