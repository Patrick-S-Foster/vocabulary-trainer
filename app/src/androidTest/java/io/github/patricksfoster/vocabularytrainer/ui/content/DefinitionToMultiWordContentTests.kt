package io.github.patricksfoster.vocabularytrainer.ui.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.github.patricksfoster.vocabularytrainer.TestUtil
import org.junit.Rule
import org.junit.Test

class DefinitionToMultiWordContentTests {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allVisible() {
        composeTestRule.setContent {
            DefinitionToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                correctWord = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                firstIncorrectWord = TestUtil.createWord(),
                secondIncorrectWord = TestUtil.createWord(),
                thirdIncorrectWord = TestUtil.createWord(),
                onSuccess = {},
                onFailure = {},
                onContinue = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentDefinition",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentDivider",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#3",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentSubmitButtonText",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun definitionOneVisible() {
        composeTestRule.setContent {
            DefinitionToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                correctWord = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                firstIncorrectWord = TestUtil.createWord(),
                secondIncorrectWord = TestUtil.createWord(),
                thirdIncorrectWord = TestUtil.createWord(),
                onSuccess = {},
                onFailure = {},
                onContinue = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentDefinition",
            useUnmergedTree = true
        ).assertTextEquals("definitionOne")
    }

    @Test
    fun correctWordClicked_SubmitButtonClicked_OnSuccessCalled() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                correctWord = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                firstIncorrectWord = TestUtil.createWord(),
                secondIncorrectWord = TestUtil.createWord(),
                thirdIncorrectWord = TestUtil.createWord(),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#0",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(onSuccessCalled)
        assert(!onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun firstIncorrectWordClicked_SubmitButtonClicked_OnFailureCalled() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                correctWord = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                firstIncorrectWord = TestUtil.createWord(),
                secondIncorrectWord = TestUtil.createWord(),
                thirdIncorrectWord = TestUtil.createWord(),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#1",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun secondIncorrectWordClicked_SubmitButtonClicked_OnFailureCalled() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                correctWord = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                firstIncorrectWord = TestUtil.createWord(),
                secondIncorrectWord = TestUtil.createWord(),
                thirdIncorrectWord = TestUtil.createWord(),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#2",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun thirdIncorrectWordClicked_SubmitButtonClicked_OnFailureCalled() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                correctWord = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                firstIncorrectWord = TestUtil.createWord(),
                secondIncorrectWord = TestUtil.createWord(),
                thirdIncorrectWord = TestUtil.createWord(),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#3",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun successfulSubmit_SubmitButtonClickedAgain_OnContinueCalled() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                correctWord = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                firstIncorrectWord = TestUtil.createWord(),
                secondIncorrectWord = TestUtil.createWord(),
                thirdIncorrectWord = TestUtil.createWord(),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#0",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick().performClick()

        assert(onSuccessCalled)
        assert(!onFailureCalled)
        assert(onContinueCalled)
    }

    @Test
    fun failureSubmit_SubmitButtonClickedAgain_OnContinueCalled() {
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            DefinitionToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                correctWord = TestUtil.createWord(
                    definitionOne = "definitionOne",
                    definitionTwo = "definitionTwo",
                    definitionThree = "definitionThree"
                ),
                firstIncorrectWord = TestUtil.createWord(),
                secondIncorrectWord = TestUtil.createWord(),
                thirdIncorrectWord = TestUtil.createWord(),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentWordCard#1",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "DefinitionToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick().performClick()

        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(onContinueCalled)
    }
}