package com.example.demoandroid

import com.example.demoandroid.data.models.CharacterResult
import com.example.demoandroid.data.models.ComicResult

interface MarvelRepository {
    fun getComics():List<ComicResult>
    fun getCharacters(): List<CharacterResult>
}