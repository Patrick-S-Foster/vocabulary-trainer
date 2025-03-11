package io.github.patricksfoster.vocabularytrainer.ui.dialogs

import android.app.Activity.RESULT_OK
import android.app.Instrumentation
import android.content.Intent
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class InformationDialogTests {
    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allVisible() {
        composeTestRule.setContent {
            InformationDialog(
                modifier = Modifier,
                onDismissRequest = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogAlertDialog",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogCloseButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogOxfordBody",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogOxfordButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogApiBody",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogApiButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun closeButtonClicked_OnDismissRequestCalled() {
        var onDismissRequestCalled = false
        composeTestRule.setContent {
            InformationDialog(
                modifier = Modifier,
                onDismissRequest = { onDismissRequestCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogCloseButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(onDismissRequestCalled)
    }

    @Test
    fun oxfordButtonClicked_StartsIntent() {
        var onDismissRequestCalled = false
        composeTestRule.setContent {
            InformationDialog(
                modifier = Modifier,
                onDismissRequest = { onDismissRequestCalled = true }
            )
        }

        intending(hasAction(Intent.ACTION_VIEW)).respondWith(
            Instrumentation.ActivityResult(
                RESULT_OK,
                null
            )
        )

        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogOxfordButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(!onDismissRequestCalled)
        intended(hasAction(Intent.ACTION_VIEW))
    }

    @Test
    fun apiButtonClicked_StartsIntent() {
        var onDismissRequestCalled = false
        composeTestRule.setContent {
            InformationDialog(
                modifier = Modifier,
                onDismissRequest = { onDismissRequestCalled = true }
            )
        }

        intending(hasAction(Intent.ACTION_VIEW)).respondWith(
            Instrumentation.ActivityResult(
                RESULT_OK,
                null
            )
        )

        composeTestRule.onNodeWithTag(
            testTag = "InformationDialogApiButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(!onDismissRequestCalled)
        intended(hasAction(Intent.ACTION_VIEW))
    }
}