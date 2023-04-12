package com.appmovies.peliculas.di

import android.content.Context
import androidx.room.Room
import com.appmovies.peliculas.data.api.database.PeliculaDao
import com.appmovies.peliculas.data.api.database.PeliculaDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {
    @Provides
    fun providePeliculaDao(peliculaDataBase: PeliculaDataBase):PeliculaDao{
        return peliculaDataBase.peliculaDao()
    }
    @Provides
    fun provideDataBase(@ApplicationContext context: Context):PeliculaDataBase{
        return Room.databaseBuilder(
            context.applicationContext,
            PeliculaDataBase::class.java,
            "peliculas"
        ).allowMainThreadQueries().build()
    }
}