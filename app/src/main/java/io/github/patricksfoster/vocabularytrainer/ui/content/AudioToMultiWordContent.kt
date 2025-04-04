package io.github.patricksfoster.vocabularytrainer.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.service.db.Word
import io.github.patricksfoster.vocabularytrainer.ui.components.WordCard

@Composable
fun AudioToMultiWordContent(
    contentPadding: PaddingValues,
    playAudio: (audioUri: String) -> Unit,
    correctWord: Word,
    firstIncorrectWord: Word,
    secondIncorrectWord: Word,
    thirdIncorrectWord: Word,
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
    onContinue: () -> Unit
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(-1) }
    var successIndex by rememberSaveable { mutableIntStateOf(-1) }
    var failureIndex by rememberSaveable { mutableIntStateOf(-1) }
    val ordering = rememberSaveable { mutableListOf(0, 1, 2, 3).shuffled() }
    var canSelect by rememberSaveable { mutableStateOf(true) }
    var submit by rememberSaveable { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.learning_content_spacing))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FilledIconButton(
                        onClick = { playAudio(correctWord.phoneticAudioUrl!!) },
                        shape = RoundedCornerShape(dimensionResource(R.dimen.learning_audio_button_corner_size)),
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.learning_audio_button_size))
                            .testTag("AudioToMultiWordContentAudioButton")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = stringResource(R.string.pronunciation_content_description),
                            modifier = Modifier
                                .size(dimensionResource(R.dimen.learning_audio_button_icon_size))
                                .testTag("AudioToMultiWordContentAudioButtonIcon")
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.divider_horizontal_padding))
                        .fillMaxWidth()
                        .testTag("AudioToMultiWordContentDivider"),
                    thickness = dimensionResource(R.dimen.divider_thickness)
                )

                ordering.withIndex().forEach {
                    WordCard(
                        modifier = Modifier.testTag("AudioToMultiWordContentWordCard#${it.value}"),
                        word = when (it.value) {
                            0 -> correctWord
                            1 -> firstIncorrectWord
                            2 -> secondIncorrectWord
                            3 -> thirdIncorrectWord
                            else -> throw IllegalStateException()
                        },
                        displayLanguageLevel = false,
                        displayAudio = !canSelect,
                        displayDefinitions = true,
                        playAudio = { audioUri ->
                            if (!canSelect) {
                                playAudio(audioUri)
                            }
                        },
                        selectable = true,
                        selected = selectedIndex == it.index || successIndex == it.index || failureIndex == it.index,
                        selectedColor = if (successIndex == it.index) {
                            colorResource(R.color.success)
                        } else if (failureIndex == it.index) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        onSelected = {
                            if (canSelect) {
                                selectedIndex = it.index
                            }
                        }
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.learning_content_spacing)))
        }

        item {
            Button(
                modifier = Modifier.testTag("AudioToMultiWordContentSubmitButton"),
                onClick = {
                    if (submit) {
                        if (ordering[selectedIndex] == 0) {
                            successIndex = selectedIndex
                            onSuccess()
                        } else {
                            failureIndex = selectedIndex
                            successIndex = (ordering.withIndex().first { it.value == 0 }).index
                            onFailure()
                        }

                        canSelect = false
                        submit = false
                    } else {
                        onContinue()
                    }
                },
                enabled = selectedIndex != -1
            ) {
                Text(
                    modifier = Modifier.testTag("AudioToMultiWordContentSubmitButtonText"),
                    text =
                    if (submit) {
                        stringResource(R.string.learning_content_button_submit)
                    } else {
                        stringResource(R.string.learning_content_button_continue)
                    }
                )
            }
        }
    }
}