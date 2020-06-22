package com.example.demoandroid.data.models

import com.google.gson.annotations.SerializedName

data class CharactersData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    @SerializedName("results") val characterResults: List<CharacterResult>,
    val total: Int
)