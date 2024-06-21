package com.example.dictionary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.api.ApiServices
import com.example.dictionary.db.DictionaryDao
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.models.ResponseTranslateWord
import com.example.dictionary.repository.FavoriteRepository
import com.example.dictionary.repository.TranslatorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TranslatorViewModel @Inject constructor(private val repo : TranslatorRepository) : ViewModel() {

    val translateWordLiveData = MutableLiveData<ResponseTranslateWord>()
    val loading = MutableLiveData<Boolean>()
    val isSuccessful = MutableLiveData<Boolean>()

    fun getTranslateWord(token : String ,action : String , lang : String , word : String )
    = viewModelScope.launch {
        loading.postValue(true)
        val res = repo.getTranslateWord(token, action, lang, word)
        if (res.isSuccessful) {
            translateWordLiveData.postValue(res.body())
            isSuccessful.postValue(true)
        }else{
            isSuccessful.postValue(false)
        }
        loading.postValue(false)
    }

    fun insertWord(entity: DictionaryEntity) = viewModelScope.launch {
        repo.insertWord(entity)
    }

}