package com.example.demoandroid.data.models

import com.google.gson.annotations.SerializedName

data class ComicsResponse(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    @SerializedName("data") val comicsData: ComicsData,
    val etag: String,
    val status: String
)