package io.github.patricksfoster.vocabularytrainer.service.db

enum class LanguageLevel(val title: String) {
    A1("A1"), A2("A2"), B1("B1"), B2("B2"), C1("C1")
}

fun String.toLanguageLevel(): LanguageLevel? =
    LanguageLevel.entries.firstOrNull { it.title == this }