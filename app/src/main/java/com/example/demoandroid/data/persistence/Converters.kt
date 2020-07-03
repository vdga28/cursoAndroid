package com.example.demoandroid.data.persistence

import androidx.room.TypeConverter
import com.example.demoandroid.data.models.Thumbnail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromThumbnail(value: Thumbnail): String {
        val gson = Gson()
        val type = object : TypeToken<Thumbnail>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toThumbnail(value: String): Thumbnail {
        val gson = Gson()
        val type = object : TypeToken<Thumbnail>() {}.type
        return gson.fromJson(value, type)
    }
}