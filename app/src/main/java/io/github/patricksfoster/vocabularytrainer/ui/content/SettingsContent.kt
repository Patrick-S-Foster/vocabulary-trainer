package io.github.patricksfoster.vocabularytrainer.ui.content

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.service.db.WordDao
import io.github.patricksfoster.vocabularytrainer.service.settings.Settings
import io.github.patricksfoster.vocabularytrainer.service.settings.ThemeState
import io.github.patricksfoster.vocabularytrainer.ui.dialogs.ConfirmDialog
import kotlinx.coroutines.launch

@Composable
fun SettingsContent(
    contentPadding: PaddingValues,
    settings: Settings,
    wordDao: WordDao,
    lifecycleScope: LifecycleCoroutineScope,
    progressReset: () -> Unit
) {
    val radioOptions = listOf(
        Pair(stringResource(R.string.settings_theme_auto), ThemeState.AUTO),
        Pair(stringResource(R.string.settings_theme_light), ThemeState.LIGHT),
        Pair(stringResource(R.string.settings_theme_dark), ThemeState.DARK)
    )
    var showDialog by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.testTag("SettingsContentSoundEffectsTitle"),
                    text = stringResource(R.string.settings_sound_effects),
                    style = MaterialTheme.typography.titleLarge
                )
                Switch(
                    modifier = Modifier.testTag("SettingsContentSoundEffectsSwitch"),
                    checked = settings.soundEffectsEnabled.value,
                    onCheckedChange = { value -> settings.soundEffectsEnabled.value = value },
                    thumbContent = {
                        Icon(
                            imageVector = if (settings.soundEffectsEnabled.value) Icons.Filled.Check else Icons.Filled.Close,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                )
            }

            Column(
                modifier = Modifier
                    .padding(top = dimensionResource(R.dimen.settings_item_spacing))
                    .fillMaxWidth()
                    .selectableGroup(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.settings_sub_item_spacing))
            ) {
                Text(
                    modifier = Modifier.testTag("SettingsContentThemeTitle"),
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
                            modifier = Modifier
                                .padding(start = dimensionResource(R.dimen.settings_sub_item_padding))
                                .testTag("SettingsContentThemeText#${it.second}")
                        )

                        RadioButton(
                            selected = settings.themeState.value == it.second,
                            onClick = null,
                            modifier = Modifier
                                .padding(end = dimensionResource(R.dimen.settings_radio_button_padding))
                                .testTag("SettingsContentThemeButton#${it.second}")
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
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("SettingsContentResetButton"),
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

    if (showDialog) {
        ConfirmDialog(
            modifier = Modifier.testTag("SettingsContentConfirmDialog"),
            title = stringResource(R.string.confirm_reset_title),
            body = stringResource(R.string.confirm_reset_body),
            onCancel = { showDialog = false },
            onConfirm = {
                showDialog = false

                lifecycleScope.launch {
                    wordDao.resetProgress()
                    progressReset()
                }
            }
        )
    }
}