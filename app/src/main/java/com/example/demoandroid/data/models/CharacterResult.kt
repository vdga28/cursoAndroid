package com.example.demoandroid.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.demoandroid.data.persistence.Converters

@Entity(tableName = "character")
data class CharacterResult(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    //val comics: Comics,
    val description: String,
    //val events: Events,
    val modified: String,
    val name: String,
    val resourceURI: String,
    //val series: Series,
    //val stories: Stories,
    @TypeConverters(Converters::class)
    @ColumnInfo(name = "thumbnail") val thumbnail: Thumbnail
    //val urls: List<Url>
)

