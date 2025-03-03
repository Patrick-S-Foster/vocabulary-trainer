package com.example.vocabularytrainer.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.settings.ThemeState

@Composable
fun SettingsContent(
    modifier: Modifier,
    contentPadding: PaddingValues,
    soundEffectsEnabled: MutableState<Boolean>,
    dailyReminderEnabled: MutableState<Boolean>,
    themeState: MutableState<Int>,
    onResetProgress: () -> Unit
) {
    val switchItems = listOf(
        Pair(stringResource(R.string.settings_sound_effects), soundEffectsEnabled),
        Pair(stringResource(R.string.settings_daily_reminder), dailyReminderEnabled)
    )
    val radioOptions = listOf(
        Pair(stringResource(R.string.settings_theme_auto), ThemeState.AUTO),
        Pair(stringResource(R.string.settings_theme_light), ThemeState.LIGHT),
        Pair(stringResource(R.string.settings_theme_dark), ThemeState.DARK)
    )

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.settings_item_spacing))) {
                switchItems.forEach { switchItem ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = switchItem.first, style = MaterialTheme.typography.titleLarge)
                        Switch(
                            checked = switchItem.second.value,
                            onCheckedChange = { value ->
                                switchItem.second.value = value
                            },
                            thumbContent = {
                                Icon(
                                    imageVector = if (switchItem.second.value) Icons.Filled.Check else Icons.Filled.Close,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize)
                                )
                            }
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.settings_sub_item_spacing))
            ) {
                Text(
                    text = stringResource(R.string.settings_theme),
                    style = MaterialTheme.typography.titleLarge
                )

                radioOptions.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = themeState.value == option.second,
                                onClick = { themeState.value = option.second },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option.first,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = dimensionResource(R.dimen.settings_sub_item_padding))
                        )

                        RadioButton(
                            selected = themeState.value == option.second,
                            onClick = null,
                            modifier = Modifier.padding(end = dimensionResource(R.dimen.settings_radio_button_padding))
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.settings_item_spacing)))
        }

        item {
            Button(
                onClick = onResetProgress,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    MaterialTheme.colorScheme.error,
                    MaterialTheme.colorScheme.onError,
                    MaterialTheme.colorScheme.errorContainer,
                    MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text(text = stringResource(R.string.settings_reset_progress))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsContentPreview() {
    SettingsContent(
        Modifier,
        PaddingValues(),
        rememberSaveable { mutableStateOf(false) },
        rememberSaveable { mutableStateOf(false) },
        rememberSaveable { mutableIntStateOf(ThemeState.AUTO) },
    ) {}
}