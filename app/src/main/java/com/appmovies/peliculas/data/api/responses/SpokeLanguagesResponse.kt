package com.appmovies.peliculas.data.api.responses

import com.google.gson.annotations.Expose

class SpokeLanguagesResponse(
    @Expose val iso_639_1:String,
    @Expose val name:String
) {
}