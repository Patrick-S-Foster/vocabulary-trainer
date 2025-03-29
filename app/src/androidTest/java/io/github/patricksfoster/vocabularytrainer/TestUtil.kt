package io.github.patricksfoster.vocabularytrainer

import io.github.patricksfoster.vocabularytrainer.service.db.LanguageLevel
import io.github.patricksfoster.vocabularytrainer.service.db.StudyState
import io.github.patricksfoster.vocabularytrainer.service.db.Word

class TestUtil private constructor() {
    companion object {
        fun createWord(
            category: String = LanguageLevel.A1.title,
            word: String = "word",
            lexicalCategory: String = "lexical_category",
            phoneticText: String = "/wɜːd/",
            phoneticAudioUrl: String? = null,
            phoneticAudioSourceUrl: String? = null,
            phoneticAudioLicenseName: String? = null,
            phoneticAudioLicenseUrl: String? = null,
            definitionOne: String = "This is a definition of the word 'word'.",
            definitionTwo: String? = null,
            definitionThree: String? = null,
            definitionWordSourceUrl: String = "",
            definitionWordLicenseName: String = "",
            definitionWordLicenseUrl: String = "",
            studyState: Int = StudyState.NONE,
            wordOfTheDayDate: String? = null,
            id: Int? = null
        ): Word = Word(
            category = category,
            word = word,
            lexicalCategory = lexicalCategory,
            phoneticText = phoneticText,
            phoneticAudioUrl = phoneticAudioUrl,
            phoneticAudioSourceUrl = phoneticAudioSourceUrl,
            phoneticAudioLicenseName = phoneticAudioLicenseName,
            phoneticAudioLicenseUrl = phoneticAudioLicenseUrl,
            definitionOne = definitionOne,
            definitionTwo = definitionTwo,
            definitionThree = definitionThree,
            definitionWordSourceUrl = definitionWordSourceUrl,
            definitionWordLicenseName = definitionWordLicenseName,
            definitionWordLicenseUrl = definitionWordLicenseUrl,
            studyState = studyState,
            wordOfTheDayDate = wordOfTheDayDate,
            id = id
        )
    }
}