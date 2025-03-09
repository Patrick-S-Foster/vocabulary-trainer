package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.core.graphics.ColorUtils
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.service.db.Word
import io.github.patricksfoster.vocabularytrainer.service.db.toLanguageLevel
import io.github.patricksfoster.vocabularytrainer.ui.dialogs.WordDialog

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WordCard(
    word: Word?,
    displayLanguageLevel: Boolean,
    displayAudio: Boolean,
    displayDefinitions: Boolean,
    playAudio: (audioUrl: String) -> Unit,
    selectable: Boolean,
    selected: Boolean = false,
    selectedColor: Color = Color.Unspecified,
    onSelected: () -> Unit = {},
) {
    var dialogOpen by rememberSaveable { mutableStateOf(false) }
    val borderWidth by animateDpAsState(
        if (selected) {
            dimensionResource(R.dimen.card_border_width_selected)
        } else {
            dimensionResource(R.dimen.card_border_width)
        }
    )
    val borderColor by animateColorAsState(
        if (selected) {
            selectedColor
        } else {
            MaterialTheme.colorScheme.outline
        }
    )
    val containerColor by animateColorAsState(
        if (selected) {
            Color(
                ColorUtils.blendARGB(
                    selectedColor.toArgb(),
                    CardDefaults.cardColors().containerColor.toArgb(),
                    0.85F
                )
            )
        } else {
            CardDefaults.cardColors().containerColor
        }
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onDoubleClick = {
                    dialogOpen = canOpenWordDialog(word, displayDefinitions, displayAudio)
                },
                onLongClick = {
                    dialogOpen = canOpenWordDialog(word, displayDefinitions, displayAudio)
                }
            ) {
                if (selectable) {
                    onSelected()
                }
            },
        border = BorderStroke(borderWidth, borderColor),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(R.dimen.card_horizontal_padding),
                    vertical = dimensionResource(R.dimen.card_vertical_padding)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.card_horizontal_padding)),
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
                    FilledIconButton(onClick = { playAudio(it) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = stringResource(R.string.pronunciation_content_description)
                        )
                    }
                }
            }
        }
    }

    if (dialogOpen && canOpenWordDialog(word, displayDefinitions, displayAudio)) {
        WordDialog(word!!, displayDefinitions, displayAudio) {
            dialogOpen = false
        }
    }
}

private fun canOpenWordDialog(
    word: Word?,
    displayDefinitions: Boolean,
    displayAudio: Boolean
): Boolean =
    word != null &&
            (displayDefinitions ||
                    displayAudio &&
                    word.phoneticAudioSourceUrl != null &&
                    word.phoneticAudioLicenseUrl != null &&
                    word.phoneticAudioLicenseName != null)