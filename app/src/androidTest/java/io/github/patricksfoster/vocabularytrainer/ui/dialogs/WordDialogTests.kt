package io.github.patricksfoster.vocabularytrainer.ui.dialogs

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.github.patricksfoster.vocabularytrainer.TestUtil
import org.junit.Rule
import org.junit.Test

class WordDialogTests {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displayDefinitions_DisplayAudio_ContainsDefinitions_ContainsDefinitionsSource_ContainsAudioSource_AllVisible() {
        composeTestRule.setContent {
            WordDialog(
                modifier = Modifier,
                word = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree",
                    definitionWordSourceUrl = "definitionWordSourceUrl",
                    definitionWordLicenseUrl = "definitionWordLicenseUrl",
                    definitionWordLicenseName = "definitionWordLicenseName",
                    phoneticAudioSourceUrl = "phoneticAudioSourceUrl",
                    phoneticAudioLicenseUrl = "phoneticAudioLicenseUrl",
                    phoneticAudioLicenseName = "phoneticAudioLicenseName"
                ),
                displayDefinitions = true,
                displayAudio = true,
                onDismissRequest = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordDialogAlertDialog",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogLexicalCategory",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogCloseButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinitionCredit",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogAudioCredit",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun doNotDisplayDefinitions_DisplayAudio_ContainsDefinitions_ContainsDefinitionsSource_ContainsAudioSource_SomeVisible() {
        composeTestRule.setContent {
            WordDialog(
                modifier = Modifier,
                word = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree",
                    definitionWordSourceUrl = "definitionWordSourceUrl",
                    definitionWordLicenseUrl = "definitionWordLicenseUrl",
                    definitionWordLicenseName = "definitionWordLicenseName",
                    phoneticAudioSourceUrl = "phoneticAudioSourceUrl",
                    phoneticAudioLicenseUrl = "phoneticAudioLicenseUrl",
                    phoneticAudioLicenseName = "phoneticAudioLicenseName"
                ),
                displayDefinitions = false,
                displayAudio = true,
                onDismissRequest = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordDialogAlertDialog",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogLexicalCategory",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogCloseButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#0",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#1",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#2",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinitionCredit",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogAudioCredit",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun displayDefinitions_DoNotDisplayAudio_ContainsDefinitions_ContainsDefinitionsSource_ContainsAudioSource_SomeVisible() {
        composeTestRule.setContent {
            WordDialog(
                modifier = Modifier,
                word = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree",
                    definitionWordSourceUrl = "definitionWordSourceUrl",
                    definitionWordLicenseUrl = "definitionWordLicenseUrl",
                    definitionWordLicenseName = "definitionWordLicenseName",
                    phoneticAudioSourceUrl = "phoneticAudioSourceUrl",
                    phoneticAudioLicenseUrl = "phoneticAudioLicenseUrl",
                    phoneticAudioLicenseName = "phoneticAudioLicenseName"
                ),
                displayDefinitions = true,
                displayAudio = false,
                onDismissRequest = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordDialogAlertDialog",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogLexicalCategory",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogCloseButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinitionCredit",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogAudioCredit",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun displayDefinitions_DisplayAudio_ContainsDefinitions_ContainsDefinitionsSource_DoesNotContainAudioSource_SomeVisible() {
        composeTestRule.setContent {
            WordDialog(
                modifier = Modifier,
                word = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree",
                    definitionWordSourceUrl = "definitionWordSourceUrl",
                    definitionWordLicenseUrl = "definitionWordLicenseUrl",
                    definitionWordLicenseName = "definitionWordLicenseName"
                ),
                displayDefinitions = true,
                displayAudio = true,
                onDismissRequest = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordDialogAlertDialog",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogLexicalCategory",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogCloseButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinition#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogDefinitionCredit",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "WordDialogAudioCredit",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun clickCloseButton_OnDismissRequestCalled() {
        var onDismissRequestCalled = false
        composeTestRule.setContent {
            WordDialog(
                modifier = Modifier,
                word = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree",
                    definitionWordSourceUrl = "definitionWordSourceUrl",
                    definitionWordLicenseUrl = "definitionWordLicenseUrl",
                    definitionWordLicenseName = "definitionWordLicenseName",
                    phoneticAudioSourceUrl = "phoneticAudioSourceUrl",
                    phoneticAudioLicenseUrl = "phoneticAudioLicenseUrl",
                    phoneticAudioLicenseName = "phoneticAudioLicenseName"
                ),
                displayDefinitions = true,
                displayAudio = true,
                onDismissRequest = { onDismissRequestCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "WordDialogCloseButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(onDismissRequestCalled)
    }
}