package com.example.dictionary.di

import android.content.Context
import androidx.room.Room
import com.example.dictionary.db.DictionaryDao
import com.example.dictionary.db.DictionaryDataBase
import com.example.dictionary.db.DictionaryEntity
import com.example.dictionary.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) : DictionaryDataBase =
        Room.databaseBuilder(context , DictionaryDataBase::class.java , Constants.DICTIONARY_DB)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

    @Provides
    @Singleton
    fun provideDao(db : DictionaryDataBase) : DictionaryDao = db.dictionaryDao()

    @Provides
    @Singleton
    fun provideEntity() : DictionaryEntity = DictionaryEntity()

}