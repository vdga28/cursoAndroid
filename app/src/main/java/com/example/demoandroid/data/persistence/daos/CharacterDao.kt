package com.example.demoandroid.data.persistence.daos

import androidx.room.*
import com.example.demoandroid.data.models.CharacterResult

@Dao
interface CharacterDao {

    @Query("SELECT * FROM CHARACTER ORDER BY ID")
    suspend fun loadAllCharacters(): List<CharacterResult>

    @Insert
    fun insertCharacter(character: CharacterResult)

    @Update
    fun updateCharacter(character: CharacterResult)

    @Delete
    suspend fun deleteCharacter(character: CharacterResult)
}