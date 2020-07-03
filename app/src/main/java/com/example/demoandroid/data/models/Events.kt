package com.example.demoandroid.data.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "events")
data class Events(
    val available: Int,
    val collectionURI: String,
    @SerializedName("events") val eventItems: List<EventItem>,
    val returned: Int
)