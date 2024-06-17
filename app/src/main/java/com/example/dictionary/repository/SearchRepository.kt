package com.example.dictionary.repository

import com.example.dictionary.db.DictionaryDao
import com.example.dictionary.db.DictionaryEntity
import javax.inject.Inject


class SearchRepository @Inject constructor(private val dao: DictionaryDao){

    suspend fun getFilteredWords(query: String) = dao.searchDictionary(query)

}