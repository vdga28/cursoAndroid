package com.example.demoandroid.data.models

import androidx.room.Entity

@Entity(tableName = "comics")
data class Comics(
    val available: Int,
    val collectionURI: String,
    val comicsItems: List<ComicsItem>,
    val returned: Int
)