package com.example.vocabularytrainer.service.db

class StudyState private constructor() {
    companion object {
        const val NONE = 0
        const val SOLVED_AUDIO_TO_MULTI_WORD = 1
        const val SOLVED_DEFINITION_TO_MULTI_WORD = 2
        const val LEARNED = 3
    }
}