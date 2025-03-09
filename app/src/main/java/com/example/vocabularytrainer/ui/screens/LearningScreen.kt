package com.example.vocabularytrainer.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.LanguageLevel
import com.example.vocabularytrainer.service.db.StudyState
import com.example.vocabularytrainer.service.db.Word
import com.example.vocabularytrainer.service.db.WordDao
import com.example.vocabularytrainer.service.settings.Settings
import com.example.vocabularytrainer.ui.content.AudioToMultiWordContent
import com.example.vocabularytrainer.ui.content.DefinitionToMultiWordContent
import com.example.vocabularytrainer.ui.content.DefinitionToWordContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@Composable
fun LearningScreen(
    languageLevel: LanguageLevel,
    contentPadding: PaddingValues,
    wordDao: WordDao,
    settings: Settings,
    playAudio: (audioUri: String) -> Unit,
    lifecycleScope: LifecycleCoroutineScope
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
    var contentType: Int? by rememberSaveable { mutableStateOf(null) }
    var contentVisible by rememberSaveable { mutableStateOf(true) }

    suspend fun getWordNotInIds(ids: List<Int>): Word {
        var candidateWord: Word

        do {
            candidateWord = wordDao.getRandomWord()
        } while (ids.contains(candidateWord.id))

        return candidateWord
    }

    suspend fun regenerate(onlyIfNull: Boolean) {
        if (!onlyIfNull && contentType == null) {
            return
        }

        val studyStates = mutableListOf(
            StudyState.NONE,
            StudyState.SOLVED_AUDIO_TO_MULTI_WORD,
            StudyState.SOLVED_DEFINITION_TO_MULTI_WORD
        )
        var studyState = studyStates.random()
        var wordGenerated = false

        while (studyStates.any()) {
            val candidateStudyState = studyStates.random()
            val candidateWord = wordDao.getRandomWord(languageLevel.title, candidateStudyState)

            if (candidateWord == null) {
                studyStates.remove(candidateStudyState)
                continue
            }

            if (candidateStudyState == StudyState.NONE && candidateWord.phoneticAudioUrl == null) {
                candidateWord.studyState = StudyState.SOLVED_AUDIO_TO_MULTI_WORD
                wordDao.updateAll(candidateWord)
                continue
            }

            studyState = candidateStudyState
            correctWord = candidateWord
            wordGenerated = true
            break
        }

        if (!wordGenerated) {
            correctWord = wordDao.getRandomWord()
        }

        val ids = mutableListOf(correctWord!!.id!!)

        firstIncorrectWord = getWordNotInIds(ids)
        ids.add(firstIncorrectWord!!.id!!)

        secondIncorrectWord = getWordNotInIds(ids)
        ids.add(secondIncorrectWord!!.id!!)

        thirdIncorrectWord = getWordNotInIds(ids)
        ids.add(thirdIncorrectWord!!.id!!)

        contentType = studyState
    }

    fun onSuccess() {
        lifecycleScope.launch {
            correctWord!!.studyState = when (correctWord!!.studyState) {
                StudyState.NONE -> StudyState.SOLVED_AUDIO_TO_MULTI_WORD
                StudyState.SOLVED_AUDIO_TO_MULTI_WORD -> StudyState.SOLVED_DEFINITION_TO_MULTI_WORD
                StudyState.SOLVED_DEFINITION_TO_MULTI_WORD -> StudyState.LEARNED
                else -> StudyState.NONE
            }

            wordDao.updateAll(correctWord!!)
        }
    }

    fun onContinue() {
        lifecycleScope.launch {
            contentVisible = false
            delay(1000)
            regenerate(false)
            contentVisible = true
        }
    }

    LaunchedEffect(true) {
        regenerate(true)
    }

    if (correctWord != null &&
        firstIncorrectWord != null &&
        secondIncorrectWord != null &&
        thirdIncorrectWord != null
    ) {
        AnimatedVisibility(
            visible = contentVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier.padding(dimensionResource(R.dimen.learning_content_padding))) {
                when (contentType) {
                    StudyState.NONE -> {
                        AudioToMultiWordContent(
                            contentPadding = contentPadding,
                            settings = settings,
                            playAudio = playAudio,
                            correctWord = correctWord!!,
                            firstIncorrectWord = firstIncorrectWord!!,
                            secondIncorrectWord = secondIncorrectWord!!,
                            thirdIncorrectWord = thirdIncorrectWord!!,
                            onSuccess = ::onSuccess,
                            onContinue = ::onContinue
                        )
                    }

                    StudyState.SOLVED_AUDIO_TO_MULTI_WORD -> {
                        DefinitionToMultiWordContent(
                            contentPadding = contentPadding,
                            settings = settings,
                            playAudio = playAudio,
                            correctWord = correctWord!!,
                            firstIncorrectWord = firstIncorrectWord!!,
                            secondIncorrectWord = secondIncorrectWord!!,
                            thirdIncorrectWord = thirdIncorrectWord!!,
                            onSuccess = ::onSuccess,
                            onContinue = ::onContinue
                        )
                    }

                    StudyState.SOLVED_DEFINITION_TO_MULTI_WORD -> {
                        DefinitionToWordContent(
                            contentPadding = contentPadding,
                            settings = settings,
                            playAudio = playAudio,
                            word = correctWord!!,
                            onSuccess = ::onSuccess,
                            onContinue = ::onContinue
                        )
                    }
                }
            }
        }
    }
}