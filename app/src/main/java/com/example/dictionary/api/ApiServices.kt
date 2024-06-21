package com.example.dictionary.api

import com.example.dictionary.models.ResponseTranslateWord
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    /**
    نکته مهم: توکن میبایست در هدر به صورت سراسری ارسال شود ولی در این سایت چون کوئری بیس هستند در قالب هدر قبول نمی کنند.
     */

    @GET("translate/")
    suspend fun getTranslateWord(
        @Query("token") token: String,
        @Query("action") action: String,
        @Query("lang") lang: String,
        @Query("q") word: String
    ): Response<ResponseTranslateWord>

}