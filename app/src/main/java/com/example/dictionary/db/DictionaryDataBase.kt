package com.example.dictionary.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DictionaryEntity::class] , version = 1 , exportSchema = false)
abstract class DictionaryDataBase : RoomDatabase(){
    abstract fun dictionaryDao() : DictionaryDao
}