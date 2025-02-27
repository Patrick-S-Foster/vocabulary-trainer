package com.example.vocabularytrainer.service.db

class StudyState private constructor() {
    companion object {
        const val NOT_ATTEMPTED = 0
        const val ATTEMPTED_FAILED = 1
        const val SOLVED_ONCE = 2
        const val SOLVED_TWICE = 3
        const val LEARNED = 4
    }
}