package io.github.patricksfoster.vocabularytrainer.ui.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import io.github.patricksfoster.vocabularytrainer.TestUtil
import org.junit.Rule
import org.junit.Test

class DefinitionToWordContentTests {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allVisible_ExceptWordCardAndContinueButton() {
        composeTestRule.setContent {
            DefinitionToWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                word = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                onSuccess = {},
                onFailure = {},
                onContinue = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBar",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentInputField",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBarIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDivider",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentWordCard",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentContinueButton",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentContinueButtonText",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun threeDefinitions_ThreeDefinitionsVisible() {
        composeTestRule.setContent {
            DefinitionToWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                word = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                onSuccess = {},
                onFailure = {},
                onContinue = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun twoDefinitions_TwoDefinitionsVisible() {
        composeTestRule.setContent {
            DefinitionToWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                word = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo"
                ),
                onSuccess = {},
                onFailure = {},
                onContinue = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#2",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#2",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun oneDefinition_OneDefinitionVisible() {
        composeTestRule.setContent {
            DefinitionToWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                word = TestUtil.createWord(definitionOne = "definitionOne"),
                onSuccess = {},
                onFailure = {},
                onContinue = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#1",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#2",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#1",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#2",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun wrongWordSubmitted_OnFailureCalled_VisibilityOfElementsChanges() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                word = TestUtil.createWord(word = "CORRECT_WORD", definitionOne = "definitionOne"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentInputField",
            useUnmergedTree = true
        ).performTextInput("INCORRECT_WORD")
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBarIcon",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBar",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentInputField",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBarIcon",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDivider",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#0",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#1",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#2",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#0",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#1",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#2",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentWordCard",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentContinueButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentContinueButtonText",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()

        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun correctWordSubmitted_OnSuccessCalled_VisibilityOfElementsChanges() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                word = TestUtil.createWord(word = "CORRECT_WORD", definitionOne = "definitionOne"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentInputField",
            useUnmergedTree = true
        ).performTextInput("CORRECT_WORD")
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBarIcon",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBar",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentInputField",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBarIcon",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDivider",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#0",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#1",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinitionLabel#2",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#0",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#1",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentDefinition#2",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentWordCard",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentContinueButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentContinueButtonText",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()

        assert(onSuccessCalled)
        assert(!onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun wrongWordSubmitted_ContinueButtonClicked_OnFailureAndOnContinueCalled() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                word = TestUtil.createWord(word = "CORRECT_WORD", definitionOne = "definitionOne"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentInputField",
            useUnmergedTree = true
        ).performTextInput("INCORRECT_WORD")
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBarIcon",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentContinueButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(onContinueCalled)
    }

    @Test
    fun correctWordSubmitted_ContinueButtonClicked_OnSuccessAndOnContinueCalled() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                word = TestUtil.createWord(word = "CORRECT_WORD", definitionOne = "definitionOne"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentInputField",
            useUnmergedTree = true
        ).performTextInput("CORRECT_WORD")
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentSearchBarIcon",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToWordContentContinueButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(onSuccessCalled)
        assert(!onFailureCalled)
        assert(onContinueCalled)
    }
}