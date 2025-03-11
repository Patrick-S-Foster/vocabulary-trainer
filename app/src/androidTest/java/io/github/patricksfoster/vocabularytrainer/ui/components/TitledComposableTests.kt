package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class TitledComposableTests {
    private val text = "TEXT"
    private val contentText = "CONTENT_TEXT"

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allVisible() {
        composeTestRule.setContent {
            TitledComposable(text) {
                Text(
                    text = contentText,
                    modifier = Modifier.testTag("CONTENT")
                )
            }
        }

        composeTestRule.onNodeWithTag(
            testTag = "TitledComposableDivider",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "TitleComposableText",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "CONTENT",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun textIsDisplayed() {
        composeTestRule.setContent {
            TitledComposable(text) {
                Text(
                    text = contentText,
                    modifier = Modifier.testTag("CONTENT")
                )
            }
        }

        composeTestRule.onNodeWithTag(
            testTag = "TitleComposableText",
            useUnmergedTree = true
        ).assertTextEquals(text)
    }
}