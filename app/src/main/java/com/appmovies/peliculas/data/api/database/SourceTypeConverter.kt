package com.appmovies.peliculas.data.api.database

import androidx.room.TypeConverter
import com.appmovies.peliculas.data.api.util.fromJson
import com.google.gson.Gson

class SourceTypeConverter {
    @TypeConverter
    fun fromGenreIds(value: List<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toGenreIds(value: String): List<Int> {
        return try {
            Gson().fromJson<List<Int>>(value) //using extension function
        } catch (e: Exception) {
            emptyList<Int>()
        }
    }


}