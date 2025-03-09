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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.WordDao
import com.example.vocabularytrainer.service.settings.Settings
import com.example.vocabularytrainer.service.settings.ThemeState
import kotlinx.coroutines.launch

@Composable
fun SettingsContent(
    contentPadding: PaddingValues,
    settings: Settings,
    wordDao: WordDao,
    lifecycleScope: LifecycleCoroutineScope,
    progressReset: () -> Unit
) {
    val switchItems = listOf(
        Pair(stringResource(R.string.settings_sound_effects), settings.soundEffectsEnabled),
        Pair(stringResource(R.string.settings_daily_reminder), settings.dailyRemindersEnabled)
    )
    val radioOptions = listOf(
        Pair(stringResource(R.string.settings_theme_auto), ThemeState.AUTO),
        Pair(stringResource(R.string.settings_theme_light), ThemeState.LIGHT),
        Pair(stringResource(R.string.settings_theme_dark), ThemeState.DARK)
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.settings_item_spacing))) {
                switchItems.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it.first, style = MaterialTheme.typography.titleLarge)
                        Switch(
                            checked = it.second.value,
                            onCheckedChange = { value -> it.second.value = value },
                            thumbContent = {
                                Icon(
                                    imageVector = if (it.second.value) Icons.Filled.Check else Icons.Filled.Close,
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
                    .padding(top = dimensionResource(R.dimen.settings_item_spacing))
                    .fillMaxWidth()
                    .selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.settings_sub_item_spacing))
            ) {
                Text(
                    text = stringResource(R.string.settings_theme),
                    style = MaterialTheme.typography.titleLarge
                )

                radioOptions.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = settings.themeState.value == it.second,
                                onClick = { settings.themeState.value = it.second },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it.first,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = dimensionResource(R.dimen.settings_sub_item_padding))
                        )

                        RadioButton(
                            selected = settings.themeState.value == it.second,
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
                onClick = {
                    lifecycleScope.launch {
                        wordDao.resetProgress()
                        progressReset()
                    }
                },
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