package com.example.dictionary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo : SearchRepository) : ViewModel() {

    val dictionaryEntityLiveData = MutableLiveData<List<DictionaryEntity>>()
    val loading = MutableLiveData<Boolean>()

    fun getFilteredWords(query : String) = viewModelScope.launch {
        loading.postValue(true)
        val res = repo.getFilteredWords(query)
        dictionaryEntityLiveData.postValue(res)
        loading.postValue(false)
    }
}