package com.example.demoandroid.data.models

import com.google.gson.annotations.SerializedName

data class Events(
    val available: Int,
    val collectionURI: String,
    @SerializedName("events") val eventItems: List<EventItem>,
    val returned: Int
)