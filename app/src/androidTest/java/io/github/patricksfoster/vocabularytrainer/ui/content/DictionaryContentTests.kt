package io.github.patricksfoster.vocabularytrainer.ui.content

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.github.patricksfoster.vocabularytrainer.service.db.WordDao
import io.github.patricksfoster.vocabularytrainer.service.db.WordDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

private class LifecycleOwnerRule : TestWatcher() {

    val lifecycleOwner: LifecycleOwner = object : LifecycleOwner {
        override val lifecycle: Lifecycle get() = lifecycleRegistry
    }

    val lifecycleRegistry by lazy { LifecycleRegistry(lifecycleOwner) }

    override fun starting(description: Description?) {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    override fun finished(description: Description?) {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }
}

class DictionaryContentTests {
    private lateinit var wordDao: WordDao
    private lateinit var db: WordDatabase

    @get: Rule
    val composeTestRule = createComposeRule()

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
    fun allVisible_ExceptTrailingIconAndExpandedContent() = runTest {
        composeTestRule.setContent {
            DictionaryContent(
                wordDao = wordDao,
                lifecycleScope = LifecycleOwnerRule().lifecycleRegistry.coroutineScope,
                playAudio = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentSearchBar",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentSearchBarInputField",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentSearchBarTrailingIcon",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentExpandedContent",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun textInput_TrailingIconAndExpandedContentExist() = runTest {
        composeTestRule.setContent {
            DictionaryContent(
                wordDao = wordDao,
                lifecycleScope = LifecycleOwnerRule().lifecycleRegistry.coroutineScope,
                playAudio = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentSearchBarInputField",
            useUnmergedTree = true
        ).performTextInput("SEARCH_TEXT")
        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentSearchBarTrailingIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentExpandedContent",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun textInput_ClickTrailingIcon_RemainsExpanded() = runTest {
        composeTestRule.setContent {
            DictionaryContent(
                wordDao = wordDao,
                lifecycleScope = LifecycleOwnerRule().lifecycleRegistry.coroutineScope,
                playAudio = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentSearchBarInputField",
            useUnmergedTree = true
        ).performTextInput("SEARCH_TEXT")
        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentSearchBarTrailingIcon",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentSearchBarTrailingIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DictionaryContentExpandedContent",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }
}