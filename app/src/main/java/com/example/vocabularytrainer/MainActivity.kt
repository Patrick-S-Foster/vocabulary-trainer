package com.example.vocabularytrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.room.Room
import com.example.vocabularytrainer.service.db.WordDao
import com.example.vocabularytrainer.service.db.WordDatabase
import com.example.vocabularytrainer.ui.components.CenterAlignedTopBar
import com.example.vocabularytrainer.ui.components.HomeNavigationBar
import com.example.vocabularytrainer.ui.components.LanguageFloatingActionButton
import com.example.vocabularytrainer.ui.content.HomeContent
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme

class MainActivity : ComponentActivity() {

    private val wordDao: WordDao by lazy {
        Room.databaseBuilder(applicationContext, WordDatabase::class.java, WordDatabase.NAME)
            .build()
            .wordDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            VocabularyTrainerTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { CenterAlignedTopBar(stringResource(R.string.app_name)) },
                    bottomBar = {
                        HomeNavigationBar(
                            onNavigateHome = {},
                            onNavigateDictionary = {},
                            onNavigateSettings = {})
                    },
                    floatingActionButton = {
                        LanguageFloatingActionButton(
                            onExpanded = {},
                            onClosed = {},
                            onClick = {})
                    }
                ) { innerPadding ->
                    HomeContent(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(
                                start = dimensionResource(R.dimen.content_padding_horizontal),
                                end = dimensionResource(R.dimen.content_padding_horizontal),
                                top = dimensionResource(R.dimen.content_padding_vertical)
                            )
                    )
                }
            }
        }
    }
}