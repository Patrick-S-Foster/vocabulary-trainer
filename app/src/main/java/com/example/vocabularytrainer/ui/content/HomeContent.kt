package com.example.vocabularytrainer.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.LanguageLevel
import com.example.vocabularytrainer.service.db.Word
import com.example.vocabularytrainer.ui.components.LanguageProgressIndicator
import com.example.vocabularytrainer.ui.components.TitledComposable
import com.example.vocabularytrainer.ui.components.WordCard
import java.util.EnumMap

@Composable
fun HomeContent(
    contentPadding: PaddingValues,
    wordOfTheDay: Word?,
    languageLevelProgress: Map<LanguageLevel, Pair<Int, Int>>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.content_padding_vertical))
    ) {
        item {
            TitledComposable(stringResource(R.string.word_of_the_day)) {
                WordCard(
                    word = wordOfTheDay,
                    displayLanguageLevel = true,
                    displayAudio = true,
                    displayDefinitions = true
                )
            }
        }
        item {
            TitledComposable(stringResource(R.string.progress)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.home_content_indicators_spacing))
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.home_content_indicators_spacing))
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.home_content_indicators_spacing))) {
                            LanguageProgressIndicator(
                                modifier = Modifier.size(dimensionResource(R.dimen.language_progress_indicator_size)),
                                languageLevel = LanguageLevel.A1,
                                completedCount = languageLevelProgress[LanguageLevel.A1]?.first
                                    ?: 0,
                                totalCount = languageLevelProgress[LanguageLevel.A1]?.second ?: 0
                            )
                            LanguageProgressIndicator(
                                modifier = Modifier.size(dimensionResource(R.dimen.language_progress_indicator_size)),
                                languageLevel = LanguageLevel.B1,
                                completedCount = languageLevelProgress[LanguageLevel.B1]?.first
                                    ?: 0,
                                totalCount = languageLevelProgress[LanguageLevel.B1]?.second ?: 0
                            )
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.home_content_indicators_spacing))) {
                            LanguageProgressIndicator(
                                modifier = Modifier.size(dimensionResource(R.dimen.language_progress_indicator_size)),
                                languageLevel = LanguageLevel.A2,
                                completedCount = languageLevelProgress[LanguageLevel.A2]?.first
                                    ?: 0,
                                totalCount = languageLevelProgress[LanguageLevel.A2]?.second ?: 0
                            )
                            LanguageProgressIndicator(
                                modifier = Modifier.size(dimensionResource(R.dimen.language_progress_indicator_size)),
                                languageLevel = LanguageLevel.B2,
                                completedCount = languageLevelProgress[LanguageLevel.B2]?.first
                                    ?: 0,
                                totalCount = languageLevelProgress[LanguageLevel.B2]?.second ?: 0
                            )
                        }
                    }
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        LanguageProgressIndicator(
                            modifier = Modifier.size(dimensionResource(R.dimen.language_progress_indicator_size)),
                            languageLevel = LanguageLevel.C1,
                            completedCount = languageLevelProgress[LanguageLevel.C1]?.first ?: 0,
                            totalCount = languageLevelProgress[LanguageLevel.C1]?.second ?: 0
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    HomeContent(PaddingValues.Absolute(), null, EnumMap(LanguageLevel::class.java))
}