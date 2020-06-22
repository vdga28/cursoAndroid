package com.example.demoandroid.data.models

import com.google.gson.annotations.SerializedName

data class Creators(
    val available: Int,
    val collectionURI: String,
    @SerializedName("items") val creatorItems: List<CreatorItem>,
    val returned: Int
)