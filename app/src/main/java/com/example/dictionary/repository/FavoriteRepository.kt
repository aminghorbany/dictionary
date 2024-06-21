package com.example.dictionary.repository

import com.example.dictionary.db.DictionaryDao
import com.example.dictionary.db.DictionaryEntity
import javax.inject.Inject


class FavoriteRepository @Inject constructor(private val dao: DictionaryDao){
    suspend fun getFavoriteWords() = dao.getAllFavoriteWord()
}