package com.example.vocabularytrainer.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.Word
import com.example.vocabularytrainer.service.db.WordDao
import com.example.vocabularytrainer.service.settings.Settings
import com.example.vocabularytrainer.ui.content.DefinitionToMultiWordContent
import kotlinx.serialization.json.Json

@Composable
fun LearningScreen(
    contentPadding: PaddingValues,
    wordDao: WordDao,
    settings: Settings,
    playAudio: (audioUri: String) -> Unit
) {
    var correctWord: Word? by rememberSaveable(
        saver = Saver(
            save = { Json.encodeToString(it.value) },
            restore = { mutableStateOf(Json.decodeFromString(it)) }
        )
    ) { mutableStateOf(null) }
    var firstIncorrectWord: Word? by rememberSaveable(
        saver = Saver(
            save = { Json.encodeToString(it.value) },
            restore = { mutableStateOf(Json.decodeFromString(it)) }
        )
    ) { mutableStateOf(null) }
    var secondIncorrectWord: Word? by rememberSaveable(
        saver = Saver(
            save = { Json.encodeToString(it.value) },
            restore = { mutableStateOf(Json.decodeFromString(it)) }
        )
    ) { mutableStateOf(null) }
    var thirdIncorrectWord: Word? by rememberSaveable(
        saver = Saver(
            save = { Json.encodeToString(it.value) },
            restore = { mutableStateOf(Json.decodeFromString(it)) }
        )
    ) { mutableStateOf(null) }

    LaunchedEffect(true) {
        if (correctWord == null) {
            correctWord = wordDao.getRandomWords().first()
        }
        if (firstIncorrectWord == null) {
            firstIncorrectWord = wordDao.getRandomWords().first()
        }
        if (secondIncorrectWord == null) {
            secondIncorrectWord = wordDao.getRandomWords().first()
        }
        if (thirdIncorrectWord == null) {
            thirdIncorrectWord = wordDao.getRandomWords().first()
        }
    }

    if (correctWord != null &&
        firstIncorrectWord != null &&
        secondIncorrectWord != null &&
        thirdIncorrectWord != null
    ) {
        Box(modifier = Modifier.padding(dimensionResource(R.dimen.learning_content_padding))) {
            DefinitionToMultiWordContent(
                contentPadding,
                settings,
                playAudio,
                correctWord!!,
                firstIncorrectWord!!,
                secondIncorrectWord!!,
                thirdIncorrectWord!!,
                {},
                {},
                {})
        }
    }
}