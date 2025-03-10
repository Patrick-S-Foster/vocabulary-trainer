package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import io.github.patricksfoster.vocabularytrainer.service.db.LanguageLevel
import org.junit.Rule
import org.junit.Test

class LanguageProgressIndicatorTests {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allVisible() {
        composeTestRule.setContent {
            LanguageProgressIndicator(Modifier, LanguageLevel.A1, 0, 100)
        }

        composeTestRule.onNodeWithTag(
            testTag = "LanguageProgressIndicatorBox",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "LanguageProgressIndicatorCircularIndicator",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "LanguageProgressIndicatorTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "LanguageProgressIndicatorBody",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun titleCorrespondsToLanguageLevel() {
        composeTestRule.setContent {
            LanguageProgressIndicator(Modifier, LanguageLevel.A1, 0, 100)
        }

        composeTestRule.onNodeWithTag(
            testTag = "LanguageProgressIndicatorTitle",
            useUnmergedTree = true
        ).assertTextEquals(LanguageLevel.A1.title)
    }

    @Test
    fun bodyCorrespondsToCounts() {
        composeTestRule.setContent {
            LanguageProgressIndicator(Modifier, LanguageLevel.A1, 123, 456)
        }

        composeTestRule.onNodeWithTag(
            testTag = "LanguageProgressIndicatorBody",
            useUnmergedTree = true
        ).assertTextEquals("123/456")
    }

    @Test
    fun zeroTotalCount_NoException() {
        composeTestRule.setContent {
            LanguageProgressIndicator(Modifier, LanguageLevel.A1, 0, 0)
        }
    }
}