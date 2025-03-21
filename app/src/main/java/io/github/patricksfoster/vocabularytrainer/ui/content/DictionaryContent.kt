package io.github.patricksfoster.vocabularytrainer.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.service.db.Word
import io.github.patricksfoster.vocabularytrainer.service.db.WordDao
import io.github.patricksfoster.vocabularytrainer.ui.components.WordCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryContent(
    wordDao: WordDao,
    lifecycleScope: LifecycleCoroutineScope,
    playAudio: (audioUrl: String) -> Unit
) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val words = remember { mutableStateListOf<Word>() }

    LaunchedEffect(text) {
        lifecycleScope.launch {
            val foundWords = wordDao.searchWords(text)
            words.clear()
            words.addAll(foundWords)
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .semantics { isTraversalGroup = true }) {

        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f }
                .testTag("DictionaryContentSearchBar"),
            inputField = {
                SearchBarDefaults.InputField(
                    modifier = Modifier.testTag("DictionaryContentSearchBarInputField"),
                    query = text,
                    onQueryChange = { text = it },
                    onSearch = { },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text(stringResource(R.string.dictionary_placeholder)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null
                        )
                    },
                    trailingIcon = {
                        if (expanded) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        if (text.isNotEmpty()) {
                                            text = ""
                                            words.clear()
                                        } else {
                                            expanded = false
                                        }
                                    }
                                    .testTag("DictionaryContentSearchBarTrailingIcon")
                            )
                        }
                    }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("DictionaryContentExpandedContent"),
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(R.dimen.dictionary_items_spacing)
                ),
                contentPadding = PaddingValues(dimensionResource(R.dimen.dictionary_items_content_padding))
            ) {
                items(words) {
                    WordCard(
                        modifier = Modifier,
                        word = it,
                        displayLanguageLevel = true,
                        displayAudio = true,
                        displayDefinitions = true,
                        playAudio = playAudio,
                        selectable = false
                    )
                }
            }
        }
    }
}