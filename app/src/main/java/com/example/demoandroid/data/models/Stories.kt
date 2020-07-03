package com.example.demoandroid.data.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "stories")
data class Stories(
    val available: Int,
    val collectionURI: String,
    @SerializedName("items") val storiesItems: List<StoriesItem>,
    val returned: Int
)