package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.github.patricksfoster.vocabularytrainer.service.db.LanguageLevel
import org.junit.Rule
import org.junit.Test

class LanguageFloatingActionButtonTests {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allVisible() {
        val expanded = mutableStateOf(false)
        composeTestRule.setContent {
            LanguageFloatingActionButton(expanded) { }
        }

        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButtonOuterBox",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButtonInnerBox#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButtonText#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButtonInnerBox#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButtonText#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButtonInnerBox#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButtonText#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButtonInnerBox#3",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#3",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButtonText#3",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButtonInnerBox#4",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#4",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButtonText#4",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButtonIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun floatingActionButtonClicked_ExpandedIsTrue() {
        val expanded = mutableStateOf(false)
        var languageLevelClicked: LanguageLevel? = null
        composeTestRule.setContent {
            LanguageFloatingActionButton(expanded) { languageLevelClicked = it }
        }

        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(expanded.value)
        assert(languageLevelClicked == null)
    }

    @Test
    fun floatingActionButtonClickedTwice_ExpandedIsFalse() {
        val expanded = mutableStateOf(false)
        var languageLevelClicked: LanguageLevel? = null
        composeTestRule.setContent {
            LanguageFloatingActionButton(expanded) { languageLevelClicked = it }
        }

        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick().performClick()

        assert(!expanded.value)
        assert(languageLevelClicked == null)
    }

    @Test
    fun languageLevelA1ButtonClicked_A1LanguageLevelInCallback() {
        val expanded = mutableStateOf(false)
        var languageLevelClicked: LanguageLevel? = null
        composeTestRule.setContent {
            LanguageFloatingActionButton(expanded) { languageLevelClicked = it }
        }

        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#0",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(expanded.value)
        assert(languageLevelClicked == LanguageLevel.A1)
    }

    @Test
    fun languageLevelA1ButtonClicked_A2LanguageLevelInCallback() {
        val expanded = mutableStateOf(false)
        var languageLevelClicked: LanguageLevel? = null
        composeTestRule.setContent {
            LanguageFloatingActionButton(expanded) { languageLevelClicked = it }
        }

        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#1",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(expanded.value)
        assert(languageLevelClicked == LanguageLevel.A2)
    }

    @Test
    fun languageLevelA1ButtonClicked_B1LanguageLevelInCallback() {
        val expanded = mutableStateOf(false)
        var languageLevelClicked: LanguageLevel? = null
        composeTestRule.setContent {
            LanguageFloatingActionButton(expanded) { languageLevelClicked = it }
        }

        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#2",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(expanded.value)
        assert(languageLevelClicked == LanguageLevel.B1)
    }

    @Test
    fun languageLevelA1ButtonClicked_B2LanguageLevelInCallback() {
        val expanded = mutableStateOf(false)
        var languageLevelClicked: LanguageLevel? = null
        composeTestRule.setContent {
            LanguageFloatingActionButton(expanded) { languageLevelClicked = it }
        }

        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#3",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(expanded.value)
        assert(languageLevelClicked == LanguageLevel.B2)
    }

    @Test
    fun languageLevelA1ButtonClicked_C1LanguageLevelInCallback() {
        val expanded = mutableStateOf(false)
        var languageLevelClicked: LanguageLevel? = null
        composeTestRule.setContent {
            LanguageFloatingActionButton(expanded) { languageLevelClicked = it }
        }

        composeTestRule.onNodeWithTag(
            testTag = "FloatingActionButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "FilledActionButton#4",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(expanded.value)
        assert(languageLevelClicked == LanguageLevel.C1)
    }
}