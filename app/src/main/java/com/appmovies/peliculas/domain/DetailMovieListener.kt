package com.appmovies.peliculas.domain

interface DetailMovieListener {
    fun onSuccess(movie: Pelicula)
    fun onErrror(error: String)
}