package io.github.patricksfoster.vocabularytrainer.ui.content

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.service.db.Word
import io.github.patricksfoster.vocabularytrainer.ui.components.WordCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefinitionToWordContent(
    contentPadding: PaddingValues,
    playAudio: (audioUri: String) -> Unit,
    word: Word,
    onSuccess: () -> Unit,
    onFailure: () -> Unit,
    onContinue: () -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    var submitted by rememberSaveable { mutableStateOf(false) }
    var success by rememberSaveable { mutableStateOf(false) }

    fun submit() {
        if (submitted || text.isEmpty()) {
            return
        }

        submitted = true
        success = text.trim().uppercase() == word.word.trim().uppercase()

        if (success) {
            onSuccess()
        } else {
            onFailure()
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box(modifier = Modifier.fillMaxSize()) {
                AnimatedVisibility(
                    visible = !submitted,
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.learning_content_spacing))
                    ) {
                        SearchBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .semantics { traversalIndex = 0f },
                            inputField = {
                                SearchBarDefaults.InputField(
                                    query = text,
                                    onQueryChange = { text = it },
                                    onSearch = { submit() },
                                    expanded = false,
                                    onExpandedChange = { },
                                    placeholder = { Text(stringResource(R.string.learning_content_search_placeholder)) },
                                    trailingIcon = {
                                        if (!submitted) {
                                            Icon(
                                                imageVector = Icons.AutoMirrored.Outlined.Send,
                                                contentDescription = null,
                                                modifier = Modifier.clickable { submit() }
                                            )
                                        }
                                    }
                                )
                            },
                            expanded = false,
                            onExpandedChange = { },
                            windowInsets = WindowInsets(top = 0)
                        ) {}

                        HorizontalDivider(
                            modifier = Modifier
                                .padding(horizontal = dimensionResource(R.dimen.divider_horizontal_padding))
                                .fillMaxWidth(),
                            thickness = dimensionResource(R.dimen.divider_thickness)
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            border = BorderStroke(
                                dimensionResource(R.dimen.card_border_width),
                                MaterialTheme.colorScheme.outline
                            )
                        ) {
                            val definitions =
                                listOfNotNull(
                                    word.definitionOne,
                                    word.definitionTwo,
                                    word.definitionThree
                                ).withIndex()

                            Column(
                                modifier = Modifier.padding(
                                    horizontal = dimensionResource(R.dimen.card_horizontal_padding),
                                    vertical = dimensionResource(R.dimen.card_vertical_padding)
                                ),
                                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.information_dialog_content_spacing))
                            ) {
                                definitions.forEach {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(
                                            dimensionResource(R.dimen.information_dialog_content_spacing)
                                        )
                                    ) {
                                        val displayIndex = it.index + 1

                                        Text(
                                            modifier = Modifier.wrapContentWidth(),
                                            text = "$displayIndex.",
                                            style = MaterialTheme.typography.titleLarge
                                        )

                                        Text(
                                            text = it.value,
                                            style = MaterialTheme.typography.titleLarge
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                AnimatedVisibility(
                    visible = submitted,
                    enter = fadeIn()
                ) {
                    WordCard(
                        word = word,
                        displayLanguageLevel = false,
                        displayAudio = true,
                        displayDefinitions = true,
                        playAudio = playAudio,
                        selectable = true,
                        selected = true,
                        selectedColor = if (success) {
                            colorResource(R.color.success)
                        } else {
                            MaterialTheme.colorScheme.error
                        }
                    )
                }
            }
        }

        item {
            AnimatedVisibility(visible = submitted) {
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.learning_content_spacing)))
            }
        }

        item {
            AnimatedVisibility(
                visible = submitted,
                enter = fadeIn()
            ) {
                Button(
                    onClick = onContinue,
                ) {
                    Text(text = stringResource(R.string.learning_content_button_continue))
                }
            }
        }
    }
}