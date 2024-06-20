package com.example.dictionary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.api.ApiServices
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.models.ResponseTranslateWord
import com.example.dictionary.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor(private val api : ApiServices) : ViewModel() {

    val translateWordLiveData = MutableLiveData<ResponseTranslateWord>()
    val loading = MutableLiveData<Boolean>()

    fun getTranslateWord(token : String ,action : String , lang : String , word : String )
    = viewModelScope.launch {
        loading.postValue(true)
        val res = api.getTranslateWord(token, action, lang, word)
        translateWordLiveData.postValue(res.body())
        loading.postValue(false)
    }

}