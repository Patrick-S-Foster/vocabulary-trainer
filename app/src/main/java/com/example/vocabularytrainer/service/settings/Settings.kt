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

class Settings(private val dataStore: DataStore<Preferences>) {
    companion object {
        private val soundEffectsKey = booleanPreferencesKey("sound_effects")
        private val dailyReminderKey = booleanPreferencesKey("daily_reminder")
        private val themeStateKey = intPreferencesKey("theme")
    }

    val soundEffectsEnabled: MutableState<Boolean> = mutableStateOf(true)
    val dailyRemindersEnabled: MutableState<Boolean> = mutableStateOf(true)
    val themeState: MutableState<Int> = mutableIntStateOf(ThemeState.AUTO)

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
            soundEffectsEnabled.value = getValue(soundEffectsKey, true)
            dailyRemindersEnabled.value = getValue(dailyReminderKey, true)
            themeState.value = getValue(themeStateKey, ThemeState.AUTO)
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