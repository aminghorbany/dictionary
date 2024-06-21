package com.example.dictionary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.repository.FavoriteRepository
import com.example.dictionary.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repo : FavoriteRepository) : ViewModel() {

    val FavoriteWordsLiveData = MutableLiveData<List<DictionaryEntity>>()

    fun getFavoriteWords() = viewModelScope.launch {
        val res = repo.getFavoriteWords()
        FavoriteWordsLiveData.postValue(res)
    }

}