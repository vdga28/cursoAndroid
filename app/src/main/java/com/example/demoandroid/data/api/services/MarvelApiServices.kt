package com.example.demoandroid.data.api.services

import com.example.demoandroid.data.models.CharactersResponse
import com.example.demoandroid.data.models.ComicsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MarvelApiServices {
    @GET("characters")
    fun getCharacters(
        @Query("apikey") apiKey: String?,
        @Query("hash") hash: String?,
        @Query("ts") timestamp: String?
    ): Call<CharactersResponse?>

    @GET("comics")
    suspend fun getComics(
        @Query("apikey") apiKey: String?,
        @Query("hash") hash: String?,
        @Query("ts") timestamp: String?
    ): ComicsResponse?
}