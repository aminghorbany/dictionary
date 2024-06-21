package com.example.dictionary.repository

import com.example.dictionary.api.ApiServices
import com.example.dictionary.db.DictionaryDao
import com.example.dictionary.db.DictionaryEntity
import javax.inject.Inject

class TranslatorRepository @Inject constructor(private val api: ApiServices , private val dao: DictionaryDao) {

    suspend fun getTranslateWord(token: String, action: String, lang: String, word: String)
    = api.getTranslateWord(token, action, lang, word)

    suspend fun insertWord(entity : DictionaryEntity) = dao.insertWord(entity)
}