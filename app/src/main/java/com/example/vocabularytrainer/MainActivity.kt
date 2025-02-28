package com.example.vocabularytrainer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.vocabularytrainer.service.db.LanguageLevel
import com.example.vocabularytrainer.service.db.Word
import com.example.vocabularytrainer.service.db.WordDao
import com.example.vocabularytrainer.service.db.WordDatabase
import com.example.vocabularytrainer.ui.components.CenterAlignedTopBar
import com.example.vocabularytrainer.ui.components.HomeNavigationBar
import com.example.vocabularytrainer.ui.components.LanguageFloatingActionButton
import com.example.vocabularytrainer.ui.content.HomeContent
import com.example.vocabularytrainer.ui.theme.VocabularyTrainerTheme
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private val wordDao: WordDao by lazy {
        Room.databaseBuilder(applicationContext, WordDatabase::class.java, WordDatabase.NAME)
            .createFromAsset(WordDatabase.ASSET_NAME)
            .build()
            .wordDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val map = mutableStateMapOf<LanguageLevel, Pair<Int, Int>>()

        for (languageLevel in LanguageLevel.entries) {
            lifecycleScope.launch {
                val completedCount = wordDao.getLearnedCount(languageLevel.title)
                val totalCount = wordDao.getTotalCount(languageLevel.title)

                map[languageLevel] = Pair(completedCount, totalCount)
            }
        }

        var wordOfTheDay: Word? by mutableStateOf(null)

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
                            ),
                        languageLevelProgress = map,
                        wordOfTheDay = wordOfTheDay
                    )
                }
            }
        }
    }
}