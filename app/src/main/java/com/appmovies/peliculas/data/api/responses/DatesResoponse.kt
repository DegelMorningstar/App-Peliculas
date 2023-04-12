package com.appmovies.peliculas.data.api.responses

import com.google.gson.annotations.Expose

class DatesResoponse(
    @Expose val maximum:String,
    @Expose val minimum:String
) {
}