package com.example.vocabularytrainer

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.vocabularytrainer.service.db.LanguageLevel
import com.example.vocabularytrainer.service.db.WordDao
import com.example.vocabularytrainer.service.db.WordDatabase
import com.example.vocabularytrainer.service.settings.Settings
import com.example.vocabularytrainer.service.settings.ThemeState
import com.example.vocabularytrainer.ui.components.CenterAlignedTopBar
import com.example.vocabularytrainer.ui.screens.HomeScreen
import com.example.vocabularytrainer.ui.screens.LearningScreen
import com.example.vocabularytrainer.ui.screens.Screen
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    companion object {
        private val Context.settings: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }

    private val settings: Settings by lazy {
        Settings(applicationContext.settings)
    }

    private val wordDao: WordDao by lazy {
        Room.databaseBuilder(applicationContext, WordDatabase::class.java, WordDatabase.NAME)
            .createFromAsset(WordDatabase.ASSET_NAME)
            .build()
            .wordDao()
    }

    @Serializable
    private object Home

    @Serializable
    private class Learning

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var screen: Screen by rememberSaveable { mutableStateOf(Screen.Home) }
            val languageLevel = rememberSaveable { mutableStateOf(LanguageLevel.A1) }

            settings.Init()

            VocabularyTrainerTheme(
                darkTheme = settings.themeState.value == ThemeState.DARK || settings.themeState.value == ThemeState.AUTO && isSystemInDarkTheme()
            ) {
                Scaffold(topBar = {
                    CenterAlignedTopBar(
                        if (screen == Screen.Home) {
                            stringResource(R.string.app_name)
                        } else {
                            stringResource(R.string.learning_title, languageLevel.value.title)
                        },
                        canNavigateBack = screen != Screen.Home,
                        navigateUp = {
                            navController.navigateUp()
                            screen = Screen.Home
                        }
                    )
                }) { innerPadding ->
                    NavHost(navController = navController, startDestination = Home) {
                        composable<Home> {
                            HomeScreen(
                                settings = settings,
                                wordDao = wordDao,
                                lifecycleScope = lifecycleScope,
                                onFabClick = {
                                    navController.navigate(route = Learning())
                                    languageLevel.value = it
                                    screen = Screen.LearningDefinitionToMultiWord
                                },
                                modifier = Modifier.padding(
                                    top = innerPadding.calculateTopPadding(),
                                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current)
                                ),
                                playAudio = { playAudio(it) }
                            )
                        }

                        composable<Learning> {
                            BackHandler {
                                // TODO: provide a prompt to see if the user wants to stop their training
                            }
                            LearningScreen(
                                contentPadding = innerPadding,
                                wordDao = wordDao,
                                settings = settings,
                                playAudio = { audioUri ->
                                    playAudio(audioUri)
                                },
                                languageLevel = languageLevel.value,
                                lifecycleScope = lifecycleScope
                            )
                        }
                    }
                }
            }
        }
    }

    private fun playAudio(audioUri: String) {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()
        )

        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
        }

        mediaPlayer.setDataSource(audioUri)
        mediaPlayer.prepareAsync()
    }
}