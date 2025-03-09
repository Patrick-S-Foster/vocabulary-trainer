package com.example.vocabularytrainer.ui.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.Density
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.Word

private object DefinitionGridCells : GridCells {
    override fun Density.calculateCrossAxisCellSizes(availableSize: Int, spacing: Int): List<Int> {
        return listOf(
            ((availableSize - spacing) * 0.25).toInt(),
            ((availableSize - spacing) * 0.75).toInt()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordDialog(
    word: Word,
    displayDefinitions: Boolean,
    displayAudio: Boolean,
    onDismissRequest: () -> Unit
) {
    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(
                dimensionResource(R.dimen.card_border_width),
                MaterialTheme.colorScheme.outline
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.word_card_horizontal_padding),
                        vertical = dimensionResource(R.dimen.word_card_vertical_padding)
                    ),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.information_dialog_content_spacing))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = word.word,
                        style = MaterialTheme.typography.titleLarge
                    )

                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.dialog_button_close_content_description)
                        )
                    }
                }

                if (displayDefinitions) {
                    val definitions =
                        listOfNotNull(
                            word.definitionOne,
                            word.definitionTwo,
                            word.definitionThree
                        ).withIndex()

                    LazyVerticalGrid(
                        columns = DefinitionGridCells,
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.information_dialog_content_spacing)),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        definitions.forEach { (index, definition) ->
                            item {
                                val displayIndex = index + 1
                                Text(
                                    modifier = Modifier.wrapContentWidth(),
                                    text = "$displayIndex.",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            item {
                                Text(
                                    text = definition,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    }
                }

                if (displayDefinitions &&
                    word.definitionWordSourceUrl != null &&
                    word.definitionWordLicenseUrl != null &&
                    word.definitionWordLicenseName != null
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.word_dialog_definitions_part_1))
                            append(' ')
                            withLink(
                                LinkAnnotation.Url(
                                    word.definitionWordSourceUrl,
                                    TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.primary))
                                )
                            ) {
                                append(stringResource(R.string.word_dialog_part_2))
                            }
                            append(' ')
                            append(stringResource(R.string.word_dialog_part_3))
                            append(' ')
                            withLink(
                                LinkAnnotation.Url(
                                    word.definitionWordLicenseUrl,
                                    TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.primary))
                                )
                            ) {
                                append(word.definitionWordLicenseName)
                            }
                            append('.')
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                if (displayAudio &&
                    word.phoneticAudioSourceUrl != null &&
                    word.phoneticAudioLicenseUrl != null &&
                    word.phoneticAudioLicenseName != null
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.word_dialog_audio_part_1))
                            append(' ')
                            withLink(
                                LinkAnnotation.Url(
                                    word.phoneticAudioSourceUrl,
                                    TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.primary))
                                )
                            ) {
                                append(stringResource(R.string.word_dialog_part_2))
                            }
                            append(' ')
                            append(stringResource(R.string.word_dialog_part_3))
                            append(' ')
                            withLink(
                                LinkAnnotation.Url(
                                    word.phoneticAudioLicenseUrl,
                                    TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.primary))
                                )
                            ) {
                                append(word.phoneticAudioLicenseName)
                            }
                            append('.')
                        },
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}