package com.example.dictionary.db

import androidx.room.*
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(dictionaryEntry: DictionaryEntity)

    @Delete
    suspend fun deleteWord(dictionaryEntry: DictionaryEntity)

    @Query("select * from dictionary_table")
    suspend fun getAllWord() : MutableList<DictionaryEntity>

    @Query("select exists (select 1 from dictionary_table where id = :wordId) ")
    suspend fun existsWord(wordId : Int) : Boolean

}