package com.example.demoandroid.data.models

import androidx.room.Entity

@Entity
data class Thumbnail(
    val extension: String,
    val path: String
)