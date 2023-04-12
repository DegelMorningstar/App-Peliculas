package com.appmovies.peliculas.data.api.util

import com.appmovies.peliculas.data.api.responses.DetailResponse
import com.appmovies.peliculas.domain.Pelicula

class Mapper {
    fun fromDetailResponseToMovieDomain(detailResponse: DetailResponse): Pelicula {
        return Pelicula(
            detailResponse.poster_path,
            detailResponse.adult,
            detailResponse.overview ?: "",
            detailResponse.release_date,
            detailResponse.genres!!.map {
                it.id
            },
            detailResponse.id,
            detailResponse.original_title,
            detailResponse.original_language,
            detailResponse.title,
            detailResponse.backdrop_path,
            detailResponse.popularity,
            detailResponse.vote_count,
            detailResponse.video,
            detailResponse.vote_average
            )
    }
}