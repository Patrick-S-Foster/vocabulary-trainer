package com.example.vocabularytrainer.service.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Query("SELECT * FROM Word ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomWords(count: Int = 1): List<Word>

    @Query("SELECT COUNT(*) FROM Word WHERE category=:languageLevelTitle")
    suspend fun getTotalCount(languageLevelTitle: String): Int

    @Query("SELECT COUNT(*) FROM Word WHERE category=:languageLevelTitle AND study_state=:studyState")
    suspend fun getLearnedCount(
        languageLevelTitle: String,
        studyState: Int = StudyState.LEARNED
    ): Int

    @Query("SELECT * FROM Word WHERE word_of_the_day_date=:date LIMIT 1")
    suspend fun getWordOfTheDay(date: String): Word?

    @Query("UPDATE Word Set study_state = 0")
    suspend fun resetProgress(): Unit

    @Update
    suspend fun updateAll(vararg words: Word)
}