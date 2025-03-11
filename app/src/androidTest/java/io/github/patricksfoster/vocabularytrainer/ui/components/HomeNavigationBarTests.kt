package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class HomeNavigationBarTests {

    @get: Rule
    val composeTestRule = createComposeRule()

    private fun setUp(
        onNavigateHome: () -> Unit,
        onNavigateDictionary: () -> Unit,
        onNavigateSettings: () -> Unit
    ) {
        composeTestRule.setContent {
            HomeNavigationBar(
                onNavigateHome = onNavigateHome,
                onNavigateDictionary = onNavigateDictionary,
                onNavigateSettings = onNavigateSettings
            )
        }
    }

    @Test
    fun allVisible() {
        setUp(
            onNavigateHome = {},
            onNavigateDictionary = {},
            onNavigateSettings = {}
        )

        composeTestRule.onNodeWithTag(
            testTag = "NavigationBar",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItemIcon#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItemLabel#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#0",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItemIcon#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItemLabel#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#1",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItemIcon#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItemLabel#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#2",
            useUnmergedTree = true
        ).assertExists().assertIsDisplayed()
    }

    @Test
    fun homeIsSelected() {
        setUp(
            onNavigateHome = { },
            onNavigateDictionary = { },
            onNavigateSettings = { }
        )

        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#0",
            useUnmergedTree = true
        ).assertIsSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#1",
            useUnmergedTree = true
        ).assertIsNotSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#2",
            useUnmergedTree = true
        ).assertIsNotSelected()
    }

    @Test
    fun clickHome_OnNavigateHomeNotCalled_HomeIsSelected() {
        var onNavigateHomeCalled = false
        var onNavigateDictionaryCalled = false
        var onNavigateSettingsCalled = false

        setUp(
            onNavigateHome = { onNavigateHomeCalled = true },
            onNavigateDictionary = { onNavigateDictionaryCalled = true },
            onNavigateSettings = { onNavigateSettingsCalled = true }
        )

        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#0",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#0",
            useUnmergedTree = true
        ).assertIsSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#1",
            useUnmergedTree = true
        ).assertIsNotSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#2",
            useUnmergedTree = true
        ).assertIsNotSelected()

        assert(!onNavigateHomeCalled)
        assert(!onNavigateDictionaryCalled)
        assert(!onNavigateSettingsCalled)
    }

    @Test
    fun clickDictionary_OnNavigateDictionaryCalled_DictionaryIsSelected() {
        var onNavigateHomeCalled = false
        var onNavigateDictionaryCalled = false
        var onNavigateSettingsCalled = false

        setUp(
            onNavigateHome = { onNavigateHomeCalled = true },
            onNavigateDictionary = { onNavigateDictionaryCalled = true },
            onNavigateSettings = { onNavigateSettingsCalled = true }
        )

        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#1",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#0",
            useUnmergedTree = true
        ).assertIsNotSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#1",
            useUnmergedTree = true
        ).assertIsSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#2",
            useUnmergedTree = true
        ).assertIsNotSelected()

        assert(!onNavigateHomeCalled)
        assert(onNavigateDictionaryCalled)
        assert(!onNavigateSettingsCalled)
    }

    @Test
    fun clickSettings_OnNavigateSettingsCalled_SettingsIsSelected() {
        var onNavigateHomeCalled = false
        var onNavigateDictionaryCalled = false
        var onNavigateSettingsCalled = false

        setUp(
            onNavigateHome = { onNavigateHomeCalled = true },
            onNavigateDictionary = { onNavigateDictionaryCalled = true },
            onNavigateSettings = { onNavigateSettingsCalled = true }
        )

        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#2",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#0",
            useUnmergedTree = true
        ).assertIsNotSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#1",
            useUnmergedTree = true
        ).assertIsNotSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#2",
            useUnmergedTree = true
        ).assertIsSelected()

        assert(!onNavigateHomeCalled)
        assert(!onNavigateDictionaryCalled)
        assert(onNavigateSettingsCalled)
    }

    @Test
    fun clickSettings_ClickHome_OnNavigateSettingsCalled_OnNavigateHomeCalled_HomeIsSelected() {
        var onNavigateHomeCalled = false
        var onNavigateDictionaryCalled = false
        var onNavigateSettingsCalled = false

        setUp(
            onNavigateHome = { onNavigateHomeCalled = true },
            onNavigateDictionary = { onNavigateDictionaryCalled = true },
            onNavigateSettings = { onNavigateSettingsCalled = true }
        )

        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#2",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#0",
            useUnmergedTree = true
        ).assertHasClickAction().performClick()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#0",
            useUnmergedTree = true
        ).assertIsSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#1",
            useUnmergedTree = true
        ).assertIsNotSelected()
        composeTestRule.onNodeWithTag(
            testTag = "NavigationBarItem#2",
            useUnmergedTree = true
        ).assertIsNotSelected()

        assert(onNavigateHomeCalled)
        assert(!onNavigateDictionaryCalled)
        assert(onNavigateSettingsCalled)
    }
}