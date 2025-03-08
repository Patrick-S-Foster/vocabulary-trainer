package com.example.vocabularytrainer.service.db

class StudyStateFlags private constructor() {
    companion object {
        const val NONE = 0
        const val ATTEMPTED = 1
        const val SOLVED_DEFINITION_TO_MULTI_WORD = 2
        const val SOLVED_AUDIO_TO_MULTI_WORD = 4
        const val SOLVED_DEFINITION_TO_WORD = 8
        const val LEARNED = 16
    }
}