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
import com.example.vocabularytrainer.ui.components.LanguageProgressIndicator
import com.example.vocabularytrainer.ui.components.TitledComposable
import com.example.vocabularytrainer.ui.components.WordCard

@Composable
fun HomeContent(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.content_padding_vertical))
    ) {
        TitledComposable(stringResource(R.string.word_of_the_day)) {
            WordCard(LanguageLevel.A1, "word", "phonetic", "null")
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
                        10,
                        100
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
                            10,
                            100
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
    HomeContent(Modifier)
}