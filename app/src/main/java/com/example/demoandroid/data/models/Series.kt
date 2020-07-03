package com.example.demoandroid.data.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "series")
data class Series(
    val available: Int,
    val collectionURI: String,
    @SerializedName("items") val seriesItems: List<SeriesItem>,
    val returned: Int
)