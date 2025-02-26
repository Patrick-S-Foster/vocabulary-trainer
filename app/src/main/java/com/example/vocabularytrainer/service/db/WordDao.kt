package com.example.vocabularytrainer.service.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * FROM Word ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomWords(count: Int = 1): Word

    @Insert
    suspend fun insertAll(vararg words: Word)
}