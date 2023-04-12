package com.appmovies.peliculas.data.api.responses

import com.google.gson.annotations.Expose

class ProductionContriesResponse(
    @Expose val iso_3166_1:String,
    @Expose val name: String
) {
}