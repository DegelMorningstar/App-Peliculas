package com.appmovies.peliculas.data.api.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.appmovies.peliculas.domain.Pelicula
import javax.inject.Singleton

@Database(entities = [Pelicula::class], version = 1, exportSchema = false)
@TypeConverters(SourceTypeConverter::class)
@Singleton
abstract class PeliculaDataBase : RoomDatabase() {
    abstract fun peliculaDao(): PeliculaDao
}