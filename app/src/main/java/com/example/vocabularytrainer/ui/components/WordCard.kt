package com.example.vocabularytrainer.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.LanguageLevel
import com.example.vocabularytrainer.service.db.Word
import com.example.vocabularytrainer.service.db.toLanguageLevel
import com.example.vocabularytrainer.ui.dialogs.WordDialog

// TODO: make this selectable in a radio group
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordCard(
    word: Word?,
    displayLanguageLevel: Boolean,
    displayAudio: Boolean,
    displayDefinitions: Boolean
) {
    var dialogOpen by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onDoubleClick = { dialogOpen = true },
                onLongClick = { dialogOpen = true }
            ) {
            },
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
                if (displayLanguageLevel) {
                    word?.category?.toLanguageLevel()?.title?.let {
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
                }
                Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.word_card_word_phonetic_spacing))) {
                    Text(text = word?.word ?: "", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = word?.phoneticText ?: "",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            if (displayAudio) {
                word?.phoneticAudioUrl?.let {
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

    if (dialogOpen && word != null) {
        WordDialog(word, displayDefinitions) {
            dialogOpen = false
        }
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun WordCardPreview() {
    Box(modifier = Modifier.padding(5.dp)) {
        WordCard(
            Word(
                LanguageLevel.A1.title,
                "action",
                "/ˈæk.ʃən/",
                null,
                null,
                null,
                null,
                "Definition of the word 'action'.",
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                0
            ),
            displayLanguageLevel = true,
            displayAudio = true,
            displayDefinitions = true
        )
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun WordCardNoLanguagePreview() {
    Box(modifier = Modifier.padding(5.dp)) {
        WordCard(
            Word(
                LanguageLevel.A1.title,
                "action",
                "/ˈæk.ʃən/",
                null,
                null,
                null,
                null,
                "Definition of the word 'action'.",
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                0
            ),
            displayLanguageLevel = false,
            displayAudio = true,
            displayDefinitions = true
        )
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun WordCardNoPronunciationPreview() {
    Box(modifier = Modifier.padding(5.dp)) {
        WordCard(
            Word(
                LanguageLevel.A1.title,
                "action",
                "/ˈæk.ʃən/",
                null,
                null,
                null,
                null,
                "Definition of the word 'action'.",
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                0
            ),
            displayLanguageLevel = true,
            displayAudio = false,
            displayDefinitions = true
        )
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun WordCardNoDefinitionPreview() {
    Box(modifier = Modifier.padding(5.dp)) {
        WordCard(
            Word(
                LanguageLevel.A1.title,
                "action",
                "/ˈæk.ʃən/",
                null,
                null,
                null,
                null,
                "Definition of the word 'action'.",
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                0
            ),
            displayLanguageLevel = true,
            displayAudio = true,
            displayDefinitions = false
        )
    }
}

@Preview(showBackground = true, widthDp = 500)
@Composable
fun WordCardNothingPreview() {
    Box(modifier = Modifier.padding(5.dp)) {
        WordCard(
            Word(
                LanguageLevel.A1.title,
                "action",
                "/ˈæk.ʃən/",
                null,
                null,
                null,
                null,
                "Definition of the word 'action'.",
                null,
                null,
                null,
                null,
                null,
                0,
                null,
                0
            ),
            displayLanguageLevel = false,
            displayAudio = false,
            displayDefinitions = false
        )
    }
}