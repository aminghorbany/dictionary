package com.example.dictionary.db

import androidx.room.*
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DictionaryDao {

//    @Query("""
//        SELECT DISTINCT englishWord, GROUP_CONCAT(persianWord, ', ') AS persianWords
//        FROM dictionary_table
//        WHERE englishWord LIKE :query || '%'
//        GROUP BY englishWord
//    """)

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

    @Query("delete from dictionary_table where englishWord = :engWord")
    suspend fun deleteWord(engWord: String)

    @Query("select * from dictionary_table where isFavorite = 1")
    suspend fun getAllFavoriteWord() : MutableList<DictionaryEntity>
}