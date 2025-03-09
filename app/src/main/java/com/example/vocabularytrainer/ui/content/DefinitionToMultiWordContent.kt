package com.example.vocabularytrainer.ui.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.Word
import com.example.vocabularytrainer.ui.components.WordCard

@Composable
fun DefinitionToMultiWordContent(
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        dimensionResource(R.dimen.card_border_width),
                        MaterialTheme.colorScheme.outline
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.card_horizontal_padding),
                            vertical = dimensionResource(R.dimen.card_vertical_padding)
                        ),
                        text = correctWord.definitionOne,
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.divider_horizontal_padding))
                        .fillMaxWidth(),
                    thickness = dimensionResource(R.dimen.divider_thickness)
                )

                ordering.withIndex().forEach {
                    WordCard(
                        word = when (it.value) {
                            0 -> correctWord
                            1 -> firstIncorrectWord
                            2 -> secondIncorrectWord
                            3 -> thirdIncorrectWord
                            else -> throw IllegalStateException()
                        },
                        displayLanguageLevel = false,
                        displayAudio = true,
                        displayDefinitions = !canSelect,
                        playAudio = playAudio,
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