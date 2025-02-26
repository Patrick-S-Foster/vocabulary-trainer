package com.example.vocabularytrainer.service.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    companion object {
        const val NAME = "word_db"
    }

    abstract fun wordDao(): WordDao
}