package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabularytrainer.R

private const val HomeIndex = 0
private const val DictionaryIndex = 1
private const val SettingsIndex = 2

@Composable
fun HomeNavigationBar(
    onNavigateHome: () -> Unit,
    onNavigateDictionary: () -> Unit,
    onNavigateSettings: () -> Unit
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(HomeIndex) }
    val tabs = listOf(
        stringResource(R.string.home_tab),
        stringResource(R.string.dictionary_tab),
        stringResource(R.string.settings_tab)
    )
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Translate, Icons.Filled.Settings)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.Translate, Icons.Outlined.Settings)

    NavigationBar {
        tabs.forEachIndexed { index, tab ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedIndex == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = tab,
                    )
                },
                label = { Text(tab) },
                selected = selectedIndex == index,
                onClick = {
                    if (selectedIndex != index) {
                        selectedIndex = index

                        when (selectedIndex) {
                            HomeIndex -> onNavigateHome()
                            DictionaryIndex -> onNavigateDictionary()
                            SettingsIndex -> onNavigateSettings()
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun HomeNavigationBarPreview() {
    HomeNavigationBar({}, {}, {})
}