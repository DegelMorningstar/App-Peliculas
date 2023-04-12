package com.appmovies.peliculas.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "peliculas")
data class Pelicula(
    @Expose val poster_path: String?,
    @Expose val adult: Boolean,
    @Expose val overview: String,
    @Expose val release_date: String,
    @Expose val genre_ids: List<Int>,
    @Expose @PrimaryKey val id: Int,
    @Expose val original_title: String,
    @Expose val original_language: String,
    @Expose val title: String,
    @Expose val backdrop_path: String?,
    @Expose val popularity: Float,
    @Expose val vote_count: Int,
    @Expose val video: Boolean,
    @Expose val vote_average: Float,
    var type:Int? = null
) : Parcelable

enum class TipoLista{
    TOP_RATED,NOW_PLAYING
}