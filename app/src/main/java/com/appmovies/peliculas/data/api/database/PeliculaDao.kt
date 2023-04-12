package com.appmovies.peliculas.data.api.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appmovies.peliculas.domain.Pelicula

@Dao
interface PeliculaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(peliculas: MutableList<Pelicula>)

    @Query("select * from peliculas")
    fun getPeliculas(): MutableList<Pelicula>

    @Query("SELECT count(id) FROM peliculas") // items is the table in the @Entity tag of ItemsYouAreStoringInDB.kt, id is a primary key which ensures each entry in DB is unique
    fun numberOfItemsInDB() : Int
}