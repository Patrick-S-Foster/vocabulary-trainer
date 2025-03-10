package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class CenterAlignedTopBarTests {
    private val title: String = "TITLE"

    @get: Rule
    val composeTestRule = createComposeRule()

    private fun setUp_WithBackNavigation(navigateUp: () -> Unit) {
        composeTestRule.setContent {
            CenterAlignedTopBar(
                title = title,
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    }

    private fun setUp_WithInfo() {
        composeTestRule.setContent {
            CenterAlignedTopBar(
                title = title,
                canNavigateBack = false,
                navigateUp = {}
            )
        }
    }

    @Test
    fun withBackNavigation_BackComposablesVisible_InfoComposablesNotVisible_DialogNotVisible() {
        setUp_WithBackNavigation {}

        composeTestRule.onNodeWithTag(
            testTag = "CenterAlignedTopAppBar",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "CenterAlignedTopAppBarTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed().assertTextEquals(title)
        composeTestRule.onNodeWithTag(
            testTag = "BackIconButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "BackIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "InfoIconButton",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "InfoIcon",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "InformationDialog",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun withInfo_BackComposablesNotVisible_InfoComposablesVisible_DialogNotVisible() {
        setUp_WithInfo()

        composeTestRule.onNodeWithTag(
            testTag = "CenterAlignedTopAppBar",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "CenterAlignedTopAppBarTitle",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed().assertTextEquals(title)
        composeTestRule.onNodeWithTag(
            testTag = "BackIconButton",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "BackIcon",
            useUnmergedTree = true
        ).assertDoesNotExist()
        composeTestRule.onNodeWithTag(
            testTag = "InfoIconButton",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "InfoIcon",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "InformationDialog",
            useUnmergedTree = true
        ).assertDoesNotExist()
    }

    @Test
    fun backIconButtonClicked_CallsNavigateUp() {
        var callbackCalled = false
        setUp_WithBackNavigation { callbackCalled = true }

        composeTestRule.onNodeWithTag(
            testTag = "BackIconButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()

        assert(callbackCalled)
    }

    @Test
    fun infoIconButtonClicked_OpensInformationDialog() {
        setUp_WithInfo()

        composeTestRule.onNodeWithTag(
            testTag = "InfoIconButton",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "InformationDialog",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }
}