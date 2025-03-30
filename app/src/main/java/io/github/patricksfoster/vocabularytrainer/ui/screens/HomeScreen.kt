package io.github.patricksfoster.vocabularytrainer.ui.screens

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.service.db.LanguageLevel
import io.github.patricksfoster.vocabularytrainer.service.db.Word
import io.github.patricksfoster.vocabularytrainer.service.db.WordDao
import io.github.patricksfoster.vocabularytrainer.service.db.toLanguageLevel
import io.github.patricksfoster.vocabularytrainer.service.settings.Settings
import io.github.patricksfoster.vocabularytrainer.ui.components.HomeNavigationBar
import io.github.patricksfoster.vocabularytrainer.ui.components.LanguageFloatingActionButton
import io.github.patricksfoster.vocabularytrainer.ui.content.DictionaryContent
import io.github.patricksfoster.vocabularytrainer.ui.content.HomeContent
import io.github.patricksfoster.vocabularytrainer.ui.content.SettingsContent
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.time.LocalDate
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
    modifier: Modifier,
    playAudio: (audioUrl: String) -> Unit,
    context: Context
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

    /**
     * This function handles counting the number of learned words per category, and the word of the day.
     */
    suspend fun populateMap() {
        for (languageLevel in LanguageLevel.entries) {
            val completedCount = wordDao.getLearnedCount(languageLevel.title)
            val totalCount = wordDao.getTotalCount(languageLevel.title)

            map[languageLevel] = Pair(completedCount, totalCount)

            wordOfTheDay = wordDao.getWordOfTheDay(
                LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
            )

            if (wordOfTheDay == null) {
                wordOfTheDay = wordDao.getRandomWord()

                wordOfTheDay!!.wordOfTheDayDate =
                    LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
                wordDao.updateAll(wordOfTheDay!!)
            }
        }
    }

    LaunchedEffect(true) {
        populateMap()
    }

    Scaffold(
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
            modifier = modifier
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
                        wordOfTheDay = wordOfTheDay,
                        playAudio = playAudio
                    )
                }

                DisplayedContent.Dictionary -> {
                    DictionaryContent(
                        wordDao = wordDao,
                        lifecycleScope = lifecycleScope,
                        playAudio = playAudio
                    )
                }

                else -> {
                    val toastText = stringResource(R.string.toast_reset)

                    SettingsContent(
                        contentPadding = PaddingValues(
                            horizontal = dimensionResource(
                                R.dimen.content_padding_horizontal
                            ),
                            vertical = dimensionResource(R.dimen.content_padding_vertical)
                        ),
                        lifecycleScope = lifecycleScope,
                        settings = settings,
                        wordDao = wordDao,
                        progressReset = {
                            lifecycleScope.launch {
                                populateMap()
                            }

                            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show()
                        }
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