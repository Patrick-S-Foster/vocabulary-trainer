package io.github.patricksfoster.vocabularytrainer.service.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Word(
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "word") val word: String,
    @ColumnInfo(name = "phonetic_text") val phoneticText: String,
    @ColumnInfo(name = "phonetic_audio_url") val phoneticAudioUrl: String?,
    @ColumnInfo(name = "phonetic_audio_source_url") val phoneticAudioSourceUrl: String?,
    @ColumnInfo(name = "phonetic_audio_license_name") val phoneticAudioLicenseName: String?,
    @ColumnInfo(name = "phonetic_audio_license_url") val phoneticAudioLicenseUrl: String?,
    @ColumnInfo(name = "definition_one") val definitionOne: String,
    @ColumnInfo(name = "definition_two") val definitionTwo: String?,
    @ColumnInfo(name = "definition_three") val definitionThree: String?,
    @ColumnInfo(name = "definition_word_source_url") val definitionWordSourceUrl: String?,
    @ColumnInfo(name = "definition_word_license_name") val definitionWordLicenseName: String?,
    @ColumnInfo(name = "definition_word_license_url") val definitionWordLicenseUrl: String?,
    @ColumnInfo(name = "study_state") var studyState: Int,
    @ColumnInfo(name = "word_of_the_day_date") var wordOfTheDayDate: String?,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)