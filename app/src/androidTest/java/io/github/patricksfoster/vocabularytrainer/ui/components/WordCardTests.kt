package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.doubleClick
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import io.github.patricksfoster.vocabularytrainer.TestUtil
import org.junit.Rule
import org.junit.Test

class WordCardTests {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displayLanguageLevel_DisplayAudio_EverythingVisibleExceptDialog() {
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(phoneticAudioUrl = "URL"),
                displayLanguageLevel = true,
                displayAudio = true,
                displayDefinitions = true,
                playAudio = {},
                selectable = true
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardCard",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardLanguageLevel",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardWord",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardPhonetic",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardAudioButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardAudioIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardDialog",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun displayLanguageLevel_DoNotDisplayAudio_EverythingVisibleExceptDialogAndAudioButton() {
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(phoneticAudioUrl = "URL"),
                displayLanguageLevel = true,
                displayAudio = false,
                displayDefinitions = true,
                playAudio = {},
                selectable = true
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardCard",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardLanguageLevel",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardWord",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardPhonetic",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardAudioButton",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardAudioIcon",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardDialog",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun doNotDisplayLanguageLevel_displayAudio_EverythingVisibleExceptDialogAndLanguageLevel() {
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(phoneticAudioUrl = "URL"),
                displayLanguageLevel = false,
                displayAudio = true,
                displayDefinitions = true,
                playAudio = {},
                selectable = true
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardCard",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardLanguageLevel",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardWord",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardPhonetic",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardAudioButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardAudioIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordCardDialog",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun clickAudioButton_CallsPlayAudio() {
        var playAudioValue: String? = null
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(phoneticAudioUrl = "URL"),
                displayLanguageLevel = true,
                displayAudio = true,
                displayDefinitions = true,
                playAudio = { playAudioValue = it },
                selectable = true
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardAudioButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(playAudioValue != null && playAudioValue == "URL")
    }

    @Test
    fun clickWordCardWhileSelectable_CallsOnSelected() {
        var onSelectedCalled = false
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(phoneticAudioUrl = "URL"),
                displayLanguageLevel = true,
                displayAudio = true,
                displayDefinitions = true,
                playAudio = { },
                selectable = true
            ) { onSelectedCalled = true }
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardCard",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        assert(onSelectedCalled)
    }

    @Test
    fun clickWordCardWhileNotSelectable_DoesNotCallsOnSelected() {
        var onSelectedCalled = false
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(phoneticAudioUrl = "URL"),
                displayLanguageLevel = true,
                displayAudio = true,
                displayDefinitions = true,
                playAudio = { },
                selectable = false
            ) { onSelectedCalled = true }
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardCard",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        assert(!onSelectedCalled)
    }

    @Test
    fun longClickWordCardWhileSelectable_DialogDisplayed() {
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(phoneticAudioUrl = "URL"),
                displayLanguageLevel = true,
                displayAudio = true,
                displayDefinitions = true,
                playAudio = { },
                selectable = true
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardCard",
            useUnmergedTree = true
        ).assertHasClickAction().performTouchInput {
            longClick()
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardDialog",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun doubleClickWordCardWhileSelectable_DialogDisplayed() {
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(phoneticAudioUrl = "URL"),
                displayLanguageLevel = true,
                displayAudio = true,
                displayDefinitions = true,
                playAudio = { },
                selectable = true
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardCard",
            useUnmergedTree = true
        ).assertHasClickAction().performTouchInput {
            doubleClick()
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardDialog",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun doubleClickWordCardWhileSelectable_ButNothingToShow_DialogNotDisplayed() {
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(),
                displayLanguageLevel = true,
                displayAudio = true,
                displayDefinitions = false,
                playAudio = { },
                selectable = true
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardCard",
            useUnmergedTree = true
        ).assertHasClickAction().performTouchInput {
            doubleClick()
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardDialog",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun longClickWordCardWhileSelectable_ButNothingToShow_DialogNotDisplayed() {
        composeTestRule.setContent {
            WordCard(
                modifier = Modifier,
                word = TestUtil.createWord(),
                displayLanguageLevel = true,
                displayAudio = true,
                displayDefinitions = false,
                playAudio = { },
                selectable = true
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardCard",
            useUnmergedTree = true
        ).assertHasClickAction().performTouchInput {
            longClick()
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordCardDialog",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }
}