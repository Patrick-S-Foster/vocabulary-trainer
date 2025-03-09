package io.github.patricksfoster.vocabularytrainer.service.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    companion object {
        const val NAME = "words.db"
        const val ASSET_NAME = "initial_words.db"
    }

    abstract fun wordDao(): WordDao
}