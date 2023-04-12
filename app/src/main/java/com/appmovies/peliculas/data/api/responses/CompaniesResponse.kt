package com.appmovies.peliculas.data.api.responses

import com.google.gson.annotations.Expose

class CompaniesResponse
    (
    @Expose val name: String,
    @Expose val id: Int,
    @Expose val logo_path: String?,
    @Expose val origin_country: String
) {
}