package com.example.demoandroid.data.models

import com.google.gson.annotations.SerializedName

data class Series(
    val available: Int,
    val collectionURI: String,
    @SerializedName("items") val seriesItems: List<SeriesItem>,
    val returned: Int
)