package io.github.patricksfoster.vocabularytrainer.ui.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import io.github.patricksfoster.vocabularytrainer.TestUtil
import org.junit.Rule
import org.junit.Test

class AudioToMultiWordContentTests {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allVisible() {
        composeTestRule.setContent {
            AudioToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = {},
                correctWord = TestUtil.createWord(phoneticAudioUrl = "correct"),
                firstIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "firstIncorrect"),
                secondIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "secondIncorrect"),
                thirdIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "thirdIncorrect"),
                onSuccess = {},
                onFailure = {},
                onContinue = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentAudioButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentAudioButtonIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentDivider",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#3",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentSubmitButtonText",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun clickAudioButton_PlayAudioCalled() {
        var audioUrl: String? = null
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            AudioToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = { audioUrl = it },
                correctWord = TestUtil.createWord(phoneticAudioUrl = "correct"),
                firstIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "firstIncorrect"),
                secondIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "secondIncorrect"),
                thirdIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "thirdIncorrect"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentAudioButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(audioUrl == "correct")
        assert(!onSuccessCalled)
        assert(!onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun correctWordClicked_SubmitButtonClicked_OnSuccessCalled() {
        var audioUrl: String? = null
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            AudioToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = { audioUrl = it },
                correctWord = TestUtil.createWord(phoneticAudioUrl = "correct"),
                firstIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "firstIncorrect"),
                secondIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "secondIncorrect"),
                thirdIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "thirdIncorrect"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#0",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertIsEnabled().assertHasClickAction().performClick()

        assert(audioUrl == null)
        assert(onSuccessCalled)
        assert(!onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun firstIncorrectWordClicked_SubmitButtonClicked_OnFailureCalled() {
        var audioUrl: String? = null
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            AudioToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = { audioUrl = it },
                correctWord = TestUtil.createWord(phoneticAudioUrl = "correct"),
                firstIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "firstIncorrect"),
                secondIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "secondIncorrect"),
                thirdIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "thirdIncorrect"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#1",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertIsEnabled().assertHasClickAction().performClick()

        assert(audioUrl == null)
        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun secondIncorrectWordClicked_SubmitButtonClicked_OnFailureCalled() {
        var audioUrl: String? = null
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            AudioToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = { audioUrl = it },
                correctWord = TestUtil.createWord(phoneticAudioUrl = "correct"),
                firstIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "firstIncorrect"),
                secondIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "secondIncorrect"),
                thirdIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "thirdIncorrect"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#2",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertIsEnabled().assertHasClickAction().performClick()

        assert(audioUrl == null)
        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun thirdIncorrectWordClicked_SubmitButtonClicked_OnFailureCalled() {
        var audioUrl: String? = null
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            AudioToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = { audioUrl = it },
                correctWord = TestUtil.createWord(phoneticAudioUrl = "correct"),
                firstIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "firstIncorrect"),
                secondIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "secondIncorrect"),
                thirdIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "thirdIncorrect"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#3",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertIsEnabled().assertHasClickAction().performClick()

        assert(audioUrl == null)
        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(!onContinueCalled)
    }

    @Test
    fun successfulSubmit_SubmitButtonClickedAgain_OnContinueCalled() {
        var audioUrl: String? = null
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            AudioToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = { audioUrl = it },
                correctWord = TestUtil.createWord(phoneticAudioUrl = "correct"),
                firstIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "firstIncorrect"),
                secondIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "secondIncorrect"),
                thirdIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "thirdIncorrect"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#0",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertIsEnabled().assertHasClickAction().performClick().performClick()

        assert(audioUrl == null)
        assert(onSuccessCalled)
        assert(!onFailureCalled)
        assert(onContinueCalled)
    }

    @Test
    fun failureSubmit_SubmitButtonClickedAgain_OnContinueCalled() {
        var audioUrl: String? = null
        var onSuccessCalled = false
        var onFailureCalled = false
        var onContinueCalled = false
        composeTestRule.setContent {
            AudioToMultiWordContent(
                contentPadding = PaddingValues(),
                playAudio = { audioUrl = it },
                correctWord = TestUtil.createWord(phoneticAudioUrl = "correct"),
                firstIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "firstIncorrect"),
                secondIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "secondIncorrect"),
                thirdIncorrectWord = TestUtil.createWord(phoneticAudioUrl = "thirdIncorrect"),
                onSuccess = { onSuccessCalled = true },
                onFailure = { onFailureCalled = true },
                onContinue = { onContinueCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentWordCard#1",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        composeTestRule.mainClock.advanceTimeBy(1000)

        composeTestRule.onNodeWithTag(
            testTag = "AudioToMultiWordContentSubmitButton",
            useUnmergedTree = true
        ).assertIsEnabled().assertHasClickAction().performClick().performClick()

        assert(audioUrl == null)
        assert(!onSuccessCalled)
        assert(onFailureCalled)
        assert(onContinueCalled)
    }
}