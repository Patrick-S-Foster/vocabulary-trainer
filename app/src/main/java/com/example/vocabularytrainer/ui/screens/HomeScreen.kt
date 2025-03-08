package com.example.vocabularytrainer.ui.screens

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.LanguageLevel
import com.example.vocabularytrainer.service.db.Word
import com.example.vocabularytrainer.service.db.WordDao
import com.example.vocabularytrainer.service.db.toLanguageLevel
import com.example.vocabularytrainer.service.settings.Settings
import com.example.vocabularytrainer.ui.components.HomeNavigationBar
import com.example.vocabularytrainer.ui.components.LanguageFloatingActionButton
import com.example.vocabularytrainer.ui.content.DictionaryContent
import com.example.vocabularytrainer.ui.content.HomeContent
import com.example.vocabularytrainer.ui.content.SettingsContent
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private enum class DisplayedContent {
    Home,
    Dictionary,
    Settings
}

@Composable
fun HomeScreen(
    settings: Settings,
    wordDao: WordDao,
    lifecycleScope: LifecycleCoroutineScope,
    onFabClick: (languageLevel: LanguageLevel) -> Unit,
    modifier: Modifier
) {
    var displayedContent by rememberSaveable { mutableStateOf(DisplayedContent.Home) }
    val fabExpanded = rememberSaveable { mutableStateOf(false) }

    val overlayTransition = updateTransition(targetState = fabExpanded.value)
    val overlayOpacity by overlayTransition.animateFloat {
        if (it) 1.0F else 0.0F
    }

    val map = rememberSaveable(
        saver = Saver(
            save = { map ->
                map.keys.map { languageLevel ->
                    Triple(
                        languageLevel.title,
                        map[languageLevel]?.first ?: 0,
                        map[languageLevel]?.second ?: 0
                    )
                }.toTypedArray()
            },
            restore = { array ->
                val map = mutableStateMapOf<LanguageLevel, Pair<Int, Int>>()

                for (item in array) {
                    val languageLevel = item.first.toLanguageLevel()

                    if (languageLevel != null) {
                        map[languageLevel] = Pair(item.second, item.third)
                    }
                }

                map
            }
        )

    ) { mutableStateMapOf<LanguageLevel, Pair<Int, Int>>() }

    var wordOfTheDay: Word? by rememberSaveable(
        saver = Saver(
            save = { Json.encodeToString(it.value) },
            restore = { mutableStateOf(Json.decodeFromString(it)) })
    ) { mutableStateOf(null) }

    LaunchedEffect(true) {
        for (languageLevel in LanguageLevel.entries) {
            val completedCount = wordDao.getLearnedCount(languageLevel.title)
            val totalCount = wordDao.getTotalCount(languageLevel.title)

            map[languageLevel] = Pair(completedCount, totalCount)

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
    }

    Scaffold(
        modifier = modifier,
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
                    onClick = {
                        onFabClick(it)
                        fabExpanded.value = false
                    }
                )
            }
        }) {
        Box(
            modifier = Modifier
                .padding(bottom = it.calculateBottomPadding())
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
                            horizontal = dimensionResource(
                                R.dimen.content_padding_horizontal
                            ),
                            vertical = dimensionResource(R.dimen.content_padding_vertical)
                        ),
                        lifecycleScope = lifecycleScope,
                        settings = settings,
                        wordDao = wordDao
                    )
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