package com.example.vocabularytrainer

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.vocabularytrainer.service.db.LanguageLevel
import com.example.vocabularytrainer.service.db.Word
import com.example.vocabularytrainer.service.db.WordDao
import com.example.vocabularytrainer.service.db.WordDatabase
import com.example.vocabularytrainer.service.settings.ThemeState
import com.example.vocabularytrainer.ui.components.CenterAlignedTopBar
import com.example.vocabularytrainer.ui.components.HomeNavigationBar
import com.example.vocabularytrainer.ui.components.LanguageFloatingActionButton
import com.example.vocabularytrainer.ui.content.DictionaryContent
import com.example.vocabularytrainer.ui.content.HomeContent
import com.example.vocabularytrainer.ui.content.SettingsContent
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    companion object {
        private val Context.settings: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }

    private enum class DisplayedContent {
        Home,
        Dictionary,
        Settings
    }

    private val wordDao: WordDao by lazy {
        Room.databaseBuilder(applicationContext, WordDatabase::class.java, WordDatabase.NAME)
            .createFromAsset(WordDatabase.ASSET_NAME)
            .build()
            .wordDao()
    }

    private val soundEffectsKey = booleanPreferencesKey("sound_effects")
    private val dailyReminderKey = booleanPreferencesKey("daily_reminder")
    private val themeStateKey = intPreferencesKey("theme")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val map = mutableStateMapOf<LanguageLevel, Pair<Int, Int>>()
        var wordOfTheDay: Word? by mutableStateOf(null)
        val soundEffectsEnabled = mutableStateOf(true)
        val dailyReminderEnabled = mutableStateOf(true)
        val themeState = mutableIntStateOf(ThemeState.AUTO)

        for (languageLevel in LanguageLevel.entries) {
            lifecycleScope.launch {
                val completedCount = wordDao.getLearnedCount(languageLevel.title)
                val totalCount = wordDao.getTotalCount(languageLevel.title)

                map[languageLevel] = Pair(completedCount, totalCount)
            }
        }

        lifecycleScope.launch {
            wordOfTheDay = wordDao.getWordOfTheDay(
                LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)
            )

            if (wordOfTheDay == null) {
                wordOfTheDay = wordDao.getRandomWords().firstOrNull()

                if (wordOfTheDay != null) {
                    wordOfTheDay!!.wordOfTheDayDate =
                        LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE)
                    wordDao.updateAll(wordOfTheDay!!)
                }
            }
        }

        lifecycleScope.launch {
            val soundEffectsFlow = applicationContext.settings.data.map { settings ->
                settings[soundEffectsKey] ?: true
            }
            soundEffectsFlow.collect { value ->
                soundEffectsEnabled.value = value
            }
        }

        lifecycleScope.launch {
            val dailyReminderFlow = applicationContext.settings.data.map { settings ->
                settings[dailyReminderKey] ?: true
            }
            dailyReminderFlow.collect { value ->
                dailyReminderEnabled.value = value
            }
        }

        lifecycleScope.launch {
            val themeStateFlow = applicationContext.settings.data.map { settings ->
                settings[themeStateKey] ?: ThemeState.AUTO
            }
            themeStateFlow.collect { value ->
                themeState.intValue = value
            }
        }

        enableEdgeToEdge()
        setContent {
            LaunchedEffect(soundEffectsEnabled.value) {
                applicationContext.settings.edit { settings ->
                    settings[soundEffectsKey] = soundEffectsEnabled.value
                }
            }

            LaunchedEffect(dailyReminderEnabled.value) {
                applicationContext.settings.edit { settings ->
                    settings[dailyReminderKey] = dailyReminderEnabled.value
                }
            }

            LaunchedEffect(themeState.intValue) {
                applicationContext.settings.edit { settings ->
                    settings[themeStateKey] = themeState.intValue
                }
            }

            VocabularyTrainerTheme(
                darkTheme = themeState.intValue == ThemeState.DARK || themeState.intValue == ThemeState.AUTO && isSystemInDarkTheme()
            ) {
                var displayedContent by rememberSaveable { mutableStateOf(DisplayedContent.Home) }
                val fabExpanded = rememberSaveable { mutableStateOf(false) }

                val overlayTransition = updateTransition(targetState = fabExpanded.value)
                val overlayOpacity by overlayTransition.animateFloat {
                    if (it) 1.0F else 0.0F
                }

                Scaffold(
                    topBar = {
                        CenterAlignedTopBar(stringResource(R.string.app_name))
                    },
                    bottomBar = {
                        HomeNavigationBar(
                            onNavigateHome = {
                                displayedContent = DisplayedContent.Home
                                fabExpanded.value = false
                            },
                            onNavigateDictionary = {
                                displayedContent = DisplayedContent.Dictionary
                                fabExpanded.value = false
                            },
                            onNavigateSettings = {

                                displayedContent = DisplayedContent.Settings
                                fabExpanded.value = false
                            })
                    },
                    floatingActionButton = {
                        if (displayedContent == DisplayedContent.Home || displayedContent == DisplayedContent.Dictionary) {
                            LanguageFloatingActionButton(
                                expanded = fabExpanded,
                                onClick = {})
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        when (displayedContent) {
                            DisplayedContent.Home -> {
                                HomeContent(
                                    contentPadding = PaddingValues(
                                        horizontal = dimensionResource(R.dimen.content_padding_horizontal),
                                        vertical = dimensionResource(R.dimen.content_padding_vertical)
                                    ),
                                    languageLevelProgress = map,
                                    wordOfTheDay = wordOfTheDay
                                )
                            }

                            DisplayedContent.Dictionary -> {
                                DictionaryContent(
                                    wordDao = wordDao,
                                    lifecycleScope = lifecycleScope
                                )
                            }

                            else -> {
                                SettingsContent(
                                    contentPadding = PaddingValues(
                                        horizontal = dimensionResource(R.dimen.content_padding_horizontal),
                                        vertical = dimensionResource(R.dimen.content_padding_vertical)
                                    ),
                                    soundEffectsEnabled = rememberSaveable { soundEffectsEnabled },
                                    dailyReminderEnabled = rememberSaveable { dailyReminderEnabled },
                                    themeState = rememberSaveable { themeState }
                                ) {
                                    lifecycleScope.launch {
                                        wordDao.resetProgress()
                                    }
                                }
                            }
                        }

                        val backgroundOverlayColor = colorResource(R.color.background_overlay)

                        if (fabExpanded.value || overlayOpacity > 0.0F) {
                            Canvas(modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(true) {
                                    fabExpanded.value = false
                                }) {
                                drawRect(
                                    color = backgroundOverlayColor,
                                    alpha = overlayOpacity
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}