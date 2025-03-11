package io.github.patricksfoster.vocabularytrainer.ui.dialogs

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class ConfirmDialogTests {
    @get: Rule
    val composeTestRule = createComposeRule()

    @Test
    fun allVisible() {
        composeTestRule.setContent {
            ConfirmDialog(
                modifier = Modifier,
                title = "TITLE",
                body = "BODY",
                onCancel = {},
                onConfirm = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogCard",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogCloseButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogCloseButtonIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogBodyText",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogCancelButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogOkButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun titleIsDisplayed() {
        composeTestRule.setContent {
            ConfirmDialog(
                modifier = Modifier,
                title = "TITLE",
                body = "BODY",
                onCancel = {},
                onConfirm = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogTitle",
            useUnmergedTree = true
        ).assertTextEquals("TITLE")
    }

    @Test
    fun bodyIsDisplayed() {
        composeTestRule.setContent {
            ConfirmDialog(
                modifier = Modifier,
                title = "TITLE",
                body = "BODY",
                onCancel = {},
                onConfirm = {}
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogBodyText",
            useUnmergedTree = true
        ).assertTextEquals("BODY")
    }

    @Test
    fun closeButtonClicked_OnCancelCalled() {
        var onCancelCalled = false
        var onConfirmCalled = false
        composeTestRule.setContent {
            ConfirmDialog(
                modifier = Modifier,
                title = "TITLE",
                body = "BODY",
                onCancel = { onCancelCalled = true },
                onConfirm = { onConfirmCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogCloseButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(onCancelCalled)
        assert(!onConfirmCalled)
    }

    @Test
    fun cancelButtonClicked_OnCancelCalled() {
        var onCancelCalled = false
        var onConfirmCalled = false
        composeTestRule.setContent {
            ConfirmDialog(
                modifier = Modifier,
                title = "TITLE",
                body = "BODY",
                onCancel = { onCancelCalled = true },
                onConfirm = { onConfirmCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogCancelButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(onCancelCalled)
        assert(!onConfirmCalled)
    }

    @Test
    fun okButtonClicked_OnConfirmCalled() {
        var onCancelCalled = false
        var onConfirmCalled = false
        composeTestRule.setContent {
            ConfirmDialog(
                modifier = Modifier,
                title = "TITLE",
                body = "BODY",
                onCancel = { onCancelCalled = true },
                onConfirm = { onConfirmCalled = true }
            )
        }

        composeTestRule.onNodeWithTag(
            testTag = "ConfirmDialogOkButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(!onCancelCalled)
        assert(onConfirmCalled)
    }
}