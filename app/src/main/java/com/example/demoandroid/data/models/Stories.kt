package com.example.demoandroid.data.models

import com.google.gson.annotations.SerializedName

data class Stories(
    val available: Int,
    val collectionURI: String,
    @SerializedName("items") val storiesItems: List<StoriesItem>,
    val returned: Int
)