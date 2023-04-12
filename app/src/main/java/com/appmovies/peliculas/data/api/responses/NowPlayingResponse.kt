package com.appmovies.peliculas.data.api.responses

import com.appmovies.peliculas.domain.Pelicula
import com.google.gson.annotations.Expose

class NowPlayingResponse(
    @Expose val dates: DatesResoponse,
    @Expose val page: Int,
    @Expose val results: List<Pelicula>,
    @Expose val total_results: Int,
    @Expose val total_pages: Int
) {
}