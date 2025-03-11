package io.github.patricksfoster.vocabularytrainer.ui.content

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.github.patricksfoster.vocabularytrainer.service.db.WordDao
import io.github.patricksfoster.vocabularytrainer.service.db.WordDatabase
import io.github.patricksfoster.vocabularytrainer.service.settings.Settings
import io.github.patricksfoster.vocabularytrainer.service.settings.ThemeState
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsContentTests {
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

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allDisplayedExceptDialog() {
        composeTestRule.setContent {
            SettingsContent(
                contentPadding = PaddingValues(),
                settings = Settings(
                    dataStore = PreferenceDataStoreFactory.create(
                        produceFile = {
                            ApplicationProvider.getApplicationContext<Context>()
                                .preferencesDataStoreFile("")
                        }
                    ),
                    soundEffectsEnabled = true,
                    themeState = ThemeState.AUTO
                ),
                wordDao = wordDao,
                lifecycleScope = LifecycleOwnerRule().lifecycleRegistry.coroutineScope,
                progressReset = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentSoundEffectsTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentSoundEffectsSwitch",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentThemeTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentThemeText#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentThemeText#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentThemeText#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentThemeButton#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentThemeButton#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentThemeButton#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentResetButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentConfirmDialog",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun resetButtonClicked_DialogIsDisplayed() {
        composeTestRule.setContent {
            SettingsContent(
                contentPadding = PaddingValues(),
                settings = Settings(
                    dataStore = PreferenceDataStoreFactory.create(
                        produceFile = {
                            ApplicationProvider.getApplicationContext<Context>()
                                .preferencesDataStoreFile("")
                        }
                    ),
                    soundEffectsEnabled = true,
                    themeState = ThemeState.AUTO
                ),
                wordDao = wordDao,
                lifecycleScope = LifecycleOwnerRule().lifecycleRegistry.coroutineScope,
                progressReset = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentResetButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "SettingsContentConfirmDialog",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }
}