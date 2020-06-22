package com.example.demoandroid.data.models

data class Comics(
    val available: Int,
    val collectionURI: String,
    val comicsItems: List<ComicsItem>,
    val returned: Int
)