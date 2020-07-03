package com.example.demoandroid.data.persistence.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.demoandroid.data.models.CharacterResult
import com.example.demoandroid.data.models.Thumbnail
import com.example.demoandroid.data.persistence.Converters
import com.example.demoandroid.data.persistence.daos.CharacterDao

@TypeConverters(Converters::class)
@Database(entities = [CharacterResult::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun CharacterDao(): CharacterDao

    companion object {
        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getInstance(context: Context): CharacterDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CharacterDatabase::class.java,
                    "character_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}