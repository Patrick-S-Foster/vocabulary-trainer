package io.github.patricksfoster.vocabularytrainer.service.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Query("SELECT * FROM Word ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWord(): Word

    @Query("SELECT * FROM Word WHERE category=:languageLevelTitle AND study_state=:studyState ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWord(languageLevelTitle: String, studyState: Int): Word?

    @Query("SELECT COUNT(*) FROM Word WHERE category=:languageLevelTitle")
    suspend fun getTotalCount(languageLevelTitle: String): Int

    @Query("SELECT COUNT(*) FROM Word WHERE category=:languageLevelTitle AND study_state=:studyState")
    suspend fun getLearnedCount(
        languageLevelTitle: String,
        studyState: Int = StudyState.LEARNED
    ): Int

    @Query("SELECT * FROM Word WHERE word_of_the_day_date=:date LIMIT 1")
    suspend fun getWordOfTheDay(date: String): Word?

    @Query("UPDATE Word Set study_state=0")
    suspend fun resetProgress()

    @Query("SELECT * FROM Word WHERE word LIKE :searchString || '%' ORDER BY word")
    suspend fun searchWords(searchString: String): List<Word>

    @Query("SELECT * FROM Word WHERE id=:id")
    suspend fun getWord(id: Int): Word

    @Update
    suspend fun updateAll(vararg words: Word)
}