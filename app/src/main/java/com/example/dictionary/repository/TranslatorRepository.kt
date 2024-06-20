package com.example.dictionary.repository

import com.example.dictionary.api.ApiServices
import javax.inject.Inject

class TranslatorRepository @Inject constructor(private val api: ApiServices) {

    suspend fun getTranslateWord(token: String, action: String, lang: String, word: String)
    = api.getTranslateWord(token, action, lang, word)

}