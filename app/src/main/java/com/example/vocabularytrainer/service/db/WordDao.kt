package com.example.vocabularytrainer.service.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * FROM Word ORDER BY RANDOM() LIMIT :count")
    suspend fun getRandomWords(count: Int = 1): Word

    @Query("SELECT COUNT(*) FROM Word WHERE category=:languageLevelTitle")
    suspend fun getTotalCount(languageLevelTitle: String): Int

    @Query("SELECT COUNT(*) FROM Word WHERE category=:languageLevelTitle AND study_state=:studyState")
    suspend fun getLearnedCount(
        languageLevelTitle: LanguageLevel,
        studyState: Int = StudyState.LEARNED
    ): Int

    @Insert
    suspend fun insertAll(vararg words: Word)
}