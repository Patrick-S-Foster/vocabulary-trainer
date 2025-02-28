package com.example.vocabularytrainer.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
    modifier: Modifier,
    wordOfTheDay: Word?,
    languageLevelProgress: Map<LanguageLevel, Pair<Int, Int>>
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.content_padding_vertical))
    ) {
        TitledComposable(stringResource(R.string.word_of_the_day)) {
            WordCard(
                languageLevel = if (wordOfTheDay != null) LanguageLevel.valueOf(wordOfTheDay.category) else null,
                word = wordOfTheDay?.word ?: "",
                phonetic = wordOfTheDay?.phoneticText ?: "",
                pronunciationUrl = wordOfTheDay?.phoneticAudioUrl
            )
        }

        TitledComposable(stringResource(R.string.progress)) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.home_content_indicators_spacing))
            ) {
                items(
                    LanguageLevel.entries.take(LanguageLevel.entries.size - 1).toList()
                ) { languageLevel ->
                    LanguageProgressIndicator(
                        Modifier.size(dimensionResource(R.dimen.language_progress_indicator_size)),
                        languageLevel,
                        languageLevelProgress[languageLevel]?.first ?: 0,
                        languageLevelProgress[languageLevel]?.second ?: 0
                    )
                }
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.home_content_indicators_spacing))
                    ) {
                        LanguageProgressIndicator(
                            Modifier
                                .size(dimensionResource(R.dimen.language_progress_indicator_size))
                                .align(Alignment.Center),
                            LanguageLevel.entries.last(),
                            languageLevelProgress[LanguageLevel.entries.last()]?.first ?: 0,
                            languageLevelProgress[LanguageLevel.entries.last()]?.second ?: 0
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
    HomeContent(Modifier, null, EnumMap(LanguageLevel::class.java))
}