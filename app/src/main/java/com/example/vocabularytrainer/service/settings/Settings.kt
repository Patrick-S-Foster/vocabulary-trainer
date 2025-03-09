package com.example.vocabularytrainer.service.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class Settings(
    private val dataStore: DataStore<Preferences>,
    soundEffectsEnabled: Boolean,
    dailyRemindersEnabled: Boolean,
    themeState: Int
) {
    companion object {
        private val soundEffectsKey = booleanPreferencesKey("sound_effects")
        private val dailyReminderKey = booleanPreferencesKey("daily_reminder")
        private val themeStateKey = intPreferencesKey("theme")
    }

    val soundEffectsEnabled: MutableState<Boolean> = mutableStateOf(soundEffectsEnabled)
    val dailyRemindersEnabled: MutableState<Boolean> = mutableStateOf(dailyRemindersEnabled)
    val themeState: MutableState<Int> = mutableIntStateOf(themeState)

    private suspend fun <T> getValue(key: Preferences.Key<T>, default: T): T {
        return dataStore.data.map {
            it[key] ?: default
        }.first()
    }

    private suspend fun <T> setValue(key: Preferences.Key<T>, value: T) {
        dataStore.edit {
            it[key] = value
        }
    }

    @Composable
    fun Init() {
        LaunchedEffect(true) {
            soundEffectsEnabled.value = getValue(soundEffectsKey, soundEffectsEnabled.value)
            dailyRemindersEnabled.value = getValue(dailyReminderKey, dailyRemindersEnabled.value)
            themeState.value = getValue(themeStateKey, themeState.value)
        }

        LaunchedEffect(soundEffectsEnabled.value) {
            setValue(soundEffectsKey, soundEffectsEnabled.value)
        }

        LaunchedEffect(dailyRemindersEnabled.value) {
            setValue(dailyReminderKey, dailyRemindersEnabled.value)
        }

        LaunchedEffect(themeState.value) {
            setValue(themeStateKey, themeState.value)
        }
    }
}