package com.example.dictionary.db

import androidx.room.*
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DictionaryDao {

    @Query("""
        SELECT * FROM dictionary_table 
        WHERE id IN (
            SELECT MIN(id) 
            FROM dictionary_table 
            WHERE englishWord LIKE :query || '%' 
            GROUP BY englishWord
        )
    """)
    suspend fun searchDictionary(query: String): List<DictionaryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWords(data : List<DictionaryEntity>)

    @Update
    suspend fun updateWord(dictionaryEntry: DictionaryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(dictionaryEntry: DictionaryEntity)

    @Delete
    suspend fun deleteWord(dictionaryEntry: DictionaryEntity)

    @Query("select * from dictionary_table where isFavorite = 1")
    suspend fun getAllFavoriteWord() : MutableList<DictionaryEntity>

    @Query("select * from dictionary_table")
    suspend fun getAllWord() : MutableList<DictionaryEntity>

    @Query("select exists (select 1 from dictionary_table where id = :wordId) ")
    suspend fun existsWord(wordId : Int) : Boolean

}