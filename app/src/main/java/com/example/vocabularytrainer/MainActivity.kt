package com.example.vocabularytrainer

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.vocabularytrainer.service.db.WordDao
import com.example.vocabularytrainer.service.db.WordDatabase
import com.example.vocabularytrainer.service.settings.Settings
import com.example.vocabularytrainer.service.settings.ThemeState
import com.example.vocabularytrainer.ui.components.CenterAlignedTopBar
import com.example.vocabularytrainer.ui.screens.HomeScreen
import com.example.vocabularytrainer.ui.screens.Screen
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            settings.Init()

            VocabularyTrainerTheme(
                darkTheme = settings.themeState.value == ThemeState.DARK || settings.themeState.value == ThemeState.AUTO && isSystemInDarkTheme()
            ) {
                Scaffold(topBar = {
                    CenterAlignedTopBar(
                        Screen.Home,
                        false
                    )
                }) {
                    HomeScreen(
                        settings,
                        wordDao,
                        lifecycleScope,
                        Modifier.padding(top = it.calculateTopPadding())
                    )
                }
            }
        }
    }
}