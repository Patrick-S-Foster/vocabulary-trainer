package io.github.patricksfoster.vocabularytrainer

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
import androidx.compose.runtime.saveable.Saver
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
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.service.db.LanguageLevel
import io.github.patricksfoster.vocabularytrainer.service.db.WordDao
import io.github.patricksfoster.vocabularytrainer.service.db.WordDatabase
import io.github.patricksfoster.vocabularytrainer.service.settings.Settings
import io.github.patricksfoster.vocabularytrainer.service.settings.ThemeState
import io.github.patricksfoster.vocabularytrainer.ui.components.CenterAlignedTopBar
import io.github.patricksfoster.vocabularytrainer.ui.dialogs.ConfirmDialog
import io.github.patricksfoster.vocabularytrainer.ui.screens.HomeScreen
import io.github.patricksfoster.vocabularytrainer.ui.screens.LearningScreen
import io.github.patricksfoster.vocabularytrainer.ui.screens.Screen
import io.github.patricksfoster.vocabularytrainer.ui.theme.VocabularyTrainerTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    companion object {
        private val Context.settings: DataStore<Preferences> by preferencesDataStore(name = "settings")
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

            val settings = rememberSaveable(
                saver = Saver(
                    save = {
                        Pair(it.soundEffectsEnabled.value, it.themeState.value)
                    },
                    restore = {
                        Settings(applicationContext.settings, it.first, it.second)
                    }
                )
            ) {
                Settings(
                    dataStore = applicationContext.settings,
                    soundEffectsEnabled = true,
                    themeState = ThemeState.AUTO
                )
            }

            settings.Init()

            var dialogOpen by rememberSaveable { mutableStateOf(false) }

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
                        navigateUp = { dialogOpen = true }
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
                                playAudio = { playAudio(it) },
                                context = applicationContext
                            )
                        }

                        composable<Learning> {
                            BackHandler { dialogOpen = true }
                            LearningScreen(
                                contentPadding = innerPadding,
                                wordDao = wordDao,
                                playAudio = { audioUri ->
                                    playAudio(audioUri)
                                },
                                languageLevel = languageLevel.value,
                                lifecycleScope = lifecycleScope,
                                playSuccess = {
                                    if (settings.soundEffectsEnabled.value) {
                                        playSuccessOrFailure(true)
                                    }
                                },
                                playFailure = {
                                    if (settings.soundEffectsEnabled.value) {
                                        playSuccessOrFailure(false)
                                    }
                                }
                            )
                        }
                    }

                    if (dialogOpen) {
                        ConfirmDialog(
                            modifier = Modifier,
                            title = stringResource(R.string.confirm_stop_training_dialog_title),
                            body = stringResource(R.string.confirm_stop_training_dialog_body),
                            onCancel = {
                                dialogOpen = false
                            }, onConfirm = {
                                dialogOpen = false
                                navController.navigateUp()
                                screen = Screen.Home
                            })
                    }
                }
            }
        }
    }

    private fun playAudio(audioUri: String) {
        lifecycleScope.launch {
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

    private fun playSuccessOrFailure(success: Boolean) {
        lifecycleScope.launch {
            val mediaPlayer =
                MediaPlayer.create(
                    applicationContext,
                    if (success) R.raw.success else R.raw.failure
                )
            mediaPlayer.setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )

            mediaPlayer.start()
        }
    }
}