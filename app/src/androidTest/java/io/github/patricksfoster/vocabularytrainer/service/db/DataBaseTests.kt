package io.github.patricksfoster.vocabularytrainer.service.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.patricksfoster.vocabularytrainer.TestUtil
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DataBaseTests {
    private lateinit var wordDao: WordDao
    private lateinit var db: WordDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, WordDatabase::class.java).build()
        wordDao = db.wordDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getRandomWord_NoParameters_ReturnsWord() = runTest {
        val word = TestUtil.createWord(id = 1234)
        wordDao.insert(word)

        val result = wordDao.getRandomWord()

        assert(result.id == word.id)
    }

    @Test
    fun getRandomWord_CategoryExists_StudyStateExists_ReturnsWord() = runTest {
        val word = TestUtil.createWord(id = 1234, category = "category", studyState = 5678)
        wordDao.insert(word)

        val result = wordDao.getRandomWord(languageLevelTitle = "category", studyState = 5678)

        assert(result != null && result.id == word.id)
    }

    @Test
    fun getRandomWord_CategoryDoesNotExist_StudyStateExists_ReturnsNull() = runTest {
        val word = TestUtil.createWord(id = 1234, category = "category", studyState = 5678)
        wordDao.insert(word)

        val result = wordDao.getRandomWord(languageLevelTitle = "does_not_exist", studyState = 5678)

        assert(result == null)
    }

    @Test
    fun getRandomWord_CategoryExists_StudyStateDoesNotExist_ReturnsNull() = runTest {
        val word = TestUtil.createWord(id = 1234, category = "category", studyState = 5678)
        wordDao.insert(word)

        val result = wordDao.getRandomWord(languageLevelTitle = "category", studyState = 1234)

        assert(result == null)
    }

    @Test
    fun getRandomWord_CategoryDoesNotExist_StudyStateDoesNotExist_ReturnsNull() = runTest {
        val word = TestUtil.createWord(id = 1234, category = "category", studyState = 5678)
        wordDao.insert(word)

        val result = wordDao.getRandomWord(languageLevelTitle = "does_not_exist", studyState = 5678)

        assert(result == null)
    }

    @Test
    fun getTotalCount_NoneExist_Returns0() = runTest {
        val result = wordDao.getTotalCount("does_not_exist")

        assert(result == 0)
    }

    @Test
    fun getTotalCount_OtherCategoriesExist_Returns0() = runTest {
        for (i in 1..10) {
            val word = TestUtil.createWord(id = i, category = "category")
            wordDao.insert(word)
        }

        val result = wordDao.getTotalCount("does_not_exist")

        assert(result == 0)
    }

    @Test
    fun getTotalCount_CategoryExists_ReturnsExpected() = runTest {
        for (i in 1..10) {
            val word = TestUtil.createWord(id = i, category = "not_relevant_category")
            wordDao.insert(word)
        }

        for (i in 25..50) {
            val word = TestUtil.createWord(id = i, category = "relevant_category")
            wordDao.insert(word)
        }

        val result = wordDao.getTotalCount("relevant_category")

        assert(result == 26)
    }

    @Test
    fun getLearnedCount_NotLearned_Returns0() = runTest {
        for (languageLevel in listOf(
            LanguageLevel.A1,
            LanguageLevel.A2,
            LanguageLevel.A2,
            LanguageLevel.B1,
            LanguageLevel.B1,
            LanguageLevel.B1,
            LanguageLevel.B1,
            LanguageLevel.B2,
            LanguageLevel.B2,
            LanguageLevel.B2,
            LanguageLevel.C1,
            LanguageLevel.C1,
            LanguageLevel.C1,
            LanguageLevel.C1,
            LanguageLevel.C1
        )) {
            wordDao.insert(
                TestUtil.createWord(
                    category = languageLevel.title,
                    studyState = StudyState.NONE
                )
            )
        }

        val a1Result = wordDao.getLearnedCount(LanguageLevel.A1.title)
        val a2Result = wordDao.getLearnedCount(LanguageLevel.A2.title)
        val b1Result = wordDao.getLearnedCount(LanguageLevel.B1.title)
        val b2Result = wordDao.getLearnedCount(LanguageLevel.B2.title)
        val c1Result = wordDao.getLearnedCount(LanguageLevel.C1.title)

        assert(a1Result == 0)
        assert(a2Result == 0)
        assert(b1Result == 0)
        assert(b2Result == 0)
        assert(c1Result == 0)
    }

    @Test
    fun getLearnedCount_Learned_ReturnsExpectedCount() = runTest {
        for (languageLevel in listOf(
            LanguageLevel.A1,
            LanguageLevel.A2,
            LanguageLevel.A2,
            LanguageLevel.B1,
            LanguageLevel.B1,
            LanguageLevel.B1,
            LanguageLevel.B2,
            LanguageLevel.B2,
            LanguageLevel.B2,
            LanguageLevel.B2,
            LanguageLevel.C1,
            LanguageLevel.C1,
            LanguageLevel.C1,
            LanguageLevel.C1,
            LanguageLevel.C1
        )) {
            wordDao.insert(
                TestUtil.createWord(
                    category = languageLevel.title,
                    studyState = StudyState.LEARNED
                )
            )
        }

        val a1Result = wordDao.getLearnedCount(LanguageLevel.A1.title)
        val a2Result = wordDao.getLearnedCount(LanguageLevel.A2.title)
        val b1Result = wordDao.getLearnedCount(LanguageLevel.B1.title)
        val b2Result = wordDao.getLearnedCount(LanguageLevel.B2.title)
        val c1Result = wordDao.getLearnedCount(LanguageLevel.C1.title)

        assert(a1Result == 1)
        assert(a2Result == 2)
        assert(b1Result == 3)
        assert(b2Result == 4)
        assert(c1Result == 5)
    }

    @Test
    fun getWordOfTheDay_NoWord_ReturnsNull() = runTest {
        val result = wordDao.getWordOfTheDay("date_string")

        assert(result == null)
    }

    @Test
    fun getWordOfTheDay_WordExists_ReturnsWord() = runTest {
        val word = TestUtil.createWord(wordOfTheDayDate = "date_string")
        wordDao.insert(word)

        val result = wordDao.getWordOfTheDay("date_string")

        assert(result != null && result.wordOfTheDayDate == "date_string")
    }

    @Test
    fun resetProgress_ResetsProgressTo0() = runTest {
        val word = TestUtil.createWord(id = 1234, studyState = 5678)
        wordDao.insert(word)
        wordDao.resetProgress()

        val result = wordDao.getRandomWord()

        assert(result.studyState == 0)
    }

    @Test
    fun searchWords_EmptySearchString_ReturnsAllAlphabetically() = runTest {
        for (c in ('a'..'z').reversed()) {
            wordDao.insert(TestUtil.createWord(word = c.toString()))
        }

        val result = wordDao.searchWords("")

        assert(result.size == 26)
        assert(result.windowed(2).all { (first, second) -> first.word < second.word })
    }

    @Test
    fun searchWords_SingleCharacter_ReturnsAllMatchingWordsAlphabetically() = runTest {
        for (c1 in ('a'..'z').reversed()) {
            for (c2 in ('a'..'z').reversed()) {
                wordDao.insert(TestUtil.createWord(word = c1.toString() + c2.toString()))
            }
        }

        for (c in 'a'..'z') {
            val result = wordDao.searchWords(c.toString())

            assert(result.size == 26)
            assert(result.windowed(2).all { (first, second) -> first.word < second.word })
        }
    }

    @Test
    fun searchWords_DoesNotExist_ReturnsEmpty() = runTest {
        for (c in ('a'..'z').reversed()) {
            wordDao.insert(TestUtil.createWord(word = c.toString()))
        }

        val result = wordDao.searchWords("#")

        assert(result.isEmpty())
    }
}