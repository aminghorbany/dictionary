package com.example.dictionary.models

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResponseTranslateWord(
    @SerializedName("status")
    val status: Int?, // 200
    @SerializedName("result")
    val result: String? // دوستان
) : Parcelable