package com.example.demoandroid.data.models

import com.google.gson.annotations.SerializedName

data class ComicsData(
    val count: Int,
    val limit: Int,
    val offset: Int,
    @SerializedName("results") val comicResults: List<ComicResult>,
    val total: Int
)