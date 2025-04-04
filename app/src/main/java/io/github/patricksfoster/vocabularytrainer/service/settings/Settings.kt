package io.github.patricksfoster.vocabularytrainer.service.settings

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
    themeState: Int
) {
    companion object {
        // These keys are used to retrieve values from the data store
        private val soundEffectsKey = booleanPreferencesKey("sound_effects")
        private val themeStateKey = intPreferencesKey("theme")
    }

    // These are the settings values
    val soundEffectsEnabled: MutableState<Boolean> = mutableStateOf(soundEffectsEnabled)
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
            themeState.value = getValue(themeStateKey, themeState.value)
        }

        LaunchedEffect(soundEffectsEnabled.value) {
            setValue(soundEffectsKey, soundEffectsEnabled.value)
        }

        LaunchedEffect(themeState.value) {
            setValue(themeStateKey, themeState.value)
        }
    }
}