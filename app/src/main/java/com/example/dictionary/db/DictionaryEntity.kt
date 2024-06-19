package com.example.dictionary.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionary.utils.Constants
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = Constants.DICTIONARY_TABLE)
data class DictionaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val englishWord: String = "",
    val persianWord: String = ""
) : Parcelable
