package com.example.demoandroid.data.models

import androidx.room.Entity

@Entity(tableName = "url")
data class Url(
    val type: String,
    val url: String
)