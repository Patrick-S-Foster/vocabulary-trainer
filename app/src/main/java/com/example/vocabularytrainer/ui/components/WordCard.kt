package com.example.vocabularytrainer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.LanguageLevel

// TODO: make this selectable in a radio group
@Composable
fun WordCard(
    languageLevel: LanguageLevel?,
    word: String,
    phonetic: String,
    pronunciationUrl: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(
            dimensionResource(R.dimen.card_border_width),
            MaterialTheme.colorScheme.outline
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.word_card_horizontal_padding),
                    vertical = dimensionResource(R.dimen.word_card_vertical_padding)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.word_card_horizontal_padding)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                languageLevel?.title?.let {
                    val circleColor = MaterialTheme.colorScheme.primary
                    Text(
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.word_card_language_level_padding))
                            .drawBehind {
                                drawCircle(
                                    color = circleColor,
                                    radius = this.size.maxDimension
                                )
                            },
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.word_card_word_phonetic_spacing))) {
                    Text(text = word, style = MaterialTheme.typography.titleMedium)
                    Text(text = phonetic, style = MaterialTheme.typography.bodySmall)
                }
            }
            pronunciationUrl?.let {
                FilledIconButton(onClick = {
                    // TODO: playback audio when clicked
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = stringResource(R.string.pronunciation_content_description)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun WordCardPreview() {
    Box(modifier = Modifier.padding(5.dp)) {
        WordCard(LanguageLevel.A1, "action", "/ˈæk.ʃən/", "")
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun WordCardNoLanguagePreview() {
    Box(modifier = Modifier.padding(5.dp)) {
        WordCard(null, "action", "/ˈæk.ʃən/", "")
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun WordCardNoPronunciationPreview() {
    Box(modifier = Modifier.padding(5.dp)) {
        WordCard(LanguageLevel.A1, "action", "/ˈæk.ʃən/", null)
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun WordCardNothingPreview() {
    Box(modifier = Modifier.padding(5.dp)) {
        WordCard(null, "action", "/ˈæk.ʃən/", null)
    }
}